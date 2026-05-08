package com.zsgs.thiranx.features.employee;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.repository.ThiranXDB;

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
