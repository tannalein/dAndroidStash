package com.mystictreehouse.dAndroidStash.model;

import com.mystictreehouse.dAndroidStash.ApplicationListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/9/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubmissionMetadata {

    private String stashId;
    private String folderId;
    private Boolean isFolder;
    private String title;
    private String artistComment;
    private String keywords;
    private String originalUrl;
    private String category;
    private Map<String, String> map;

    private static final String STASH_ID = "stashid";
    private static final String FOLDER_ID = "folderid";
    private static final String IS_FOLDER = "is_folder";
    private static final String TITLE = "title";
    private static final String ARTIST_COMMENTS = "artist_comment";
    private static final String KEYWORDS = "keywords";
    private static final String ORIGINAL_URL = "original_url";
    private static final String CATEGORY = "category";
    private static final String FILES = "files";

    public String getStashId() {
        return stashId;
    }

    public void setStashId(String stashId) {
        this.stashId = stashId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public Boolean getFolder() {
        return isFolder;
    }

    public void setFolder(Boolean folder) {
        isFolder = folder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistComment() {
        return artistComment;
    }

    public void setArtistComment(String artistComment) {
        this.artistComment = artistComment;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
