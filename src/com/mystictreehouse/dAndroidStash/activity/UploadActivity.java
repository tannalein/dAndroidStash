package com.mystictreehouse.dAndroidStash.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/10/13
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class UploadActivity extends Activity implements ApplicationListener {

    protected ImageButton cameraUploadButton;
    protected ImageButton imageUploadButton;
    protected Button submitButton;
    protected ImageView mainImage;
    protected String path;
    protected boolean taken;

    protected static final String PHOTO_TAKEN	= "photo_taken";
    protected static final int REQ_CODE_PICK_IMAGE = 1;
    private static final int REQ_CODE_CAPTURE_IMAGE = 2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final UploadActivity activity = this;

        setContentView(R.layout.upload);

        path = Environment.getExternalStorageDirectory() + "/uploadImage.jpg";
        mainImage = ( ImageView ) findViewById( R.id.upload_imageview );
        cameraUploadButton = (ImageButton) findViewById( R.id.cameraUploadImageButton );
        imageUploadButton = (ImageButton) findViewById(R.id.uploadImageButton);
        submitButton = (Button) findViewById(R.id.buttonSubmit);

        TextView userNameTextView = (TextView) findViewById(R.id.userNameTextView);
        User user = ((StashApplication) this.getApplication()).getUser();
        Space space = ((StashApplication) this.getApplication()).getSpace();
        userNameTextView.setText(user.getSymbol() + user.getUsername());

        cameraUploadButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCameraActivity();
            }
        });

        imageUploadButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGalleryActivity();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File myFile = new File(path);
                RequestParams params = new RequestParams();
                params.put("access_token", ((StashApplication) activity.getApplication()).getToken().getAccessToken());
                params.put("title", (((EditText) findViewById(R.id.editTextTitle)).getText().toString()));

                try {
                    params.put("upload", myFile);
                } catch(FileNotFoundException e) {}

                Toast.makeText(getApplicationContext(), "Uploading", Toast.LENGTH_LONG).show();

                ApiClient.post(Utils.DEVIANTART_STASH_SUBMIT, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject json) {
                        // Get space
                        try {
                            if (json.getString("status").equals("success")){
                                Response response = new Response();
                                response.setStashId(json.getString("stashid"));
                                response.setFolder(json.getString("folder"));
                                response.setFolderId(json.getString("folderid"));

                                Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_LONG).show();

                                //Starting item Intent
                                Intent itemScreen = new Intent(getApplicationContext(), ItemActivity.class);

                                itemScreen.putExtra("stashid", json.getString("stashid"));
                                // starting item  activity
                                startActivity(itemScreen);

                            }   else {
                                Toast.makeText(getApplicationContext(), "Upload failed", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable, String s) {
                        super.onFailure(throwable, s);    //To change body of overridden methods use File | Settings | File Templates.
                        Toast.makeText(getApplicationContext(), "Upload failed: " + s, Toast.LENGTH_LONG).show();
                    }
                });
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

        RequestParams params = new RequestParams();
        params.put("access_token", ((StashApplication) activity.getApplication()).getToken().getAccessToken());

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
    }

    protected void startCameraActivity()
    {
        File file = new File( path );
        Uri outputFileUri = Uri.fromFile(file);

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(intent, REQ_CODE_CAPTURE_IMAGE);
    }

    protected void startGalleryActivity()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intent.setType("image/*");

        startActivityForResult( intent, REQ_CODE_PICK_IMAGE );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch(requestCode) {
            case REQ_CODE_PICK_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    path = cursor.getString(columnIndex);
                    cursor.close();

                    onPhotoTaken();
                }
                break;

            case REQ_CODE_CAPTURE_IMAGE:
                if (resultCode == RESULT_OK) {
                    // Image captured and saved to fileUri specified in the Intent
                    Toast.makeText(this, "Image saved to:\n" +
                            path, Toast.LENGTH_LONG).show();
                    onPhotoTaken();
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                    Toast.makeText(this, "Image upload canceled", Toast.LENGTH_LONG).show();
                } else {
                    // Image capture failed, advise user
                    Toast.makeText(this, "Error capturing image", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    protected void onPhotoTaken()
    {
        taken = true;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        Bitmap bitmap = BitmapFactory.decodeFile( path, options );

        mainImage.setImageBitmap(bitmap);
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState){
        if( savedInstanceState.getBoolean( UploadActivity.PHOTO_TAKEN ) ) {
            onPhotoTaken();
        }
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        outState.putBoolean( UploadActivity.PHOTO_TAKEN, taken );
    }

    @Override
    public void onUserChange(User user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onSpaceChange(Space space) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onStashContentChange(StashContent stashContent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onSubmissionChange(SubmissionMetadata submissionMetadata) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}