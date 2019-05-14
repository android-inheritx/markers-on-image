package com.example.vishalpatel.pinlistdemo;

public class PinModel {


    public String id;
    public double x_cordinate_pin;
    public double y_cordinate_pin;
    public String captionName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getX_cordinate_pin() {
        return x_cordinate_pin;
    }

    public void setX_cordinate_pin(double x_cordinate_pin) {
        this.x_cordinate_pin = x_cordinate_pin;
    }

    public double getY_cordinate_pin() {
        return y_cordinate_pin;
    }

    public void setY_cordinate_pin(double y_cordinate_pin) {
        this.y_cordinate_pin = y_cordinate_pin;
    }

    public String getCaptionName() {
        return captionName;
    }

    public void setCaptionName(String captionName) {
        this.captionName = captionName;
    }
}
