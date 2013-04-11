package com.mystictreehouse.dAndroidStash.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import com.mystictreehouse.dAndroidStash.StashActivity;
import com.mystictreehouse.dAndroidStash.StashApplication;
import com.mystictreehouse.dAndroidStash.Utils;
import com.mystictreehouse.dAndroidStash.activity.AuthorizationActivity;
import com.mystictreehouse.dAndroidStash.activity.GalleryActivity;
import com.mystictreehouse.dAndroidStash.model.OAuthParams;
import com.mystictreehouse.dAndroidStash.model.Token;
import org.apache.amber.oauth2.client.OAuthClient;
import org.apache.amber.oauth2.client.URLConnectionClient;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.amber.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.amber.oauth2.common.message.types.GrantType;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/8/13
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetrieveToken extends AsyncTask<OAuthParams, Void, Boolean> {

    private Exception exception;
    private Activity activity;
    private OAuthParams oAuthParams;

    public RetrieveToken(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(OAuthParams... oAuthParamses) {
        oAuthParams = oAuthParamses[0];
        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(oAuthParams.getTokenEndpoint())
                    .setClientId(oAuthParams.getClientId())
                    .setClientSecret(oAuthParams.getClientSecret())
                    .setRedirectURI(oAuthParams.getRedirectUri())
                    .setCode(oAuthParams.getAuthzCode())
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .buildQueryMessage();

            OAuthClient client = new OAuthClient(new URLConnectionClient());

            OAuthAccessTokenResponse oauthResponse = null;
            Class<? extends OAuthAccessTokenResponse> cl = OAuthJSONAccessTokenResponse.class;

            oauthResponse = client.accessToken(request, cl);

            ((StashApplication) activity.getApplication()).setToken(new Token(
                    oauthResponse.getAccessToken(),
                    oauthResponse.getExpiresIn(),
                    Utils.isIssued(oauthResponse.getRefreshToken())));

            return true;

        } catch (Exception e) {
            this.exception = e;
            return false;
        }
    }

    @Override
    protected void onPostExecute(final Boolean success) {

        if (success) {
            Intent galleryScreen = new Intent(activity.getApplicationContext(), GalleryActivity.class);

            activity.startActivity(galleryScreen);
            //activity.finish();
        } else {
            //Intent mainScreen = new Intent(activity.getApplicationContext(), StashActivity.class);

            //activity.startActivity(mainScreen);
        }
    }
}
