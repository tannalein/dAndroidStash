package com.mystictreehouse.dAndroidStash.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/9/13
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Media {
    private String url;
    private String size;
    private String width;
    private String height;

    private static final String URL =  "url";
    private static final String SIZE = "size";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";

    public Media() {}

    public Media(JSONObject json) throws JSONException {
        this.url = json.getString(URL);
        this.size = json.getString(SIZE);
        this.width = json.getString(WIDTH);
        this.height = json.getString(HEIGHT);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return this.size;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return this.width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight() {
        return this.height;
    }
}
