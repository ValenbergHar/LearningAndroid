package com.example.testretrofitrx.screens.employee;

import com.example.testretrofitrx.pojo.Employee;

import java.util.List;

public interface EmployeesListView {
    void showData(List<Employee> employees);

    void showError();
}
