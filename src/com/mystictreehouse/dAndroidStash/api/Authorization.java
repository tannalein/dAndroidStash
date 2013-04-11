package com.mystictreehouse.dAndroidStash.api;

import com.mystictreehouse.dAndroidStash.Utils;
import com.mystictreehouse.dAndroidStash.exception.ApplicationException;
import com.mystictreehouse.dAndroidStash.model.OAuthParams;
import org.apache.amber.oauth2.client.OAuthClient;
import org.apache.amber.oauth2.client.URLConnectionClient;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.client.response.GitHubTokenResponse;
import org.apache.amber.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.amber.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.types.GrantType;
import org.apache.amber.oauth2.common.message.types.ResponseType;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/7/13
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Authorization {
    public String authorize(OAuthParams oauthParams)
            throws OAuthSystemException, IOException {

        try {
            Utils.validateAuthorizationParams(oauthParams);

            OAuthClientRequest request = OAuthClientRequest
                    .authorizationLocation(oauthParams.getAuthzEndpoint())
                    .setClientId(oauthParams.getClientId())
                    .setRedirectURI(oauthParams.getRedirectUri())
                    .setResponseType(ResponseType.CODE.toString())
                    .setScope(oauthParams.getScope())
                    .buildQueryMessage();
            return request.getLocationUri();
        } catch (ApplicationException e) {
            oauthParams.setErrorMessage(e.getMessage());
            throw new OAuthSystemException(e.getMessage());
        }
    }

    public void getToken(OAuthParams oauthParams) throws OAuthSystemException, IOException {

        try {

            Utils.validateTokenParams(oauthParams);

            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(oauthParams.getTokenEndpoint())
                    .setClientId(oauthParams.getClientId())
                    .setClientSecret(oauthParams.getClientSecret())
                    .setRedirectURI(oauthParams.getRedirectUri())
                    .setCode(oauthParams.getAuthzCode())
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .buildQueryMessage();

            OAuthClient client = new OAuthClient(new URLConnectionClient());

            OAuthAccessTokenResponse oauthResponse = null;
            Class<? extends OAuthAccessTokenResponse> cl = OAuthJSONAccessTokenResponse.class;

            oauthResponse = client.accessToken(request, cl);

            oauthParams.setAccessToken(oauthResponse.getAccessToken());
            oauthParams.setExpiresIn(oauthResponse.getExpiresIn());
            oauthParams.setRefreshToken(Utils.isIssued(oauthResponse.getRefreshToken()));

        } catch (ApplicationException e) {
            oauthParams.setErrorMessage(e.getMessage());
            throw new OAuthSystemException(e.getMessage());
        } catch (OAuthProblemException e) {
            StringBuffer sb = new StringBuffer();
            sb.append("Error code: ").append(e.getError()).append("; ");
            sb.append("Error description: ").append(e.getDescription()).append("; ");
            sb.append("Error uri: ").append(e.getUri()).append("; ");
            sb.append("State: ").append(e.getState()).append("; ");
            oauthParams.setErrorMessage(sb.toString());
            throw new OAuthSystemException(e.getMessage());
        }
    }
}
