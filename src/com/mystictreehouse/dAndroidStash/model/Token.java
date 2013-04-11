package com.mystictreehouse.dAndroidStash.model;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/9/13
 * Time: 8:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Token {
    private String accessToken;
    private Long expiresIn;
    private String refreshToken;

    public Token() {}

    public Token(String accessToken, Long expiresIn, String refreshToken) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
