package com.example.valentinvaleanu.pushyourself;

/**
 * Created by Tina on 07-Nov-17.
 */

public class ExerciseWithImage
{
    private String name;
    private String imgSrc;

    public ExerciseWithImage(String name, String imgSrc) {
        this.name = name;
        this.imgSrc = imgSrc;
    }

    public void setImgSrc(String imgSrc) {

        this.imgSrc = imgSrc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {

        return imgSrc;
    }

    public String getName() {
        return name;
    }
}
