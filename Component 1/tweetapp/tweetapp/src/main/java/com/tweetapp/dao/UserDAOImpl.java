package com.tweetapp.dao;

import com.tweetapp.configuration.DBConnectorHandler;
import com.tweetapp.models.Tweet;
import com.tweetapp.models.User;
import com.tweetapp.models.UserAndTweet;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean register(User user) {
        int isUserRegistered =0;
        Connection connection = DBConnectorHandler.getConnection();
        LocalDate dob =user.getDob();
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT into user(first_name,last_name,gender,dob,email,password) values(?,?,?,?,?,?)")) {
            preparedStatement.setString(1,user.getFirstName());
            preparedStatement.setString(2,user.getLastName());
            preparedStatement.setString(3,user.getGender());
            preparedStatement.setDate(4, dob==null?null:Date.valueOf(dob));
            preparedStatement.setString(5,user.getEmail());
            preparedStatement.setString(6,user.getPassword());
            isUserRegistered= preparedStatement.executeUpdate();
            System.out.println("inserted");
        } catch (SQLException e) {
            if(e instanceof SQLIntegrityConstraintViolationException){
                System.err.println("User with this email already registered");
                return false;
            }
            System.err.println("Some Error Occurred,Try after sometime");
            return false;
        }
        return isUserRegistered>0;
    }

    @Override
    public User login(String email, String password) {
        User user=null;
        Connection connection = DBConnectorHandler.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from user where email=? and password=?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Date dobDB=resultSet.getDate("dob");
                user = new User(resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("gender"),
                        dobDB==null?null:dobDB.toLocalDate(),resultSet.getString("email"),null,null);
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Some Error Occurred,Try after sometime");
            return null;
        }

        return user;
    }

    @Override
    public boolean changePassword(int userId,String newPassword,String oldPassword) {
        Connection connection = DBConnectorHandler.getConnection();
        int isPasswordUpdated = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user set password=? where id=? and password=?")){
                preparedStatement.setString(1,newPassword);
                preparedStatement.setInt(2,userId);
                preparedStatement.setString(3,oldPassword);
                isPasswordUpdated = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Some Error Occurred,Try after sometime");
            return false;
        }
        return isPasswordUpdated>0;
    }

    @Override
    public boolean postTweet(int userId,String tweetContent) {
        int affectedRow=0;
        Connection connection = DBConnectorHandler.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT into tweet(content,user_id) values(?,?)")) {
            preparedStatement.setString(1,tweetContent);
            preparedStatement.setInt(2,userId);
            affectedRow  = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Some Error Occurred,Try after sometime");
            return false;
        }
        return affectedRow>0;
    }

    @Override
    public List<Tweet> getUserTweets(int userId) {
        List<Tweet> tweets = new ArrayList<>();
        Connection connection = DBConnectorHandler.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from tweet where user_id=?")) {
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tweets.add(new Tweet(resultSet.getString("content"),resultSet.getTimestamp("posted_time").toLocalDateTime(),resultSet.getInt("user_id"),resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            System.err.println("Some Error Occurred,Try after sometime");
            return null;
        }
        return tweets;
    }

    @Override
    public List<UserAndTweet> getAllUserWithTweets() {
        List<UserAndTweet> usersWithTweets = new ArrayList<>();
        Connection connection =DBConnectorHandler.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select user.id as id,first_name,last_name,gender,dob,email,content,posted_time from user left join tweet on user.id=tweet.user_id");
            //preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Timestamp postedTimeDB = resultSet.getTimestamp("posted_time");
                LocalDateTime postedTime = postedTimeDB!=null?postedTimeDB.toLocalDateTime():null;
                Date dobDb =resultSet.getDate("dob");
                usersWithTweets.add(new UserAndTweet(resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("gender"),
                        dobDb==null?null: dobDb.toLocalDate(),
                        resultSet.getString("email"),
                        resultSet.getString("content"),
                        postedTime));
            }
        } catch (SQLException e) {
            System.err.println("Some Error Occurred,Try after sometime");
            return null;
        }
        return usersWithTweets;
    }
}
