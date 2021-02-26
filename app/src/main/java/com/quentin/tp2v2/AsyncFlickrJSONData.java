package com.quentin.tp2v2;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncFlickrJSONData  extends AsyncTask<String, Void, JSONObject> {
    private URL url;
    private String res;

    public AsyncFlickrJSONData(URL s) {
        url = s;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        HttpURLConnection urlConnection;
        JSONObject jsObj = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);
                res = s;
            } finally {
                urlConnection.disconnect();
            }
        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        try {
            jsObj = new JSONObject(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsObj;
    }

    protected void onPostExecute(JSONObject jsObj){
        int l = jsObj.length();
        String result = (String)jsObj.toString().subSequence(13, l - 2);
        Log.i("JFL", result);

        String res = null;
        try {
            JSONObject obj = jsObj.getJSONObject("link");
            res =  jsObj.getJSONArray("items").getJSONObject(0).getJSONObject("media").getString("m");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}
