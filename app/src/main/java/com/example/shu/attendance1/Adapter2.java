package com.example.shu.attendance1;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.jar.Attributes;

/**
 * Created by Shu on 23/09/2016.
 */
public class Adapter2 extends BaseAdapter {

    Context context;

    ArrayList courseCode,type,ct1,ct2;
    Adapter2(Context context,ArrayList<String> courseCode,ArrayList<String> type,ArrayList<String> ct1,ArrayList<String> ct2)
    {

        this.context=context;
        this.courseCode=courseCode;
        this.type=type;
        this.ct1=ct1;
        this.ct2=ct2;



    }
    @Override
    public int getCount() {
        return courseCode.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holderid h=new holderid();

        LayoutInflater inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflate.inflate(R.layout.marksdetails2,null);
        h.courseCode=(TextView)v.findViewById(R.id.courseCode);
        h.type=(TextView)v.findViewById(R.id.type);
        h.ct1=(TextView)v.findViewById(R.id.ct1);
        h.ct2=(TextView)v.findViewById(R.id.ct2);


        String s1 = "<b>" + "COURSE CODE :" + "</b> " + (String) courseCode.get(position);
        h.courseCode.setText(Html.fromHtml(s1));
        String s2 = "<b>" + "TYPE : " + "</b> " + (String) type.get(position);
        h.type.setText(Html.fromHtml(s2));
        String s3 = "<b>" + "CT1 : " + "</b> " + (String) ct1.get(position);
        h.ct1.setText(Html.fromHtml(s3));
        String s4 = "<b>" + "CT2 : " + "</b> " + (String) ct2.get(position);
        h.ct2.setText(Html.fromHtml(s4));

        return v;
    }
    class holderid{

        TextView courseCode,type,ct1,ct2;

    }
}

