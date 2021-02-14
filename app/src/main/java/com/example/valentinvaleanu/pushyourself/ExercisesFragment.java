package com.example.valentinvaleanu.pushyourself;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Valentin Valeanu on 11/11/2017.
 */

public class ExercisesFragment extends Fragment
{
    public ExercisesFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_muscle, container, false);
    }
}
