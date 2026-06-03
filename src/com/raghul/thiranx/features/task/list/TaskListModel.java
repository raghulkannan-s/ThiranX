package com.raghul.thiranx.features.task.list;

import com.raghul.thiranx.data.dto.Task;
import com.raghul.thiranx.data.repository.ThiranXDB;
import java.util.List;

class TaskListModel {

    private final TaskListView taskListView;

    TaskListModel(TaskListView taskListView) {
        this.taskListView = taskListView;
    }

    List<Task> getMyTasks(Long employeeId) {
        return ThiranXDB.getInstance().getTasksAssignedTo(employeeId);
    }

    List<Task> filterByTitle(List<Task> tasks, String query) {
        if (tasks == null) return java.util.Collections.emptyList();
        if (query == null || query.trim().isEmpty()) return tasks;
        String needle = query.trim().toLowerCase(java.util.Locale.ROOT);
        List<Task> result = new java.util.ArrayList<>();
        for (Task task : tasks) {
            String title = task == null ? null : task.getTitle();
            if (title != null && title.toLowerCase(java.util.Locale.ROOT).contains(needle)) {
                result.add(task);
            }
        }
        return result;
    }

    String getEmployeeName(Long id) {
        if (id == null) return "-";
        com.raghul.thiranx.data.dto.Employee employee = ThiranXDB.getInstance().getEmployeeById(id);
        return (employee == null || employee.getName() == null) ? "-" : employee.getName();
    }
}
