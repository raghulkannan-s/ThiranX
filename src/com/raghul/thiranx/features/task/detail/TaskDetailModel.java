package com.raghul.thiranx.features.task.detail;

import com.raghul.thiranx.data.dto.Employee;
import com.raghul.thiranx.data.dto.Task;
import com.raghul.thiranx.data.dto.TaskStatusHistory;
import com.raghul.thiranx.data.repository.ThiranXDB;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class TaskDetailModel {

    private final TaskDetailView taskDetailView;

    TaskDetailModel(TaskDetailView taskDetailView) {
        this.taskDetailView = taskDetailView;
    }

    List<Task> getVisibleTasks(Employee user) {
        List<Task> result = new ArrayList<>();
        if (user == null || user.getId() == null) return result;
        Long userId = user.getId();
        Set<Long> seen = new HashSet<>();

        for (Task task : ThiranXDB.getInstance().getTasksAssignedTo(userId)) {
            if (task.getId() != null && seen.add(task.getId())) result.add(task);
        }
        for (Task task : ThiranXDB.getInstance().getTasksCreatedBy(userId)) {
            if (task.getId() != null && seen.add(task.getId())) result.add(task);
        }
        return result;
    }

    List<TaskStatusHistory> getHistoryFor(Long taskId) {
        return ThiranXDB.getInstance().getStatusHistoryForTask(taskId);
    }

    boolean updatePriority(Task task, Task.Priority priority) {
        if (task == null || priority == null) return false;
        task.setPriority(priority);
        return ThiranXDB.getInstance().updateTask(task) != null;
    }

    String getEmployeeName(Long id) {
        if (id == null) return "-";
        Employee employee = ThiranXDB.getInstance().getEmployeeById(id);
        return (employee == null || employee.getName() == null) ? "-" : employee.getName();
    }
}
