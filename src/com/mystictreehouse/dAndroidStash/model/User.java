package com.mystictreehouse.dAndroidStash.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/8/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class User {
    private String username;
    private String symbol;
    private String userIconUrl;

    private static final String USERNAME = "username";
    private static final String SYMBOL = "symbol";
    private static final String USERICONURL = "usericonurl";

    public User() {}

    public User(JSONObject json) throws JSONException {
        this.username = json.getString(USERNAME);
        this.symbol = json.getString(SYMBOL);
        this.userIconUrl = json.getString(USERICONURL);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getSymbol(){
        return symbol;
    }

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public String getUserIconUrl(){
        return userIconUrl;
    }

    public void setUserIconUrl(){
        this.userIconUrl = userIconUrl;
    }
}
