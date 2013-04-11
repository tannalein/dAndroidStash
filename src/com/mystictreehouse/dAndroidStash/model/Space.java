package com.mystictreehouse.dAndroidStash.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/8/13
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Space {
    private String availableSpace;
    private String totalSpace;

    private static final String AVAILABLE_SPACE = "available_space";
    private static final String TOTAL_SPACE = "total_space";

    public Space() {}

    public Space(JSONObject json) throws JSONException {
        this.availableSpace = json.getString(AVAILABLE_SPACE);
        this.totalSpace = json.getString(TOTAL_SPACE);
    }

    public String getAvailableSpace(){
        return availableSpace;
    }

    public void setAvailableSpace(String availableSpace){
        this.availableSpace = availableSpace;
    }

    public String getTotalSpace(){
        return totalSpace;
    }

    public void setTotalSpace(String totalSpace){
        this.totalSpace = totalSpace;
    }
}
