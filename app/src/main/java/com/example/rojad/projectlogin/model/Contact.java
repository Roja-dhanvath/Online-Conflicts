package com.example.rojad.projectlogin.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rojad on 11/27/2016.
 */

public class Contact {

    public static final int CONTACT_TYPE_USER = 0;
    public static final int CONTACT_TYPE_ADMIN = CONTACT_TYPE_USER + 1;
    public int id;
    public String name;
    public String email;
    public String uname;
    public String password;
    public int type = CONTACT_TYPE_USER;


   /* public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }*/

}
