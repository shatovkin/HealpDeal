package com.example.dimitri.helpdeal.azureClasses.classes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.dimitri.helpdeal.azureClasses.activities.Login_Activity;
import com.example.dimitri.helpdeal.azureClasses.azureModels.Users;

import java.util.HashMap;

/**
 * Created by Dimitri on 11.11.2017.
 */

public class SessionManager {

    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "SocialPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_EMAIL= "email";
    public static final String KEY_ID = "user_id";
    public static final String KEY_FIRSTNAME ="firstname";
    public static final String KEY_NAME ="name";
    public static final String KEY_INDEX ="index";
    public static final String KEY_CITY = "city";
    public static final String KEY_STREET ="street";
    public static final String KEY_PASSWORD ="password";
    public static final String KEY_PHONE ="phone";
    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(Users user) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, user.getId());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_FIRSTNAME,user.getFirstname());
        editor.putString(KEY_NAME,user.getName());
        editor.putString(KEY_INDEX,user.getIndex());
        editor.putString(KEY_CITY,user.getCity());
        editor.putString(KEY_STREET,user.getStreet());
        editor.putString(KEY_PASSWORD,user.getPassword());
        editor.putString(KEY_PHONE,user.getPhone());

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login_Activity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        // user email id
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE,null));
        // return user
        return user;
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        editor.putBoolean(IS_LOGIN, false);
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Login_Activity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * Quick check for login
     **/
// Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

}