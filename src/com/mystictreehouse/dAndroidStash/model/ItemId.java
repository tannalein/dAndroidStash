package com.mystictreehouse.dAndroidStash.model;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/11/13
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemId {
    private int id;
    private StashObject.StashObjectType type;
    private String url;
    private Response response;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StashObject.StashObjectType getType() {
        return type;
    }

    public void setType(StashObject.StashObjectType type) {
        this.type = type;
    }
}
