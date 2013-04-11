package com.mystictreehouse.dAndroidStash;

import com.mystictreehouse.dAndroidStash.model.Space;
import com.mystictreehouse.dAndroidStash.model.StashContent;
import com.mystictreehouse.dAndroidStash.model.SubmissionMetadata;
import com.mystictreehouse.dAndroidStash.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/9/13
 * Time: 9:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ApplicationListener {
    public void onUserChange(User user);
    public void onSpaceChange(Space space);
    public void onStashContentChange(StashContent stashContent);
    public void onSubmissionChange(SubmissionMetadata submissionMetadata);
}
