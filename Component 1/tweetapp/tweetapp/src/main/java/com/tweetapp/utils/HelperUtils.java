package com.tweetapp.utils;

import com.tweetapp.dto.UserDTO;
import com.tweetapp.models.User;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperUtils {

    public UserDTO convertUserToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setGender(user.getGender());
        userDTO.setDob(user.getDob());
        userDTO.setId(user.getId());
        userDTO.setPassword(user.getPassword());
        return  userDTO;
    }

    public User convertUserDTOtoUser(UserDTO userDTO){
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setDob(userDTO.getDob());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public boolean validateTweetContent(String content){
        if(content.length()<1){
            System.err.println("Tweet Content cannot be Empty");
            return false;
        }
        else if(content.length()>144){
            System.err.println("Tweet Content should not exceed 144 Characters");
            return false;
        }
        return true;
    }

    public boolean validateInputDate(String date){
        if(date.length()==0)
            return true;
        String regex = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public boolean validatePassword(String inputOldPassword,String newPassword,String confirmPassword){
        if(inputOldPassword.length()<1){
            System.err.println("Old Password is required");
            return false;
        }
        else if(newPassword.length()<1 || confirmPassword.length()<1){
            System.err.println("New Password/Confirm Password are required");
            return false;
        }
        else if(!newPassword.equals(confirmPassword)){
            System.err.println("New Password and Confirm Password are not equal");
            return false;
        }
        return true;
    }
    public boolean validateDto(UserDTO userDTO) {
        if(userDTO.getFirstName().length()<1){
            System.err.println("First Name is required");
            return false;
        }
        else if(userDTO.getGender().length()<1){
            System.err.println("Gender is required");
            return false;
        }
        else if(userDTO.getEmail().length()<1){
            System.err.println("Email is required");
            return false;
        }
        else if(!EmailValidator.getInstance().isValid(userDTO.getEmail()))
        {
            System.err.println("Invalid Email");
            return false;
        }
        return true;
    }
}
