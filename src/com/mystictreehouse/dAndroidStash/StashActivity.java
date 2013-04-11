package com.mystictreehouse.dAndroidStash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.mystictreehouse.dAndroidStash.activity.AuthorizationActivity;
import com.mystictreehouse.dAndroidStash.activity.GalleryActivity;

public class StashActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        Button auth = (Button)findViewById(R.id.button);
//        auth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
                //Starting authorization Intent
                Intent authScreen = new Intent(getApplicationContext(), AuthorizationActivity.class);
                // starting authorization activity
                startActivity(authScreen);

                //this.finish();
//            }
//        }
//        );
    }

    protected void onResume() {
        super.onResume();
    }
}
