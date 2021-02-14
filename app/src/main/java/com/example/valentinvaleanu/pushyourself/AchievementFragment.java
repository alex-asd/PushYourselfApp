package com.example.valentinvaleanu.pushyourself;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Valentin Valeanu on 11/11/2017.
 */

public class AchievementFragment extends Fragment
{
    private ListView list;
    private String[] titles;
    private String [] description;
    private int[] imgs = {R.drawable.bronze_medal, R.drawable.silver_medal, R.drawable.gold_medal, R.drawable.diamond_medal, R.drawable.abs_achievement,R.drawable.back_achievement,R.drawable.cardio_achievement,R.drawable.chest_achievement,
            R.drawable.legs_achievement,R.drawable.abs_achievement2, R.drawable.diffucult_achievement, R.drawable.time_achievement};

    public AchievementFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_achievements, container, false);
        Resources res = getResources();
        titles = res.getStringArray(R.array.titles);
        description = res.getStringArray(R.array.description);
        list = (ListView) v.findViewById(R.id.list1);
        MyAdapter adapter = new MyAdapter(this.getActivity(),titles,imgs,description);
        list.setAdapter(adapter);
        return v;
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        int [] images;
        String [] mytitles;
        String [] myDescriptions;
        MyAdapter(Context c,String[] titles,int imgs[],String[]desc)
        {
            super(c,R.layout.row,R.id.text1,titles);
            this.context=c;
            this.images=imgs;
            this.mytitles=titles;
            this.myDescriptions=desc;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.row,parent,false);
            ImageView myImage = (ImageView) row.findViewById(R.id.icon);
            TextView myTitle = (TextView) row.findViewById(R.id.text1);
            TextView myDesc = (TextView) row.findViewById(R.id.text2);
            myImage.setImageResource(images[position]);
            myTitle.setText(mytitles[position]);
            myDesc.setText(myDescriptions[position]);
            return row;
        }
    }
}
