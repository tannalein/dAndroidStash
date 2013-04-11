package com.mystictreehouse.dAndroidStash.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mystictreehouse.dAndroidStash.ApplicationListener;
import com.mystictreehouse.dAndroidStash.R;
import com.mystictreehouse.dAndroidStash.StashApplication;
import com.mystictreehouse.dAndroidStash.Utils;
import com.mystictreehouse.dAndroidStash.model.Space;
import com.mystictreehouse.dAndroidStash.model.StashContent;
import com.mystictreehouse.dAndroidStash.model.SubmissionMetadata;
import com.mystictreehouse.dAndroidStash.model.User;
import com.mystictreehouse.dAndroidStash.tasks.ApiClient;
import com.mystictreehouse.dAndroidStash.tasks.RetrieveImage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/10/13
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemActivity extends Activity implements ApplicationListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);

        final ItemActivity activity = this;

        TextView userNameTextView = (TextView) findViewById(R.id.userNameTextView);
        User user = ((StashApplication) this.getApplication()).getUser();
        Space space = ((StashApplication) this.getApplication()).getSpace();
        userNameTextView.setText(user.getSymbol() + user.getUsername());

        ImageView mainImageView = (ImageView) findViewById(R.id.selected_imageview);

        RequestParams params = new RequestParams();
        params.put("access_token", ((StashApplication) this.getApplication()).getToken().getAccessToken());

        ImageButton uploadButton = (ImageButton) findViewById(R.id.imageButtonUpload);

        final SubmissionMetadata submissionMetadata = new SubmissionMetadata();

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Starting access token Intent
                Intent uploadScreen = new Intent(getApplicationContext(), UploadActivity.class);

                // starting access token  activity
                startActivity(uploadScreen);
            }
        });

        ImageButton homeButton = (ImageButton) findViewById(R.id.homeImageButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryScreen = new Intent(activity.getApplicationContext(), GalleryActivity.class);

                activity.startActivity(galleryScreen);
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.userIconImageView);
        try {
            RetrieveImage.getBitmap(user.getUserIconUrl(), imageView);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        TextView availableSpaceTextView = (TextView) findViewById(R.id.availableSpaceTextView);
        availableSpaceTextView.setText("Available space: " + space.getAvailableSpace());

        TextView totalSpaceTextView = (TextView) findViewById(R.id.totalSpaceTextView);
        totalSpaceTextView.setText("Total space: " + space.getTotalSpace());

        ApiClient.post(Utils.DEVIANTART_STASH_SPACE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                // Get space
                try {
                    ((StashApplication) activity.getApplication()).setSpace(new Space(json));

                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });

        params.put("stashid", getIntent().getExtras().get("stashid").toString());

        ApiClient.post(Utils.DEVIANTART_STASH_METADATA, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                // Get space
                try {
                    submissionMetadata.setTitle(json.getString("title"));
                    submissionMetadata.setArtistComment(json.getString("artist_comments"));
                    submissionMetadata.setKeywords(json.getString("keywords"));
                    submissionMetadata.setOriginalUrl(json.getString("original_url"));
                    submissionMetadata.setCategory(json.getString("category"));

                    Map<String, String> map = new HashMap<String, String>();
                    JSONObject files = json.getJSONObject("files");
                    Iterator fit = files.keys();
                    while(fit.hasNext()) {
                        String fileKey = (String)fit.next();
                        map.put("files" + "_" + fileKey, files.getString(fileKey));
                    }

                    submissionMetadata.setMap(map);

                    onSubmissionChange(submissionMetadata);

                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });
    }

    @Override
    public void onUserChange(User user) {
        TextView userNameTextView = (TextView) findViewById(R.id.userNameTextView);
        userNameTextView.setText(user.getSymbol() + user.getUsername());

        final ImageView imageView = (ImageView) findViewById(R.id.userIconImageView);
        try {
            RetrieveImage.getBitmap(user.getUserIconUrl(), imageView);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void onSpaceChange(Space space) {
            TextView availableSpaceTextView = (TextView) findViewById(R.id.availableSpaceTextView);
            availableSpaceTextView.setText("Available space: " + space.getAvailableSpace());

            TextView totalSpaceTextView = (TextView) findViewById(R.id.totalSpaceTextView);
            totalSpaceTextView.setText("Total space: " + space.getTotalSpace());
    }

    @Override
    public void onStashContentChange(StashContent stashContent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onSubmissionChange(SubmissionMetadata submissionMetadata) {
        TextView title = (TextView) findViewById(R.id.titleTextView);
        title.setText(submissionMetadata.getTitle());

        TextView comments = (TextView) findViewById(R.id.artistCommentsTextView);
        comments.setText(submissionMetadata.getArtistComment());

        TextView keywords = (TextView) findViewById(R.id.KeywordsTextView);
        keywords.setText(submissionMetadata.getKeywords());

        TextView originalUrl = (TextView) findViewById(R.id.originalUrlTextView);
        originalUrl.setText(submissionMetadata.getOriginalUrl());

        TextView category = (TextView) findViewById(R.id.tCategoryTextView);
        category.setText(submissionMetadata.getCategory());

        ImageView mainImageView = (ImageView) findViewById(R.id.selected_imageview);
        try {
            // TODO: progress bar
            RetrieveImage.getBitmap(submissionMetadata.getMap().get("files_200H"), mainImageView );

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}