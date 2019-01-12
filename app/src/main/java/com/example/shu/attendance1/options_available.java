package com.example.shu.attendance1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shu on 23/09/2016.
 */
public class options_available extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_available);
        mContext = options_available.this;


        Button info_button = (Button) findViewById(R.id.info_button);
        info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iin = getIntent();
                Bundle b = iin.getExtras();

                String token = null;
                if (b != null) {
                    token = getIntent().getStringExtra("token");
                }
                new newAsynk().execute("http://139.59.12.194:3001/info", token);
            }
        });

        Button attendance_button = (Button) findViewById(R.id.attendance_button);
        attendance_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iin = getIntent();
                Bundle b = iin.getExtras();

                String token = null;
                if (b != null) {
                    token = getIntent().getStringExtra("token");
                }
                new newAsynk2().execute("http://139.59.12.194:3001/attendance", token);
            }
        });

        Button marks_button = (Button) findViewById(R.id.marks_button);
        marks_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iin = getIntent();
                Bundle b = iin.getExtras();

                String token = null;
                if (b != null) {
                    token = getIntent().getStringExtra("token");
                }
                new newAsynk3().execute("http://139.59.12.194:3001/marks", token);
            }
        });

    }

    private class newAsynk extends AsyncTask<String, String, String> {
        private ProgressDialog dialogdata;

        @Override
        protected void onPreExecute() {

            dialogdata = ProgressDialog.show(mContext, "", "Fetching data...");
            dialogdata.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            return com.example.shu.attendance1.newHttppost2.makePostRequest(params[0], params[1]);
        }

        @SuppressWarnings("unused")
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject2 = new JSONObject(result);
                JSONObject innerJObject = jsonObject2.getJSONObject("data");
                String name = innerJObject.getString("Name");
                String regno = innerJObject.getString("RegNo");
                String prog = innerJObject.getString("Prog");
                String dept = innerJObject.getString("Dept");
                String sem = innerJObject.getString("Sem");
                String batch = innerJObject.getString("Batch");
                dialogdata.dismiss();
                Log.v("name", name);
                Log.v("name", regno);
                Log.v("name", prog);
                Log.v("name", dept);
                Log.v("name", sem);
                Log.v("name", batch);

                Intent intent = new Intent(options_available.this, Main2Activity.class);
                intent.putExtra("name", name);
                intent.putExtra("regno", regno);
                intent.putExtra("prog", prog);
                intent.putExtra("dept", dept);
                intent.putExtra("sem", sem);
                intent.putExtra("batch", batch);
                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private class newAsynk2 extends AsyncTask<String, String, String> {
        private ProgressDialog dialogdata;

        @Override
        protected void onPreExecute() {

            dialogdata = ProgressDialog.show(mContext, "", "Fetching data...");
            dialogdata.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            return com.example.shu.attendance1.newHttppost2.makePostRequest(params[0], params[1]);
        }

        @SuppressWarnings("unused")
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray innerArray1 = jsonObject.getJSONArray("headers");
                ArrayList<String> headers = new ArrayList<String>();
                if (innerArray1 != null) {
                    for (int j = 0; j < innerArray1.length(); j++) {
                        headers.add((String) innerArray1.get(j));
                    }
                }
                JSONArray innerArray2 = jsonObject.getJSONArray("data");
                ArrayList<String> course_code = new ArrayList<String>();
                ArrayList<String> course_title = new ArrayList<String>();
                ArrayList<String> category = new ArrayList<String>();
                ArrayList<String> faculty = new ArrayList<String>();
                ArrayList<String> slot = new ArrayList<String>();
                ArrayList<String> room_no = new ArrayList<String>();
                ArrayList<String> hours_conducted = new ArrayList<String>();
                ArrayList<String> hours_absent = new ArrayList<String>();
                ArrayList<String> attn = new ArrayList<String>();
                if (innerArray2 != null) {
                    for (int j = 0; j < innerArray2.length(); j++) {
                        JSONArray innerArray3 = innerArray2.getJSONArray(j);


                        course_code.add((String) innerArray3.get(0));
                        course_title.add((String) innerArray3.get(1));
                        category.add((String) innerArray3.get(2));
                        faculty.add((String) innerArray3.get(3));
                        slot.add((String) innerArray3.get(4));
                        room_no.add((String) innerArray3.get(5));
                        hours_conducted.add((String) innerArray3.get(6));
                        hours_absent.add((String) innerArray3.get(7));
                        attn.add((String) innerArray3.get(8));


                    }
                }
                dialogdata.dismiss();
                Intent intent = new Intent(options_available.this, AllDetails.class);
                intent.putStringArrayListExtra("course_code", (ArrayList<String>) course_code);
                intent.putStringArrayListExtra("course_title", (ArrayList<String>) course_title);
                intent.putStringArrayListExtra("category", (ArrayList<String>) category);
                intent.putStringArrayListExtra("faculty", (ArrayList<String>) faculty);
                intent.putStringArrayListExtra("slot", (ArrayList<String>) slot);
                intent.putStringArrayListExtra("room_no", (ArrayList<String>) room_no);
                intent.putStringArrayListExtra("hours_conducted", (ArrayList<String>) hours_conducted);
                intent.putStringArrayListExtra("hours_absent", (ArrayList<String>) hours_absent);
                intent.putStringArrayListExtra("attn", (ArrayList<String>) attn);
                startActivity(intent);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private class newAsynk3 extends AsyncTask<String, String, String> {
        private ProgressDialog dialogdata;

        @Override
        protected void onPreExecute() {

            dialogdata = ProgressDialog.show(mContext, "", "Fetching data...");
            dialogdata.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            return com.example.shu.attendance1.newHttppost2.makePostRequest(params[0], params[1]);
        }

        @SuppressWarnings("unused")
        @Override
        protected void onPostExecute(String result) {
            try {Log.v("Marks",result);
                JSONObject jsonObject = new JSONObject(result);
                JSONArray innerArray1 = jsonObject.getJSONArray("headers");
                ArrayList<String> headers = new ArrayList<String>();
                if (innerArray1 != null) {
                    for (int j = 0; j < innerArray1.length(); j++) {
                        headers.add((String) innerArray1.get(j));
                    }
                }
                JSONArray innerArray2 = jsonObject.getJSONArray("data");
                ArrayList<String> courseCode = new ArrayList<String>();
                ArrayList<String> type = new ArrayList<String>();
                ArrayList<String> ct1 = new ArrayList<String>();
                ArrayList<String> ct2 = new ArrayList<String>();
                if (innerArray2 != null) {
                    for (int j = 0; j < innerArray2.length(); j++) {
                        JSONObject innerObject = innerArray2.getJSONObject(j);


                        courseCode.add(innerObject.getString("courseCode"));
                        type.add(innerObject.getString("subType"));
                        try{ct1.add(innerObject.getString("CT1/10.00"));}
                        catch(Exception e){
                            ct1.add("-");
                        }
                        try{ct2.add(innerObject.getString("CT2/10.00"));}
                        catch(Exception e){
                            ct2.add("-");
                        }


                    }
                }

                dialogdata.dismiss();
                Intent intent = new Intent(options_available.this, AllDetails2.class);
                intent.putStringArrayListExtra("courseCode", (ArrayList<String>) courseCode);
                intent.putStringArrayListExtra("type", (ArrayList<String>) type);
                intent.putStringArrayListExtra("ct1", (ArrayList<String>) ct1);
                intent.putStringArrayListExtra("ct2", (ArrayList<String>) ct2);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

