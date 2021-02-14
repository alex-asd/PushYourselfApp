package com.example.valentinvaleanu.pushyourself;

/**
 * Created by Valentin Valeanu on 11/23/2017.
 */

public class ExerciseWithImageAndStatus
{
    private String name;
    private String imgSrc;
    private String status;

    public ExerciseWithImageAndStatus(String name, String imgSrc, String status) {
        this.name = name;
        this.imgSrc = imgSrc;
        this.status = status;
    }

    public void setImgSrc(String imgSrc) {

        this.imgSrc = imgSrc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) { this.status = status; }

    public String getImgSrc() {

        return imgSrc;
    }

    public String getName() {
        return name;
    }

    public String getStatus() { return status; }
}
