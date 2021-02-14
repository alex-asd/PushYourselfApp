package com.example.valentinvaleanu.pushyourself;

import android.provider.ContactsContract;

/**
 * Created by Valentin Valeanu on 11/13/2017.
 */

public class Exercise
{
    private String Name;
    private String Difficulty;
    private String MuscleGroup;
    private String Description;
    private String ImageID;

    public Exercise()
    {

    }

    public Exercise(String Name, String Difficulty, String MuscleGroup, String Description, String ImageID) {
        this.Name = Name;
        this.Difficulty = Difficulty;
        this.MuscleGroup = MuscleGroup;
        this.Description = Description;
        this.ImageID = ImageID;
    }

    public String getName()
    {
        return Name;
    }

    public String getDifficulty()
    {
        return Difficulty;
    }

    public String getMuscleGroup()
    {
        return MuscleGroup;
    }

    public String getDescription()
    {
        return Description;
    }

    public String getImageID() { return ImageID; }

    public void setName(String Name)
    {
        this.Name = Name;
    }

    public void setDifficulty(String Difficulty)
    {
        this.Difficulty = Difficulty;
    }

    public void setMuscleGroup(String MuscleGroup)
    {
        this.MuscleGroup = MuscleGroup;
    }

    public void setDescription(String Description)
    {
        this.Description = Description;
    }

    public void setImageID(String ImageID) { this.ImageID = ImageID; }

    public String toString()
    {
        return "Name " + Name + " , Description " + Description + " , MuscleGroup " + MuscleGroup + " , Difficulty " + Difficulty;
    }
}
