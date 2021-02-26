package com.quentin.tp2v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private String res;
    private TextView tv_result;
    private Button GetAnImage;
    private GetImageOnClickListener myGetImageOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_result = findViewById(R.id.tv_result);
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

    public void onClick (View myView) {

        Thread t = new Thread(){
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection urlConnection;

                String username, password, sendMsg;
                String basicAuth;


                try {
                    url = new URL("https://httpbin.org/basic-auth/bob/sympa");
                    urlConnection = (HttpURLConnection) url.openConnection();

                    username = ((EditText)findViewById(R.id.Login)).getText().toString();
                    password = ((EditText)findViewById(R.id.Password)).getText().toString();
                    sendMsg = username + ":" + password;


                    basicAuth = "Basic " + Base64.encodeToString(sendMsg.getBytes(), Base64.NO_WRAP);

                    urlConnection.setRequestProperty ("Authorization", basicAuth);

                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        String s = readStream(in);
                        Log.i("JFL", "sendMsg=" + sendMsg);
                        res = s;
                    } finally {
                        urlConnection.disconnect();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsObj = new JSONObject(res);
                                    Boolean r = jsObj.getBoolean("authenticated");
                                    tv_result.setText(r.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch(MalformedURLException e) {
                    e.printStackTrace();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
    }
}