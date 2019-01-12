package com.example.shu.attendance1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shu on 23/09/2016.
 */
public class AllDetails extends Fragment {
    ListView list;
    Adapter a;
    View v;

    ArrayList<String> course_code = new ArrayList<String>();
    ArrayList<String> course_title = new ArrayList<String>();
    ArrayList<String> category = new ArrayList<String>();
    ArrayList<String> faculty = new ArrayList<String>();
    ArrayList<String> slot = new ArrayList<String>();
    ArrayList<String> room_no = new ArrayList<String>();
    ArrayList<String> hours_conducted = new ArrayList<String>();
    ArrayList<String> hours_absent = new ArrayList<String>();
    ArrayList<String> attn = new ArrayList<String>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.all_details,container,false);

        return v ;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HTTPPostActivity a=new HTTPPostActivity();
        final String token=HTTPPostActivity.getActivityInstance().getToken();
        Log.v("Token", token);
        DatabaseHandler db=new DatabaseHandler(getActivity());
        Cursor c=db.viewAttendance();

        if(c.getCount()>0) {

            if (c.moveToFirst())
                do {

                    String Course_code = c.getString(c.getColumnIndex("course_code"));
                    course_code.add(Course_code);
                    String Course_title = c.getString(c.getColumnIndex("title"));
                    course_title.add(Course_title);
                    String Category = c.getString(c.getColumnIndex("category"));
                    category.add(Category);
                    String Faculty = c.getString(c.getColumnIndex("faculty"));
                    faculty.add(Faculty);
                    String Slot = c.getString(c.getColumnIndex("slot"));
                    slot.add(Slot);
                    String Room_no = c.getString(c.getColumnIndex("room_no"));
                    room_no.add(Room_no);
                    String Hours_conducted = c.getString(c.getColumnIndex("hours_conducted"));
                    hours_conducted.add(Hours_conducted);
                    String Hours_absent = c.getString(c.getColumnIndex("hours_absent"));
                    hours_absent.add(Hours_absent);
                    String Attn = c.getString(c.getColumnIndex("attn"));
                    attn.add(Attn);


                } while (c.moveToNext());
            list=(ListView)getView().findViewById(R.id.listview);
            Adapter b;

            b=new Adapter(getActivity(),course_code,course_title,category,faculty,slot,room_no,hours_conducted,hours_absent,attn);
            list.setAdapter(b);

        }
        else{
            new newAsynk2().execute("http://139.59.12.194:3001/attendance", token);
        }
        FloatingActionButton fab = (FloatingActionButton)getView().findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new newAsynk2().execute("http://139.59.12.194:3001/attendance", token);
            }
        });

    }

    private class newAsynk2 extends AsyncTask<String, String, String> {
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
                JSONObject jsonObject = new JSONObject(result);
                JSONArray innerArray1 = jsonObject.getJSONArray("headers");
                ArrayList<String> headers = new ArrayList<String>();
                if (innerArray1 != null) {
                    for (int j = 0; j < innerArray1.length(); j++) {
                        headers.add((String) innerArray1.get(j));
                    }
                }
                course_code.clear();
                course_title.clear();
                category.clear();
                faculty.clear();
                slot.clear();
                room_no.clear();
                hours_conducted.clear();
                hours_absent.clear();
                attn.clear();
                JSONArray innerArray2 = jsonObject.getJSONArray("data");

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
                DatabaseHandler db = new DatabaseHandler(getActivity());
                db.refreshAttendance();
                for (int i = 0; i < course_code.size(); i++) {
                    db.addAttendance(course_code.get(i), course_title.get(i), category.get(i), faculty.get(i), slot.get(i),
                            room_no.get(i), hours_conducted.get(i), hours_absent.get(i), attn.get(i));
                }
                list = (ListView) getView().findViewById(R.id.listview);
                a = new Adapter(getActivity(), course_code, course_title, category, faculty, slot, room_no, hours_conducted, hours_absent, attn);
                list.setAdapter(a);
                dialogdata.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            else{Toast.makeText(getActivity(), "No record", Toast.LENGTH_SHORT).show();

                dialogdata.dismiss();}
        }


    }


}
