package com.tweetapp;

import com.tweetapp.service.UserService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        UserService service = new UserService();
        service.menu();
    }
}
