package com.raghul.thiranx.features.employee;

import com.raghul.thiranx.data.dto.Employee;
import com.raghul.thiranx.data.repository.ThiranXDB;

import java.util.List;

class EmployeeListModel {

    private final EmployeeListView employeeListView;

    EmployeeListModel(EmployeeListView employeeListView) {
        this.employeeListView = employeeListView;
    }

    List<Employee> getAllEmployees() {
        return ThiranXDB.getInstance().getEmployees();
    }
}
