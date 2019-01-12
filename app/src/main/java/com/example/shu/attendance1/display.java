package com.example.shu.attendance1;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by Shu on 22/09/2016.
 */
public class display extends Fragment {
    View v;
    ProgressDialog dialogdata;
    Context mContext;
    TextView name1;
    TextView regno1;
    TextView prog1;
    TextView dept1;
    TextView sem1;
    TextView batch1;
    String name ;
    String regno;
    String prog ;
    String dept;
    String sem;
    String batch ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.display,container,false);
        Bundle bundle = this.getArguments();


        return v ;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        HTTPPostActivity a=new HTTPPostActivity();
        final String token=HTTPPostActivity.getActivityInstance().getToken();
        Log.v("Token", token);

        DatabaseHandler db=new DatabaseHandler(getActivity());
        Cursor c=db.viewInfo();

        if(c.getCount()>0) {

            if (c.moveToFirst())
                do {

                    name = c.getString(c.getColumnIndex("name"));
                    regno = c.getString(c.getColumnIndex("regno"));
                    prog = c.getString(c.getColumnIndex("prog"));
                    dept = c.getString(c.getColumnIndex("dept"));
                    sem = c.getString(c.getColumnIndex("sem"));
                    batch = c.getString(c.getColumnIndex("batch"));
                    name1=(TextView)getView().findViewById(R.id.name);
                    regno1=(TextView)getView().findViewById(R.id.regno);
                    prog1=(TextView)getView().findViewById(R.id.prog);
                    dept1=(TextView)getView().findViewById(R.id.dept);
                    sem1=(TextView)getView().findViewById(R.id.sem);
                    batch1=(TextView)getView().findViewById(R.id.batch);

                    name1.setText(name);
                    regno1.setText(regno);
                    prog1.setText(prog);
                    dept1.setText(dept);
                    sem1.setText(sem);
                    batch1.setText(batch);


                } while (c.moveToNext());


        }
        else{
            new newAsynk().execute("http://139.59.12.194:3001/info", token);
        }
        FloatingActionButton fab = (FloatingActionButton)getView().findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new newAsynk().execute("http://139.59.12.194:3001/info", token);
            }
        });
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private class newAsynk extends AsyncTask<String, String, String> {



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
                    Log.v("String", result);
                    JSONObject jsonObject2 = new JSONObject(result);
                    JSONObject innerJObject = jsonObject2.getJSONObject("data");
                    name = innerJObject.getString("Name");
                    regno = innerJObject.getString("RegNo");
                    prog = innerJObject.getString("Prog");
                    dept = innerJObject.getString("Dept");
                    sem = innerJObject.getString("Sem");
                    batch = innerJObject.getString("Batch");
                    DatabaseHandler bd = new DatabaseHandler(getActivity());
                    bd.addInfo(name, regno, prog, dept, sem, batch);
                    name1 = (TextView) getView().findViewById(R.id.name);
                    regno1 = (TextView) getView().findViewById(R.id.regno);
                    prog1 = (TextView) getView().findViewById(R.id.prog);
                    dept1 = (TextView) getView().findViewById(R.id.dept);
                    sem1 = (TextView) getView().findViewById(R.id.sem);
                    batch1 = (TextView) getView().findViewById(R.id.batch);
                    name1.setText(name);
                    regno1.setText(regno);
                    prog1.setText(prog);
                    dept1.setText(dept);
                    sem1.setText(sem);
                    batch1.setText(batch);
                    dialogdata.dismiss();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{Toast.makeText(getActivity(), "No record", Toast.LENGTH_SHORT).show();

                dialogdata.dismiss();}
            }

        public String getName() {
            return name;
        }
        public String getProg() {
            return prog;
        }
        public String getRegno() {
            return regno;
        }
        public String getSem() {
            return sem;
        }
        public String getBatch() {
            return batch;
        }
        public String getDept() {
            return dept;
        }
    }
}
