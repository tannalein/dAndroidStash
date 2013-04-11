package com.mystictreehouse.dAndroidStash;

import android.app.Application;
import com.mystictreehouse.dAndroidStash.model.Space;
import com.mystictreehouse.dAndroidStash.model.StashContent;
import com.mystictreehouse.dAndroidStash.model.Token;
import com.mystictreehouse.dAndroidStash.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/9/13
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class StashApplication extends Application {
    private Token token;
    private User user;
    private Space space;
    private StashContent stashContent;

    private final List<ApplicationListener> listeners = new ArrayList<ApplicationListener>();

    public void registerListener(ApplicationListener listener) {
        this.listeners.add(listener);
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        for(ApplicationListener listener: listeners) {
            listener.onUserChange(user);
        }
    }
    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
        for(ApplicationListener listener: listeners) {
            listener.onSpaceChange(space);
        }
    }

    public StashContent getStashContent() {
        return stashContent;
    }

    public void setStashContent(StashContent stashContent) {
        this.stashContent = stashContent;
        for(ApplicationListener listener: listeners) {
            listener.onStashContentChange(stashContent);
        }
    }
}
