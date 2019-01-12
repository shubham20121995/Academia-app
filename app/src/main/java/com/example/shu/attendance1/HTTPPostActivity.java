package com.example.shu.attendance1;

/**
 * Created by Shu on 22/09/2016.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class HTTPPostActivity extends Activity {
    private Context mContext;
    String token;

    static HTTPPostActivity INSTANCE;
    @Override
    public void onBackPressed() {

            finishAffinity();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mContext = HTTPPostActivity.this;
        INSTANCE=this;
        final EditText login=(EditText)findViewById(R.id.login);
        final EditText password=(EditText)findViewById(R.id.password);
        DatabaseHandler db=new DatabaseHandler(getApplicationContext());
        Cursor c=db.viewLogin();

        if(c.getCount()>0) {

            if (c.moveToFirst())
                do {

                    token = c.getString(c.getColumnIndex("token"));


                } while (c.moveToNext());
            Intent i=new Intent(HTTPPostActivity.this,Main2Activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
        }
        Button login_button=(Button)findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ReportAsynk().execute(login.getText().toString(),password.getText().toString());
            }
        });

    }

    private class ReportAsynk extends AsyncTask<String, String, String> {
        private ProgressDialog dialogdata;

        @Override
        protected void onPreExecute() {

            dialogdata = ProgressDialog.show(mContext, "", "Authenticating...");
            dialogdata.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            return com.example.shu.attendance1.newHttppost.makePostRequest("http://139.59.12.194:3001/token",params[0],params[1]);
        }

        @SuppressWarnings("unused")
        @Override
        protected void onPostExecute(String result) {

            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String success = jsonObject.getString("success");
                    if(success.equals("true")) {
                        token = jsonObject.getString("token");
                        Log.v("success", success);
                        Log.v("token", token);
                        DatabaseHandler db=new DatabaseHandler(getApplicationContext());
                        db.addLogin(token);
                        dialogdata.dismiss();

                        Intent i=new Intent(HTTPPostActivity.this, Main2Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i);
                         }
                    else
                    { Toast.makeText(mContext, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        dialogdata.dismiss();}

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{Toast.makeText(mContext, "No record", Toast.LENGTH_SHORT).show();

                dialogdata.dismiss();}
        }


    }
    public static HTTPPostActivity getActivityInstance()
    {
        return INSTANCE;
    }

    public String getToken()
    {
        return this.token;
    }

}
