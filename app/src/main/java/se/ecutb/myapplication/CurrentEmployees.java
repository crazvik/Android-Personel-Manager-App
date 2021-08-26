package se.ecutb.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CurrentEmployees extends AppCompatActivity {
    private static final String URL = "https://dummy.restapiexample.com/api/v1/employees";

    private ListView listView;
    private final ArrayList<Employee> listData = new ArrayList<>();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_employees);
        listView = (ListView) findViewById(R.id.employeeView);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    try {
                        JSONArray data = response.getJSONArray("data");
                        for (int i=0; i<data.length(); i++) {
                            JSONObject user = data.getJSONObject(i);
                            Employee employee = new Employee();
                            employee.setName(user.getString("employee_name"));
                            employee.setSalary(user.getString("employee_salary"));
                            employee.setAge(user.getString("employee_age"));
                            listData.add(employee);
                        }
                        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
                        listView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        requestQueue.add(getRequest);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.currentEmployees);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.database:
                    startActivity(new Intent(getApplicationContext(),
                            Add_Employee.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.currentEmployees:
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
}