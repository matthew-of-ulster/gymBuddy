package com.example.gymbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String ETN20,ETN15,ETN10,ETN5,ETN2_5,ETN1_25;
    // initialize SharedPreferences var
    SharedPreferences sharedPref;
    EditText ex1ET,ex2ET,ex3ET;
    EditText ET20,ET15,ET10,ET5,ET2_5,ET1_25;
    String data;
    Button weightsConfirmBut, db_view;
    private ArrayList<Lift> fromDBArrayList;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = getSharedPreferences("data", MODE_PRIVATE);
        String data = sharedPref.getString("data", "default if empty");

        if(data.length()<20) {
            data = "4:4:4:4:4:4,60:Squat,30:Dead lift,40:Shoulder Press,90:Bench,a";
        }

        sharedPref.edit().putString("data", data).commit();
        String[] dataArray = data.split(",");

        ET20=(EditText)findViewById(R.id.ET20);
        ET15=(EditText)findViewById(R.id.ET15);
        ET10=(EditText)findViewById(R.id.ET10);
        ET5=(EditText)findViewById(R.id.ET5);
        ET2_5=(EditText)findViewById(R.id.ET2_5);
        ET1_25=(EditText)findViewById(R.id.ET1_25);

        ETN20 = ET20.getText().toString();
        ETN15 = ET15.getText().toString();
        ETN10 = ET10.getText().toString();
        ETN5 = ET5.getText().toString();
        ETN2_5 = ET2_5.getText().toString();
        ETN1_25 = ET1_25.getText().toString();

        String[] wA=dataArray[0].split(":");

        ET20.setText(wA[0]);
        ET15.setText(wA[1]);
        ET10.setText(wA[2]);
        ET5.setText(wA[3]);
        ET2_5.setText(wA[4]);
        ET1_25.setText(wA[5]);

        weightsConfirmBut = (Button) findViewById(R.id.weightsConfirmBut);
        db_view = (Button) findViewById(R.id.database_btn);



        fromDBArrayList = new ArrayList<>();
        dbHandler = new DBHandler(MainActivity.this);

        fromDBArrayList = dbHandler.readExcercises();



        ArrayList<Lift> arrayOfLifts = new ArrayList<>();
        ArrayList<PastLift> arrayOfPastLift = new ArrayList<>();


        for(int i =0;i<fromDBArrayList.size();i++){
            if(fromDBArrayList.get(i).visible) {
                arrayOfLifts.add(fromDBArrayList.get(i));
            }
        }


        // Create the adapter to convert the array to views
        LiftAdapter adapter = new LiftAdapter(this, arrayOfLifts);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(adapter);


        weightsConfirmBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ETN20 = ET20.getText().toString();
                ETN15 = ET15.getText().toString();
                ETN10 = ET10.getText().toString();
                ETN5 = ET5.getText().toString();
                ETN2_5 = ET2_5.getText().toString();
                ETN1_25 = ET1_25.getText().toString();
            }
        });
        db_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ETN20 = ET20.getText().toString();

                Intent switchActivityIntent = new Intent(MainActivity.this, DatabaseActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }

    public static String deriveWeights(String text) {
        //Get The Total Weight to be Lifted
        String rString = text;

        double total = (Double.parseDouble(rString)-20)/2;
        rString="";
        /*Set total amount of pairs of plates 20,15,10,5,2.5,1.25
        Future Derive From DB
        */


        int[] weightsAvailable = {Integer.parseInt(ETN20), Integer.parseInt(ETN15), Integer.parseInt(ETN10), Integer.parseInt(ETN5), Integer.parseInt(ETN2_5), Integer.parseInt(ETN1_25)};

        double[] weightSize = {20,15,10,5,2.5,1.25};

        //Counter for Amount of each pair used
        int[] totalUsed = {0,0,0,0,0,0};


        for(int i =0;i<weightsAvailable.length;i++){
            for(int j=0;j<weightsAvailable[i];j++){
                    if(total-weightSize[i]>=0){
                        total=total-weightSize[i];
                        totalUsed[i]++;
                    }
                }
        }

        int count = 0;
        for(int i=0;i<totalUsed.length;i++){
            if(totalUsed[i]!=0){
                //add comma before every weight except first one
                count++;
                if(count>1){
                    rString+=", ";
                }

                rString+=  totalUsed[i] +":";
            if(weightSize[i] - (int)weightSize[i] ==0) {
                rString += (int) weightSize[i];
            }else{
                rString +=weightSize[i];
            }
            }
        }
        return rString;
    }
}
