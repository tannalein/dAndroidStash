package com.mystictreehouse.dAndroidStash.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/9/13
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class FolderMetadata {
    private String folderId;
    private Boolean isFolder;
    private String title;
    private String description;
    private String size;
    private String thumb;
    private List<SubmissionMetadata> submissionMetadataList;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public List<SubmissionMetadata> getSubmissionMetadataList() {
        return submissionMetadataList;
    }

    public void setSubmissionMetadataList(List<SubmissionMetadata> submissionMetadataList) {
        this.submissionMetadataList = submissionMetadataList;
    }
}
