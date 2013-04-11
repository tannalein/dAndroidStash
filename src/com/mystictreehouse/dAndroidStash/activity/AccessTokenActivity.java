package com.mystictreehouse.dAndroidStash.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import com.mystictreehouse.dAndroidStash.R;
import com.mystictreehouse.dAndroidStash.Utils;
import com.mystictreehouse.dAndroidStash.exception.ApplicationException;
import com.mystictreehouse.dAndroidStash.model.OAuthParams;
import com.mystictreehouse.dAndroidStash.tasks.RetrieveToken;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/8/13
 * Time: 7:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessTokenActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accesstoken);

        Bundle data = getIntent().getExtras();
        OAuthParams oAuthParams = data.getParcelable(Utils.OAUTHPARAMS);

        try {
            Utils.validateTokenParams(oAuthParams);

            AsyncTask task = new RetrieveToken(this).execute(oAuthParams);

        } catch (ApplicationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}