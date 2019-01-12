package com.example.shu.attendance1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import android.app.Fragment;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Shu on 23/09/2016.
 */
public class AllDetails2 extends Fragment {
    ListView list;
    Adapter2 a;
    View v;
    ArrayList<String> courseCode = new ArrayList<String>();
    ArrayList<String> type = new ArrayList<String>();
    ArrayList<String> ct1 = new ArrayList<String>();
    ArrayList<String> ct2 = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.marks_details,container,false);
        return v;
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HTTPPostActivity a=new HTTPPostActivity();
        final String token=HTTPPostActivity.getActivityInstance().getToken();
        Log.v("Token", token);
        DatabaseHandler db=new DatabaseHandler(getActivity());
        Cursor c=db.viewMarks();

        if(c.getCount()>0) {

            if (c.moveToFirst())
                do {

                    String Course_code = c.getString(c.getColumnIndex("coursecode"));
                    courseCode.add(Course_code);
                    String Course_title = c.getString(c.getColumnIndex("subtype"));
                    type.add(Course_title);
                    String Category = c.getString(c.getColumnIndex("ct1"));
                    ct1.add(Category);
                    String Faculty = c.getString(c.getColumnIndex("ct2"));
                    ct2.add(Faculty);


                } while (c.moveToNext());
            list=(ListView)getView().findViewById(R.id.listview);
            Adapter2 b;

            b=new Adapter2(getActivity(),courseCode,type,ct1,ct2);
            list.setAdapter(b);

        }
        else{
            new newAsynk3().execute("http://139.59.12.194:3001/marks", token);
        }
        FloatingActionButton fab = (FloatingActionButton)getView().findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new newAsynk3().execute("http://139.59.12.194:3001/marks", token);
            }
        });

    }

    private class newAsynk3 extends AsyncTask<String, String, String> {
        private ProgressDialog dialogdata;

        @Override
        protected void onPreExecute() {

            dialogdata = ProgressDialog.show(getActivity(), "", "Fetching data...");
            dialogdata.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {
            return com.example.shu.attendance1.newHttppost2.makePostRequest(params[0], params[1]);
        }

        @SuppressWarnings("unused")
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
            try {
                Log.v("Marks", result);
                JSONObject jsonObject = new JSONObject(result);
                JSONArray innerArray1 = jsonObject.getJSONArray("headers");
                ArrayList<String> headers = new ArrayList<String>();
                if (innerArray1 != null) {
                    for (int j = 0; j < innerArray1.length(); j++) {
                        headers.add((String) innerArray1.get(j));
                    }
                }
                JSONArray innerArray2 = jsonObject.getJSONArray("data");
                courseCode.clear();
                type.clear();
                ct1.clear();
                ct2.clear();

                if (innerArray2 != null) {
                    for (int j = 0; j < innerArray2.length(); j++) {
                        JSONObject innerObject = innerArray2.getJSONObject(j);
                       courseCode.add(innerObject.getString("courseCode"));
                        type.add(innerObject.getString("subType"));
                        try {
                            ct1.add(innerObject.getString("CT1/10.00"));
                        } catch (Exception e) {
                            ct1.add("-");
                        }
                        try {
                            ct2.add(innerObject.getString("CT2/10.00"));
                        } catch (Exception e) {
                            ct2.add("-");
                        }


                    }
                }

                DatabaseHandler db = new DatabaseHandler(getActivity());
                db.refreshMarks();
                for (int i = 0; i < courseCode.size(); i++) {
                    db.addMarks(courseCode.get(i), type.get(i), ct1.get(i), ct2.get(i));
                }
                list = (ListView) getView().findViewById(R.id.listview);

                a = new Adapter2(getActivity(), courseCode, type, ct1, ct2);
                list.setAdapter(a);
                dialogdata.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            else{
                Toast.makeText(getActivity(), "No record", Toast.LENGTH_SHORT).show();

                dialogdata.dismiss();}
        }
    }
}
