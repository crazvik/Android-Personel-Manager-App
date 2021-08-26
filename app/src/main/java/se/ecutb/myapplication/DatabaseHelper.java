package se.ecutb.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "employee_table";

    private static final String COL1 = "id";
    private static final String COL2 = "name";
    private static final String COL3 = "salary";
    private static final String COL4 = "age";

    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " + COL3 + " INTEGER, " + COL4 + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String name, int salary, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, name);
        values.put(COL3, salary);
        values.put(COL4, age);

        Log.d(TAG, "addData: Adding " + name + " " + salary + " " + age + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, values);

        return result != -1;
    }

    public Cursor getItemId(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        return db.rawQuery(query, null);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public void deleteData(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

    public void updateName(String item, int selectedID, String selectedName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 + " = '"
                + item + "' WHERE " + COL1 + " = '" + selectedID
                + "' AND " + COL2 + " = '" + selectedName + "'";
        db.execSQL(query);
    }

    public void updateSalary(String salary, int selectedID, String selectedSalary) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 + " = '"
                + salary + "' WHERE " + COL1 + " = '" + selectedID
                + "' AND " + COL3 + " = '" + selectedSalary + "'";
        db.execSQL(query);
    }

    public void updateAge(String age, int selectedID, String selectedAge) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 + " = '"
                + age + "' WHERE " + COL1 + " = '" + selectedID
                + "' AND " + COL4 + " = '" + selectedAge + "'";
        db.execSQL(query);
    }
}
