package com.example.gymbuddy;

import java.util.ArrayList;

public class Lift {
    String name;
    double weight;
    double increment;
    //how many weeks before it loops
    int rWeek;
    String active_days;
    ArrayList<PastLift>  past_lifts = new ArrayList<>();
    boolean visible;

    public Lift(String name, double weight, double increment, int rWeek, String active_days,boolean visible) {
        this.name = name;
        this.weight = weight;
        this.rWeek = rWeek;
        this.increment=increment;
        this.active_days = active_days;
        this.visible=visible;
    }
    public Lift(String test){
    this.name = "name"+test;
    this.weight = Double.parseDouble(test);
    }
}
