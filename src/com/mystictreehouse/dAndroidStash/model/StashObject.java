package com.mystictreehouse.dAndroidStash.model;

import java.util.Collections;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/9/13
 * Time: 11:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class StashObject {
    public enum StashObjectType {
        FOLDER,
        SUBMISSION
    }
    private final Map<String, String> metadata;

    public StashObject(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public StashObjectType type() {
        return metadata.containsKey("stashid")
                ? StashObjectType.SUBMISSION
                : StashObjectType.FOLDER;
    }

    public Map<String, String> getMetadata() {
        return Collections.unmodifiableMap(metadata);
    }
}
