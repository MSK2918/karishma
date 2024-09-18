package com.genuinecoder.springclient.reotrfit;

import com.genuinecoder.springclient.model.Employee;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmployeeApi {

  @GET("/api/v1/student")
  Call<List<Employee>> getAllEmployees();

  @POST("/api/v1/student")
  Call<Employee> save(@Body Employee employee);

  // Update an employee
  @PUT("/api/v1/student/{id}")
  Call<Employee> updateEmployee(@Path("id") Integer id, @Body Employee employee);

  // Delete an employee
  @DELETE("/api/v1/student/{id}")
  Call<Void> deleteEmployee(@Path("id") Integer id);

  @GET("/student/{id}")
  Call<Employee> getStudentById(@Path("id") Integer id);

}
