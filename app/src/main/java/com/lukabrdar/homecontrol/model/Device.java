package com.lukabrdar.homecontrol.model;

public class Device {
    public String name;
    public String location;
    public Boolean status;
    public int id;
    public int iconResId;
    private static int counter;

    public Device(String name, String location, Boolean status, int iconResId) {
        this.name = name;
        this.location = location;
        this.status = status;
        this.iconResId = iconResId;
        this.id = counter++;
    }

    public void changeState() {
        this.status = !this.status;
    }
}
