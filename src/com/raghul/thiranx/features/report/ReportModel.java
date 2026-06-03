package com.raghul.thiranx.features.report;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import com.raghul.thiranx.data.dto.Employee;
import com.raghul.thiranx.data.dto.Task;
import com.raghul.thiranx.data.repository.ThiranXDB;

class ReportModel {

    List<Task> getTasksCreatedBy(Long managerId) {
        return ThiranXDB.getInstance().getTasksCreatedBy(managerId);
    }

    List<Employee> getDirectReports(Long managerId) {
        return ThiranXDB.getInstance().getDirectReports(managerId);
    }

    List<Task> getTasksAssignedTo(Long employeeId) {
        return ThiranXDB.getInstance().getTasksAssignedTo(employeeId);
    }

    Map<Task.TaskStatus, Integer> getStatusCounts(List<Task> tasks) {
        Map<Task.TaskStatus, Integer> counts = new EnumMap<>(Task.TaskStatus.class);
        for (Task.TaskStatus status : Task.TaskStatus.values()) {
            counts.put(status, 0);
        }
        if (tasks == null) return counts;
        for (Task task : tasks) {
            Task.TaskStatus status = task == null ? null : task.getStatus();
            if (status == null) continue;
            counts.put(status, counts.get(status) + 1);
        }
        return counts;
    }
}
