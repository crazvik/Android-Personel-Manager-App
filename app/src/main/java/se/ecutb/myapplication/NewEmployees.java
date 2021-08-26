package se.ecutb.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class NewEmployees extends AppCompatActivity {
    private static final String TAG = "New Employees";
    DatabaseHelper databaseHelper;
    private ListView listView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);

        populateListView();

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
                    return true;
            }
            return false;
        });
    }

    private void populateListView() {
        Cursor data = databaseHelper.getData();
        ArrayList<Employee> listData = new ArrayList<>();
        while (data.moveToNext()) {
            Employee employee = new Employee(data.getString(1), data.getString(2), data.getString(3));
            listData.add(employee);
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Employee employee = (Employee) adapterView.getItemAtPosition(i);
            Log.d(TAG, adapterView.getItemAtPosition(i).toString());
            Cursor data1 = databaseHelper.getItemId(employee.getName());
            int itemId = -1;
            while (data1.moveToNext()) {
                itemId = data1.getInt(0);
            }
            if(itemId>-1) {
                Log.d(TAG, "onItemClick: The ID is: " + itemId);
                Intent intent = new Intent(NewEmployees.this, EditEmployee.class);
                intent.putExtra("id",itemId);
                intent.putExtra("name", employee.getName());
                intent.putExtra("salary", employee.getSalary());
                intent.putExtra("age", employee.getAge());
                startActivity(intent);
            }
        });
    }

}