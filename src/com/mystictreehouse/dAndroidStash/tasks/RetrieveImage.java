package com.mystictreehouse.dAndroidStash.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.mystictreehouse.dAndroidStash.activity.GalleryActivity;
import com.mystictreehouse.dAndroidStash.model.ItemId;
import org.json.JSONException;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/10/13
 * Time: 7:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class RetrieveImage {
    static int i = 0;

    public static void getBitmap(final String url, final ImageView imageView) throws JSONException {
        AsyncHttpClient client = new AsyncHttpClient();

        String[] allowedTypes = new String[] { "image/png", "image/jpg", "image/gif" };
        final Bitmap[] bitmap = new Bitmap[1];

        client.get(url, new BinaryHttpResponseHandler(allowedTypes) {
            @Override
            public void onSuccess(byte[] imageData) {
                // Successfully got a response
                Log.d("MTH", "image retrieved " + url);
                if (imageData != null) {
                    bitmap[0] = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    imageView.setImageBitmap(bitmap[0]);
                }
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);    //To change body of overridden methods use File | Settings | File Templates.
                Log.e("MTH", "image not retrieved " + s);
            }
        });
    }

//    public static void getBitmap(final ItemId itemId, final LinearLayout myGallery, final GalleryActivity activity) throws JSONException {
//        AsyncHttpClient client = new AsyncHttpClient();
//
//        String[] allowedTypes = new String[] { "image/png", "image/jpg", "image/gif" };
//        //final Bitmap[] bitmap = new Bitmap[1];
//
//        client.get(itemId.getUrl(), new BinaryHttpResponseHandler(allowedTypes) {
//            @Override
//            public void onSuccess(byte[] imageData) {
//                // Successfully got a response
//                if (imageData != null) {
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
//                    //bitmaps.add(bitmap);
//                    View view = activity.insertPhoto(bitmap);
//                    itemId.setId(i++);
//                    view.setTag(itemId);
//                    view.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            activity.setSelectedImage(itemId.getId());
//                        }
//                    });
//                    myGallery.addView(view);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable throwable, String s) {
//                super.onFailure(throwable, s);    //To change body of overridden methods use File | Settings | File Templates.
//            }
//        });
//    }
}
