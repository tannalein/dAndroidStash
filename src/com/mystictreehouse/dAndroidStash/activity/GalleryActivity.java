package com.mystictreehouse.dAndroidStash.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mystictreehouse.dAndroidStash.ApplicationListener;
import com.mystictreehouse.dAndroidStash.R;
import com.mystictreehouse.dAndroidStash.StashApplication;
import com.mystictreehouse.dAndroidStash.Utils;
import com.mystictreehouse.dAndroidStash.model.*;
import com.mystictreehouse.dAndroidStash.tasks.ApiClient;
import com.mystictreehouse.dAndroidStash.tasks.RetrieveImage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/8/13
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class GalleryActivity extends Activity implements ApplicationListener {
    LinearLayout myGallery;
    ImageView mainImageView;
    static int i;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stash);
        ((StashApplication) this.getApplication()).registerListener(this);

        // Reference the Gallery view
        myGallery = (LinearLayout)findViewById(R.id.mygallery);
        mainImageView = (ImageView) findViewById(R.id.singleImageView);
        final GalleryActivity activity = this;

        RequestParams params = new RequestParams();
        params.put("access_token", ((StashApplication) this.getApplication()).getToken().getAccessToken());


        ImageButton uploadButton = (ImageButton) findViewById(R.id.imageButtonUpload);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Starting access token Intent
                Intent uploadScreen = new Intent(getApplicationContext(), UploadActivity.class);

                // starting access token  activity
                startActivity(uploadScreen);
            }
        });

        mainImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemId id = (ItemId) mainImageView.getTag();

                StashObject.StashObjectType type = id.getType();
                switch (type) {
                    case FOLDER:
                        //TODO: Folder activity
                        break;
                    case SUBMISSION:
                        //Starting item Intent
                        Intent itemScreen = new Intent(getApplicationContext(), ItemActivity.class);

                        itemScreen.putExtra("stashid", id.getResponse().getStashId());
                        // starting item  activity
                        startActivity(itemScreen);
                        break;
                }
            }
        });
        ApiClient.get(Utils.DEVIANTART_USER, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                // Get user data
                try {
                    ((StashApplication) activity.getApplication()).setUser(new User(json));

                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });

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

        ApiClient.post(Utils.DEVIANTART_STASH_LIST, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                // Get stash content
                try {
                    StashContent stashContent = new StashContent();

                    stashContent.setCursor(json.getString("cursor"));
                    stashContent.setHasMore(json.getBoolean("has_more"));
                    stashContent.setReset(json.getBoolean("reset"));

                    List<StashObject> stashObjects = new ArrayList<StashObject>();
                    JSONArray entries = json.getJSONArray("entries");

                    for (int i = 0; i < entries.length(); i++) {
                        Map<String, String> stashObjectMetadata = new HashMap<String, String>();
                        JSONObject metadata = entries.getJSONObject(i).getJSONObject("metadata");
                        Iterator it = metadata.keys();
                        while (it.hasNext()) {
                            String key = (String) it.next();
                            if (key.equals("contents")) continue;
                            if (key.equals("files")) {
                                JSONObject files = metadata.getJSONObject(key);
                                Iterator fit = files.keys();
                                while(fit.hasNext()) {
                                    String fileKey = (String)fit.next();
                                    stashObjectMetadata.put(key + "_" + fileKey, files.getString(fileKey));
                                }
                            } else {
                                stashObjectMetadata.put(key, metadata.getString(key));
                            }
                        }

                        stashObjects.add(new StashObject(stashObjectMetadata));
                    }

                    stashContent.setStashObjects(stashObjects);

                    ((StashApplication) activity.getApplication()).setStashContent(stashContent);

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

        final List<ItemId> ids = new ArrayList<ItemId>();

        for(StashObject stashObject: stashContent.getStashObjects()){
            ItemId id = new ItemId();
            Response response = new Response();

            StashObject.StashObjectType type = stashObject.type();
            //TODO: proper presentation of folders
            switch (type) {
                case FOLDER:
//                    id.setType(type);
//                    id.setUrl(stashObject.getMetadata().get("thumb"));
//                    response.setFolder(stashObject.getMetadata().get("title"));
//                    response.setFolderId(stashObject.getMetadata().get("folderid"));
//                    id.setResponse(response);
//                    ids.add(id);
                      break;
                case SUBMISSION:
                    id.setType(type);
                    id.setUrl(stashObject.getMetadata().get("files_fullview"));
                    response.setFolderId(stashObject.getMetadata().get("folderid"));
                    response.setStashId(stashObject.getMetadata().get("stashid"));
                    id.setResponse(response);
                    ids.add(id);
                    break;
            }
        }

        for (final ItemId itemId: ids){
//            try {
                // TODO: progress bar
                Toast.makeText(myGallery.getContext(), "Retrieving images, please wait", Toast.LENGTH_LONG).show();

                //Bitmap bitmap = null;
                View view = this.insertPhoto(itemId.getUrl());
                itemId.setId(i++);
                view.setTag(itemId);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "image selected: " + itemId.getUrl(), Toast.LENGTH_LONG).show();
                        setSelectedImage(itemId.getId());
                    }
                });
                myGallery.addView(view);


//            } catch (JSONException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
        }
    }

    @Override
    public void onSubmissionChange(SubmissionMetadata submissionMetadata) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public View insertPhoto(String path){
  //      Bitmap bm = decodeSampledBitmapFromUri(path, 220, 220);

        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setLayoutParams(new LayoutParams(250, 250));
        layout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setLayoutParams(new LayoutParams(220, 220));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        layout.addView(imageView);

        try {
            RetrieveImage.getBitmap(path, imageView);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //    imageView.setImageBitmap(bm);

        return layout;
    }

    public View insertPhoto(Bitmap bm){

        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setLayoutParams(new LayoutParams(250, 250));
        layout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setLayoutParams(new LayoutParams(220, 220));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        layout.addView(imageView);

        imageView.setImageBitmap(bm);

        return layout;
    }

    public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
        Bitmap bm = null;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, options);

        return bm;
    }

    public int calculateInSampleSize(

        BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }

        return inSampleSize;
    }

    public void setSelectedImage(int selectedImagePosition) {

        LinearLayout child = (LinearLayout)myGallery.getChildAt(selectedImagePosition);
        ImageView imageChild = (ImageView)child.getChildAt(0);

        mainImageView.setImageDrawable(imageChild.getDrawable());
        mainImageView.setTag(child.getTag());
    }
}