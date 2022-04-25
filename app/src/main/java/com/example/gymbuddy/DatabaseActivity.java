package com.example.gymbuddy;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DatabaseActivity extends AppCompatActivity {

    Button rButton;
    // creating variables for our edittext, button and dbhandler
    private EditText exNameEdt, exWeightEdt, exIncEdt;
    private Button addExBtn;
    private DBHandler dbHandler;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.database_view);

            rButton = (Button) findViewById(R.id.rButton);


            rButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent switchActivityIntent = new Intent(DatabaseActivity.this, MainActivity.class);
                    startActivity(switchActivityIntent);
                }
            });


                    // initializing all our variables.
                    exNameEdt = findViewById(R.id.idEdtExName);
                    exWeightEdt = findViewById(R.id.idEdtExWeight);
                    exIncEdt = findViewById(R.id.idEdtExInc);
                    addExBtn = findViewById(R.id.idBtnAddEx);

                    // creating a new dbhandler class
                    // and passing our context to it.
                    dbHandler = new DBHandler(DatabaseActivity.this);

                    // below line is to add on click listener for our add course button.
                    addExBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            // below line is to get data from all edit text fields.
                            String exName = exNameEdt.getText().toString();
                            String exWeight = exWeightEdt.getText().toString();
                            String exInc = exIncEdt.getText().toString();

                            // validating if the text fields are empty or not.
                            if (exName.isEmpty() && exWeight.isEmpty() && exInc.isEmpty()) {
                                Toast.makeText(DatabaseActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // on below line we are calling a method to add new
                            // course to sqlite data and pass all our values to it.
                            dbHandler.addNewCourse(exName, exWeight,exInc );

                            // after adding the data we are displaying a toast message.
                            Toast.makeText(DatabaseActivity.this, "Excercise has been added.", Toast.LENGTH_SHORT).show();
                            exNameEdt.setText("");
                            exIncEdt.setText("");
                            exWeightEdt.setText("");
                        }
                    });
                }
            }




