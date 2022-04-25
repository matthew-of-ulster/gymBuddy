package com.example.gymbuddy;

import java.text.SimpleDateFormat;

public class PastLift {
    boolean successful;
    String dateAttempted;
    double weight;

    public PastLift(boolean successful, double weight) {
        this.successful = successful;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(System.currentTimeMillis());
        dateAttempted = date;
        this.weight = weight;
    }
}
