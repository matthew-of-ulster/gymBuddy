package com.example.gymbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditExcercise extends AppCompatActivity {

    // creating variables for our edittext, button and dbhandler
    private EditText exNameEdt, exWeightEdt, exIncEdt;
    private Button editExBtn,rButton,delButton;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_ex);

        Intent flagIntent = getIntent( );

        delButton = (Button) findViewById(R.id.delButton);
        rButton = (Button) findViewById(R.id.rButton);


        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(EditExcercise.this, MainActivity.class);
                startActivity(switchActivityIntent);
            }
        });


        // initializing all our variables.
        exNameEdt = findViewById(R.id.idEdtExName);
        exWeightEdt = findViewById(R.id.idEdtExWeight);
        exIncEdt = findViewById(R.id.idEdtExInc);
        editExBtn = findViewById(R.id.idBtnAddEx);

        exNameEdt.setText(flagIntent.getStringExtra("name"));
        exWeightEdt.setText(flagIntent.getDoubleExtra("weight",0.0)+"");
        exIncEdt.setText(flagIntent.getDoubleExtra("inc",0.0)+"");


        editExBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String exName = exNameEdt.getText().toString();
                String exWeight = exWeightEdt.getText().toString();
                String exInc = exIncEdt.getText().toString();

                // validating if the text fields are empty or not.
                if (exName.isEmpty() && exWeight.isEmpty() && exInc.isEmpty()) {
                    Toast.makeText(EditExcercise.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }


                db = new DBHandler(EditExcercise.this);
                db.updateLift(flagIntent.getStringExtra("name"), exName, exWeight, exInc);

                exNameEdt.setText("");
                exIncEdt.setText("");
                exWeightEdt.setText("");
            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                exNameEdt.setText("");
                exIncEdt.setText("");
                exWeightEdt.setText("");

                db = new DBHandler(EditExcercise.this);
                db.deleteEx(flagIntent.getStringExtra("name"));

                Toast.makeText(EditExcercise.this, "Deleted the course", Toast.LENGTH_SHORT).show();

                Intent switchActivityIntent = new Intent(EditExcercise.this, MainActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }
}




