package com.quentin.tp2v2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {
    public void downloadImage(URL url, ImageView image) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        Bitmap bm = BitmapFactory.decodeStream(in);
        image.setImageBitmap(bm);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return null;
    }
}
