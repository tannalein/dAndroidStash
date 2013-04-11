package com.mystictreehouse.dAndroidStash.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.mystictreehouse.dAndroidStash.R;
import com.mystictreehouse.dAndroidStash.Utils;
import com.mystictreehouse.dAndroidStash.api.Authorization;
import com.mystictreehouse.dAndroidStash.exception.ApplicationException;
import com.mystictreehouse.dAndroidStash.model.OAuthParams;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/7/13
 * Time: 6:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthorizationActivity extends Activity {

    OAuthParams oAuthParams;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        WebView webview = (WebView)findViewById(R.id.authWebView);
        webview.getSettings().setJavaScriptEnabled(true);

        oAuthParams = new OAuthParams();

        oAuthParams.setAuthzEndpoint(Utils.DEVIANTART_AUTHZ);
        oAuthParams.setTokenEndpoint(Utils.DEVIANTART_TOKEN);
        oAuthParams.setClientId(Utils.DEVIANTART_CLIENT_ID);
        oAuthParams.setClientSecret(Utils.DEVIANTART_CLIENT_SECRET);
        oAuthParams.setRedirectUri(Utils.REDIRECT_URI);

        Authorization auth = new Authorization();
        String redirect = null;

        try {
            redirect = auth.authorize(oAuthParams);
        } catch (OAuthSystemException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        /* WebViewClient must be set BEFORE calling loadUrl! */
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap bitmap)  {
                //TODO: replace with logger
                //System.out.println("onPageStarted : " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url)  {

                if (url.startsWith(Utils.REDIRECT_URI)) {
                    try {
                        if (url.indexOf("code=")!=-1) {

                            String code = extractCodeFromUrl(url);

                            oAuthParams.setAuthzCode(code);
                            Utils.validateTokenParams(oAuthParams);

                            //Starting access token Intent
                            Intent tokenScreen = new Intent(getApplicationContext(), AccessTokenActivity.class);

                            tokenScreen.putExtra(Utils.OAUTHPARAMS, oAuthParams);
                            // starting access token  activity
                            startActivity(tokenScreen);
                        }
                    } catch (ApplicationException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                //TODO: replace with logger
                //System.out.println("onPageFinished : " + url);
            }

            private String extractCodeFromUrl(String url) {
                return url.substring(Utils.REDIRECT_URI.length()+6,url.length());
            }
        });

        webview.loadUrl(redirect);
    }
}
