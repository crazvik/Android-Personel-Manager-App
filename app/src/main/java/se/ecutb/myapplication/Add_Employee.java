package se.ecutb.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Add_Employee extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private EditText editName, editSalary, editAge;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        editName = (EditText) findViewById(R.id.newName);
        editSalary = (EditText) findViewById(R.id.newSalary);
        editAge = (EditText) findViewById(R.id.newAge);
        Button submit = (Button) findViewById(R.id.submitButton);
        databaseHelper = new DatabaseHelper(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.database);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.database:
                    return true;
                case R.id.currentEmployees:
                    startActivity(new Intent(getApplicationContext(),
                            CurrentEmployees.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(),
                            NewEmployees.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        submit.setOnClickListener(v -> {
            String newName = editName.getText().toString();
            int newSalary = Integer.parseInt(editSalary.getText().toString());
            int newAge = Integer.parseInt(editAge.getText().toString());

            if (editName.length() != 0 || editSalary.length() != 0 || editAge.length() != 0) {
                AddData(newName, newSalary, newAge);
                editName.setText("");
                editSalary.setText("");
                editAge.setText("");
            } else {
                toastMessage("You must put something in the text field");
            }

            Intent intent = new Intent(Add_Employee.this, NewEmployees.class);
            startActivity(intent);
        });


    }

    public void AddData(String newName, int newSalary, int newAge) {
        boolean insertData =  databaseHelper.addData(newName, newSalary, newAge);
        if (insertData) {
            toastMessage("Data Successfully Inserted");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}