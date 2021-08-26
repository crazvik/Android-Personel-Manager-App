package se.ecutb.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EditEmployee extends AppCompatActivity {
    private EditText newName;
    private EditText newSalary;
    private EditText newAge;
    private Button update;
    private Button delete;

    DatabaseHelper databaseHelper;

    private String selectedName;
    private String selectedSalary;
    private String selectedAge;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        newName = (EditText) findViewById(R.id.newName);
        newSalary = (EditText) findViewById(R.id.newSalary);
        newAge = (EditText) findViewById(R.id.newAge);
        update = (Button) findViewById(R.id.updateButton);
        delete = (Button) findViewById(R.id.deleteButton);
        databaseHelper = new DatabaseHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedName = receivedIntent.getStringExtra("name");
        selectedSalary = receivedIntent.getStringExtra("salary");
        selectedAge = receivedIntent.getStringExtra("age");

        newName.setText(selectedName);
        newSalary.setText(selectedSalary);
        newAge.setText(selectedAge);

        update.setOnClickListener(view -> {
            String name = newName.getText().toString();
            String salary = newSalary.getText().toString();
            String age = newAge.getText().toString();
            if(!name.equals("") || !salary.equals("") || !age.equals("")) {
                databaseHelper.updateName(name, selectedID, selectedName);
                databaseHelper.updateSalary(salary, selectedID, selectedSalary);
                databaseHelper.updateAge(age, selectedID, selectedAge);
            } else toastMessage("You must enter a name");
            startActivity(new Intent(EditEmployee.this, NewEmployees.class));
        });

        delete.setOnClickListener(view -> {
            databaseHelper.deleteData(selectedID, selectedName);
            newName.setText("");
            newSalary.setText("");
            newAge.setText("");
            startActivity(new Intent(EditEmployee.this, NewEmployees.class));
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.database:
                    startActivity(new Intent(getApplicationContext(),
                            Add_Employee.class));
                    overridePendingTransition(0, 0);
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
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}