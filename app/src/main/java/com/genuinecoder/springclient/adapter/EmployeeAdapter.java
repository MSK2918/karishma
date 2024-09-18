package com.genuinecoder.springclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genuinecoder.springclient.R;
import com.genuinecoder.springclient.UpdateEmployeeActivity;
import com.genuinecoder.springclient.model.Employee;
import com.genuinecoder.springclient.reotrfit.EmployeeApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeHolder> {

  private List<Employee> employeeList;
  private Context context;
  private EmployeeApi employeeApi; // Retrofit instance

  // Updated constructor to pass context and employeeApi
  public EmployeeAdapter(Context context, List<Employee> employeeList, EmployeeApi employeeApi) {
    this.context = context;  // Initialize context
    this.employeeList = employeeList;  // Assign employee list
    this.employeeApi = employeeApi;  // Initialize employeeApi
  }

  @NonNull
  @Override
  public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.list_employee_item, parent, false);
    return new EmployeeHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {
    Employee employee = employeeList.get(position);
    holder.name.setText(employee.getName());
    holder.location.setText(employee.getLocation());
    holder.branch.setText(employee.getBranch());

    // Handle delete button click
    holder.btnDelete.setOnClickListener(v -> {
      deleteEmployee(employee.getId());  // Call delete method with employee ID
    });

    holder.btnUpdate.setOnClickListener(view -> {
      Intent intent = new Intent(context, UpdateEmployeeActivity.class);
      intent.putExtra("EMPLOYEE_ID", employeeList.get(position).getId()); // assuming getId() returns Integer
      context.startActivity(intent);
    });


  }

  @Override
  public int getItemCount() {
    return employeeList.size();
  }

  // Method to delete employee
  private void deleteEmployee(Integer id) {
    employeeApi.deleteEmployee(id).enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
          Toast.makeText(context, "Student deleted!", Toast.LENGTH_SHORT).show();
          // Optionally, refresh the employee list here by removing the item and notifying the adapter
          removeEmployeeFromList(id);
        } else {
          Toast.makeText(context, "Delete failed!", Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {
        Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  // Helper method to remove the employee from the list after deletion
  private void removeEmployeeFromList(Integer id) {
    for (int i = 0; i < employeeList.size(); i++) {
      if (employeeList.get(i).getId() == id) {
        employeeList.remove(i);  // Remove the employee from the list
        notifyItemRemoved(i);  // Notify RecyclerView about the removed item
        break;
      }
    }
  }
}
