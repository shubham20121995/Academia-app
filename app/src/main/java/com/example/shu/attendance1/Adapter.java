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
public class Adapter extends BaseAdapter {

    Context context;

    ArrayList course_code,course_title,category,faculty,slot,room_no,hours_conducted,hours_absent,attn;
    Adapter(Context context,ArrayList<String> course_code,ArrayList<String> course_title,ArrayList<String> category,ArrayList<String> faculty,ArrayList<String> slot,ArrayList<String> room_no,ArrayList<String> hours_conducted,ArrayList<String> hours_absent,ArrayList<String> attn)
    {

        this.context=context;
        this.course_code=course_code;
        this.course_title=course_title;
        this.category=category;
        this.faculty=faculty;
        this.slot=slot;
        this.room_no=room_no;
        this.hours_conducted=hours_conducted;
        this.hours_absent=hours_absent;
        this.attn=attn;


    }
    @Override
    public int getCount() {
        return course_code.size();
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
        View v=inflate.inflate(R.layout.alldetails2,null);
        h.course_code=(TextView)v.findViewById(R.id.course_code);
        h.course_title=(TextView)v.findViewById(R.id.course_title);
        h.category=(TextView)v.findViewById(R.id.category);
        h.faculty=(TextView)v.findViewById(R.id.faculty);
        h.slot=(TextView)v.findViewById(R.id.slot);
        h.room_no=(TextView)v.findViewById(R.id.room_no);
        h.hours_conducted=(TextView)v.findViewById(R.id.hours_conducted);
        h.hours_absent=(TextView)v.findViewById(R.id.hours_absent);
        h.attn=(TextView)v.findViewById(R.id.attn);

        String s1 = "<b>" + "COURSE CODE :" + "</b> " + (String) course_code.get(position);
        h.course_code.setText(Html.fromHtml(s1));
        String s2 = "<b>" + "COURSE TITLE : " + "</b> " + (String) course_title.get(position);
        h.course_title.setText(Html.fromHtml(s2));
        String s3 = "<b>" + "CATEGORY : " + "</b> " + (String) category.get(position);
        h.category.setText(Html.fromHtml(s3));
        String s4 = "<b>" + "FACULTY : " + "</b> " + (String) faculty.get(position);
        h.faculty.setText(Html.fromHtml(s4));
        String s5 = "<b>" + "SLOT : " + "</b> " + (String) slot.get(position);
        h.slot.setText(Html.fromHtml(s5));
        String s6 = "<b>" + "ROOM NO : " + "</b> " + (String) room_no.get(position);
        h.room_no.setText(Html.fromHtml(s6));
        String s7 = "<b>" + "HOURS CONDUCTED : " + "</b> " + (String) hours_conducted.get(position);
        h.hours_conducted.setText(Html.fromHtml(s7));
        String s8 = "<b>" + "HOURS ABSENT : " + "</b> " + (String) hours_absent.get(position);
        h.hours_absent.setText(Html.fromHtml(s8));
        String s9 = "<b>" + "ATTENDANCE : " + "</b> " + (String) attn.get(position);
        h.attn.setText(Html.fromHtml(s9));




        return v;
    }
    class holderid{

        TextView course_code,course_title,category,faculty,slot,room_no,hours_conducted,hours_absent,attn;

    }
}

