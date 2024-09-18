package com.genuinecoder.springclient;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.genuinecoder.springclient.model.Employee;
import com.genuinecoder.springclient.reotrfit.EmployeeApi;
import com.genuinecoder.springclient.reotrfit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEmployeeActivity extends AppCompatActivity {

    private EditText editName, editLocation, editBranch;
    private Button btnUpdate;
    private EmployeeApi employeeApi;
    private Integer employeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);

        // Initialize views
        editName = findViewById(R.id.editName);
        editLocation = findViewById(R.id.editLocation);
        editBranch = findViewById(R.id.editBranch);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Initialize Retrofit and API
        RetrofitService retrofitService = new RetrofitService();
        employeeApi = retrofitService.getRetrofit().create(EmployeeApi.class);

        // Get the employee ID from the intent
//        employeeId = getIntent().getLongExtra("employeeId", -1);

        employeeId = getIntent().getIntExtra("EMPLOYEE_ID", -1);

        // Fetch and populate employee details
        fetchEmployeeDetails(employeeId);

        // Handle the update button click
        btnUpdate.setOnClickListener(v -> updateEmployee());
    }

    private void fetchEmployeeDetails(Integer id) {
        employeeApi.getStudentById(id).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Employee employee = response.body();
                    editName.setText(employee.getName());
                    editLocation.setText(employee.getLocation());
                    editBranch.setText(employee.getBranch());
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(UpdateEmployeeActivity.this, "Failed to load student details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateEmployee() {
        String name = editName.getText().toString();
        String location = editLocation.getText().toString();
        String branch = editBranch.getText().toString();

        if (name.isEmpty() || location.isEmpty() || branch.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create updated Employee object
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setName(name);
        employee.setLocation(location);
        employee.setBranch(branch);

        // Make API call to update the employee
        employeeApi.updateEmployee(employeeId, employee).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateEmployeeActivity.this, "Student updated successfully", Toast.LENGTH_SHORT).show();
                    finish();  // Go back to the employee list activity
                } else {
                    Toast.makeText(UpdateEmployeeActivity.this, "Failed to update student", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(UpdateEmployeeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
