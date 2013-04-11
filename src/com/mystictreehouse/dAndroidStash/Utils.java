/**
 *       Copyright 2010 Newcastle University
 *
 *          http://research.ncl.ac.uk/smart/
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mystictreehouse.dAndroidStash;

import com.mystictreehouse.dAndroidStash.exception.ApplicationException;
import com.mystictreehouse.dAndroidStash.model.OAuthParams;
import com.mystictreehouse.dAndroidStash.model.OAuthRegParams;


/**
 *
 *
 *
 */
public final class Utils {
    private Utils() {
    }

    public static final String REDIRECT_URI = "http://localhost:8080/redirect";
    public static final String DISCOVERY_URI = "http://localhost:8080";

    public static final String REG_TYPE_PULL = "pull";
    public static final String REG_TYPE_PUSH = "push";

    public static final String DEVIANTART = "deviantart";
    public static final String DEVIANTART_API = "https://www.deviantart.com/api/draft15/";
    public static final String DEVIANTART_AUTHZ = "https://www.deviantart.com/oauth2/draft15/authorize";
    public static final String DEVIANTART_TOKEN = "https://www.deviantart.com/oauth2/draft15/token";
    public static final String DEVIANTART_CLIENT_ID = "143";
    public static final String DEVIANTART_CLIENT_SECRET = "89c6f55051e36bdea1cca3fa18442a02";

    public static final String DEVIANTART_USER = "user/whoami";

    public static final String DEVIANTART_STASH_SUBMIT = "stash/submit";
    public static final String DEVIANTART_STASH_DELETE = "stash/delete";
    public static final String DEVIANTART_STASH_MOVE = "stash/move";
    public static final String DEVIANTART_STASH_RENAME = "stash/folder";
    public static final String DEVIANTART_STASH_SPACE = "stash/space";
    public static final String DEVIANTART_STASH_LIST = "stash/delta";
    public static final String DEVIANTART_STASH_METADATA = "stash/metadata";
    public static final String DEVIANTART_STASH_MEDIA = "stash/media";

    public static final String OAUTHPARAMS = "oAuthParams";

    public static void validateRegistrationParams(OAuthRegParams oauthParams) throws ApplicationException {

        String regType = oauthParams.getRegistrationType();

        String name = oauthParams.getName();
        String url = oauthParams.getUrl();
        String description = oauthParams.getDescription();
        StringBuffer sb = new StringBuffer();

        if (isEmpty(url)) {
            sb.append("Application URL ");
        }

        if (REG_TYPE_PUSH.equals(regType)) {
            if (isEmpty(name)) {
                sb.append("Application Name ");
            }

            if (isEmpty(description)) {
                sb.append("Application URL ");
            }
        } else if (!REG_TYPE_PULL.equals(regType)) {
            throw new ApplicationException("Incorrect registration type: " + regType);
        }

        String incorrectParams = sb.toString();
        if ("".equals(incorrectParams)) {
            return;
        }
        throw new ApplicationException("Incorrect parameters: " + incorrectParams);

    }

    public static void validateAuthorizationParams(OAuthParams oauthParams) throws ApplicationException {


        String authzEndpoint = oauthParams.getAuthzEndpoint();
        String tokenEndpoint = oauthParams.getTokenEndpoint();
        String clientId = oauthParams.getClientId();
        String clientSecret = oauthParams.getClientSecret();
        String redirectUri = oauthParams.getRedirectUri();

        StringBuffer sb = new StringBuffer();

        if (isEmpty(authzEndpoint)) {
            sb.append("authorization Endpoint ");
        }

        if (isEmpty(tokenEndpoint)) {
            sb.append("Token Endpoint ");
        }

        if (isEmpty(clientId)) {
            sb.append("Client ID ");
        }

        if (isEmpty(clientSecret)) {
            sb.append("Client Secret ");
        }

        if (!REDIRECT_URI.equals(redirectUri)) {
            sb.append("Redirect URI");
        }

        String incorrectParams = sb.toString();
        if ("".equals(incorrectParams)) {
            return;
        }
        throw new ApplicationException("Incorrect parameters: " + incorrectParams);

    }

    public static void validateTokenParams(OAuthParams oauthParams) throws ApplicationException {

        String authzEndpoint = oauthParams.getAuthzEndpoint();
        String tokenEndpoint = oauthParams.getTokenEndpoint();
        String clientId = oauthParams.getClientId();
        String clientSecret = oauthParams.getClientSecret();
        String redirectUri = oauthParams.getRedirectUri();
        String authzCode = oauthParams.getAuthzCode();

        StringBuffer sb = new StringBuffer();

        if (isEmpty(authzCode)) {
            sb.append("authorization Code ");
        }

        if (isEmpty(authzEndpoint)) {
            sb.append("authorization Endpoint ");
        }

        if (isEmpty(tokenEndpoint)) {
            sb.append("Token Endpoint ");
        }

        if (isEmpty(clientId)) {
            sb.append("Client ID ");
        }

        if (isEmpty(clientSecret)) {
            sb.append("Client Secret ");
        }

        if (!REDIRECT_URI.equals(redirectUri)) {
            sb.append("Redirect URI");
        }

        String incorrectParams = sb.toString();
        if ("".equals(incorrectParams)) {
            return;
        }
        throw new ApplicationException("Incorrect parameters: " + incorrectParams);

    }

    public static boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }

    public static String isIssued(String value) {
        if (isEmpty(value)) {
            return "(Not issued)";
        }
        return value;
    }
}
