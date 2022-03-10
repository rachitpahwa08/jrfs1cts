package com.tweetapp.service;

import com.tweetapp.dao.UserDAO;
import com.tweetapp.dao.UserDAOImpl;
import com.tweetapp.dto.UserDTO;
import com.tweetapp.models.Tweet;
import com.tweetapp.models.User;
import com.tweetapp.models.UserAndTweet;
import com.tweetapp.utils.HelperUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class UserService {

    private UserDAO userDAO = new UserDAOImpl();
    private HelperUtils helperUtils = new HelperUtils();
    private Scanner sc = new Scanner(System.in);
    private UserDTO userSession;

    public void menu(){
        if(userSession != null){
            userMenu();
        }
        else {
            defaultMenu();
        }
    }

    private void defaultMenu(){
        System.out.println("Welcome to Tweet APP");
        System.out.println("Please select option from below choices");
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("3. Exit");
        int input= sc.nextInt();
        sc.nextLine();
        switch (input){
            case 1:
                register();
                break;
            case  2:
                login();
                break;
            case  3:
                System.out.println("Thank You for using Tweet App.");
                System.exit(0);
            default:
                System.out.println("Wrong input");
                menu();
        }
    }

    private  void userMenu(){
        System.out.println("Please select your choice");
        System.out.println("1: Post a new Tweet");
        System.out.println("2: View all your Tweets");
        System.out.println("3: View all Users and their Tweets");
        System.out.println("4: Change Password");
        System.out.println("5: Logout");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice){
            case  1:
                postNewTweet();
                break;
            case  2:
                viewAllTweets();
                break;
            case  3:
                getUserWithTweets();
                break;
            case  4:
                changePassword();
                break;
            case  5:
                userSession=null;
                menu();
                break;
            default:
                System.out.println("Wrong Input");
                menu();
        }
    }

    private void register(){
        UserDTO userDTO = new UserDTO();
        System.out.println("Enter Below Details to Register User");
        System.out.print("Enter First Name: ");
        userDTO.setFirstName(sc.nextLine());
        System.out.print("Enter Last Name (Optional): ");
        String lastName=sc.nextLine();
        userDTO.setLastName(lastName.length()>0?lastName:null);
        System.out.print("Enter Gender (M for Male, F for Female, any other key for Others): ");
        char c = sc.next().charAt(0);
        String gender =String.valueOf(c).toLowerCase().equals("m")?"MALE":(String.valueOf(c).toLowerCase().equals("f")?"FEMALE":"OTHER");
        userDTO.setGender(gender);
        sc.nextLine();
        System.out.print("Enter Date of Birth (Optional): ");
        String dateStr = sc.nextLine();
        if(!helperUtils.validateInputDate(dateStr)){
            System.err.println("Date format should be in dd-MM-yyyy format\n");
            register();
            return;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        userDTO.setDob(dateStr.length()>0? LocalDate.parse(dateStr,dateTimeFormatter):null);
        System.out.print("Enter Email: ");
        userDTO.setEmail(sc.nextLine());
        System.out.print("Enter Password: ");
        userDTO.setPassword(sc.nextLine());
        if(helperUtils.validateDto(userDTO)){
            if(userDAO.register(helperUtils.convertUserDTOtoUser(userDTO))){
                System.out.println("User Registered Successfully");
            }
        }
        else {
            register();
            return;
        }
        System.out.print("Press any key to continue");
        sc.nextLine();
        menu();
    }

    private void login(){
        System.out.println("Enter your credentials");
        System.out.print("Email:");
        String email=sc.nextLine();
        if(email.length()<1){
            System.out.println("Email is required");
            login();
            return;
        }
        System.out.print("Password:");
        String password =sc.nextLine();
        if(password.length()<1){
            System.out.println("Password is required");
            login();
            return;
        }
        User user =userDAO.login(email,password);
        if(user!=null){
            System.out.println(String.format("Welcome %s",user.getFirstName()));
            userSession=helperUtils.convertUserToDTO(user);
        }
        else{
            System.err.println("Invalid Email/Password");
        }
        menu();
    }

    private void postNewTweet(){
        System.out.println("Enter below details to post a new Tweet");
        System.out.print("Enter Tweet Content (max 144 char)");
        String content = sc.nextLine();
        if(!helperUtils.validateTweetContent(content)){
            postNewTweet();
            return;
        }
        else {
            if(userDAO.postTweet(userSession.getId(),content)){
                System.out.println("Tweet Posted Successfully");
            }
            else {
                System.out.println("Some Error occurred, Please try after sometime");
            }
        }
        System.out.print("Press any key to continue");
        sc.nextLine();
        menu();
    }
    private void viewAllTweets(){
        var tweets = userDAO.getUserTweets(userSession.getId());
        if(tweets!=null) {
            if (tweets.size() > 0) {
                System.out.println("List of all Tweets");
                tweets.forEach((tweet) -> {
                    System.out.println("----------------------------------------------------");
                    System.out.println("Posted Time =>" + tweet.getPostedTime().toString());
                    System.out.println(String.format("Tweet Content => %s", tweet.getContent()));
                    System.out.println("----------------------------------------------------");
                });
            } else {
                System.err.println("No Tweet found for current User.");
            }
        }
        System.out.print("Press any key to continue");
        sc.nextLine();
        menu();
    }

    private void getUserWithTweets(){
        System.out.println("List of Users and their tweets.");
        var userAndTweets =userDAO.getAllUserWithTweets();
        if(userAndTweets!=null) {
            var userAndTweetsMap = userAndTweets.stream().collect(groupingBy(UserAndTweet::getUserId));
            List<User> users = userAndTweetsMap.entrySet().stream().map(val -> new User(val.getValue().get(0), val.getValue().stream().filter(userAndTweet -> userAndTweet.getContent() != null).map(tweet -> new Tweet(tweet.getContent(), tweet.getPostedTime(), tweet.getUserId(), -1)).collect(Collectors.toList()))).collect(Collectors.toList());
            users.forEach((user) -> {
                System.out.println(String.format("User Name => %s %s", user.getFirstName(), user.getLastName()));
                System.out.println(String.format("User Email => %s", user.getEmail()));
                System.out.println("Tweets =>");
                user.getTweets().forEach((tweet) -> {
                    System.out.println("----------------------------------------------------");
                    System.out.println("Posted Time =>" + tweet.getPostedTime().toString());
                    System.out.println(String.format("Tweet Content => %s", tweet.getContent()));
                    System.out.println("----------------------------------------------------");
                });
                if (user.getTweets().size() == 0) {
                    System.err.println("No Tweet found for this user.");
                }
            });
        }
        System.out.print("Press any key to continue");
        sc.nextLine();
        menu();
    }

    private void changePassword(){
        System.out.println("Enter below details to change the password.");
        System.out.print("Enter Old Password:");
        String oldPassword = sc.nextLine();
        System.out.print("Enter New Password:");
        String newPassword = sc.nextLine();
        System.out.print("Confirm New Password:");
        String confirmNewPassword = sc.nextLine();
        if(helperUtils.validatePassword(oldPassword,newPassword,confirmNewPassword)){
            if(userDAO.changePassword(userSession.getId(),newPassword,oldPassword)){
                System.out.println("Password Changed Successfully");
            }
            else {
                System.err.println("Wrong Password");
            }
        }
        else{
            changePassword();
            return;
        }
        System.out.print("Press any key to continue");
        sc.nextLine();
        menu();
    }
}
