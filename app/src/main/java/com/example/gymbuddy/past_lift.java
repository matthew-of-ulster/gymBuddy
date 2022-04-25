package com.example.gymbuddy;

import java.text.SimpleDateFormat;

public class past_lift {
    boolean successful;
    String dateAttempted;
    double weight;

    public past_lift(boolean successful, double weight) {
        this.successful = successful;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(System.currentTimeMillis());
        dateAttempted = date;
        this.weight = weight;
    }
}
