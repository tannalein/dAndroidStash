package com.mystictreehouse.dAndroidStash.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/9/13
 * Time: 5:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Response {
    private String stashId;
    private String folder;
    private String folderId;

    private static final String STASH_ID = "stashid";
    private static final String FOLDER = "folder";
    private static final String FOLDER_ID = "folderid";

    public Response() {}

    public Response(JSONObject json) throws JSONException {
        this.stashId = json.getString(STASH_ID);
        this.folder = json.optString(FOLDER);
        this.folderId = json.optString(FOLDER_ID);
    }

    public void setStashId(String stashId) {
        this.stashId = stashId;
    }

    public String getStashId() {
        return stashId;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getFolderId() {
        return this.folderId;
    }
}
