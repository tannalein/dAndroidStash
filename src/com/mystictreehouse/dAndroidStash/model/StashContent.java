package com.mystictreehouse.dAndroidStash.model;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/9/13
 * Time: 11:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class StashContent {
    private String cursor;
    private Boolean hasMore;
    private Boolean reset;
    private List<StashObject> stashObjects;

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public Boolean getReset() {
        return reset;
    }

    public void setReset(Boolean reset) {
        this.reset = reset;
    }

    public List<StashObject> getStashObjects() {
        return stashObjects;
    }

    public void setStashObjects(List<StashObject> stashObjects) {
        this.stashObjects = stashObjects;
    }
}
