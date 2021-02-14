package com.example.valentinvaleanu.pushyourself;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tina on 07-Nov-17.
 */

public class ExercisesWithImageAdapter extends ArrayAdapter<ExerciseWithImage> {


    public ExercisesWithImageAdapter(Activity context, ArrayList<ExerciseWithImage> exercises) {
        super(context,0, exercises);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View listItemView = convertView;

        if(listItemView == null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.custom_layout_exercises,parent,false);
        }

        ExerciseWithImage currentExercise = getItem(position);

        ImageView icon=(ImageView)listItemView.findViewById(R.id.img_exercise);
        int imageID = getResourceId(getContext(), currentExercise.getImgSrc(), "drawable", getContext().getPackageName());

        icon.setImageResource(imageID);

        TextView name=(TextView)listItemView.findViewById(R.id.text_exercise);
        name.setText(currentExercise.getName());

        return listItemView;
    }

    private static int getResourceId(Context context, String pVariableName, String pResourcename, String pPackageName) throws RuntimeException {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Resource ID.", e);
        }
    }

}
