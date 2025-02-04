package com.genuinecoder.springclient.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.genuinecoder.springclient.R;

public class EmployeeHolder extends RecyclerView.ViewHolder {

  TextView name, location, branch;
  Button btnDelete, btnUpdate;

  public EmployeeHolder(@NonNull View itemView) {
    super(itemView);
    name = itemView.findViewById(R.id.employeeListItem_name);
    location = itemView.findViewById(R.id.employeeListItem_location);
    branch = itemView.findViewById(R.id.employeeListItem_branch);
    btnDelete = itemView.findViewById(R.id.btnDelete);
    btnUpdate = itemView.findViewById(R.id.btnUpdate);
  }
}
