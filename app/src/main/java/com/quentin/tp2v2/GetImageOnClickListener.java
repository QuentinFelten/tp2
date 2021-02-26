package com.quentin.tp2v2;

import android.view.View;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class GetImageOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        URL url = null;
        try {
            url = new URL("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        AsyncFlickrJSONData asynctask = new AsyncFlickrJSONData(url);
    }
}
