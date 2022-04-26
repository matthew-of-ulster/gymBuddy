package com.example.gymbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "excerciseDB";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "excercises";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String NAME_COL = "name";

    // below variable id for our course duration column.
    private static final String WEIGHT_COL = "weight";

    // below variable for our course description column.
    private static final String INC_COL = "increment";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + WEIGHT_COL + " TEXT,"
                + INC_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewCourse(String exName, String exWeight, String exInc) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, exName);
        values.put(WEIGHT_COL, exWeight);
        values.put(INC_COL, exInc);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }


    // we have created a new method for reading all the courses.
    public ArrayList<Lift> readExcercises() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorLifts = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<Lift> liftArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorLifts.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                liftArrayList.add(new Lift(cursorLifts.getString(1),
                        Double.parseDouble(cursorLifts.getString(2)),
                        Double.parseDouble(cursorLifts.getString(3)),
                        0,"",true));
            } while (cursorLifts.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorLifts.close();
        return liftArrayList;
    }

    // below is the method for updating our courses
    public void updateLift(String originalName, String name, String weight, String increment) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, name);
        values.put(WEIGHT_COL, weight);
        values.put(INC_COL, increment);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TABLE_NAME, values, "name=?", new String[]{originalName});
        db.close();
    }

    // below is the method for deleting our course.
    public void deleteEx(String courseName) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, "name=?", new String[]{courseName});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
