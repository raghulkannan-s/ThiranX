package com.raghul.thiranx.features.home;

import com.raghul.thiranx.data.dto.Employee;
import com.raghul.thiranx.data.dto.Task;
import com.raghul.thiranx.data.repository.ThiranXDB;
import java.util.List;

class HomeModel {

    private final HomeView homeView;

    HomeModel(HomeView homeView) {
        this.homeView = homeView;
    }

    void init(Employee employee) {
        if (employee == null || employee.getRole() == null) {
            homeView.showUnauthorized();
            return;
        }
        if (employee.getRole() == Employee.Role.MANAGER) {
            homeView.showManagerMenu();
        } else {
            homeView.showEmployeeMenu();
        }
    }

    HomeSummary getSummary(Employee employee) {
        if (employee == null || employee.getId() == null) return new HomeSummary(0, 0, 0);
        List<Task> tasks;
        if (employee.getRole() == Employee.Role.MANAGER) {
            tasks = ThiranXDB.getInstance().getTasksCreatedBy(employee.getId());
        } else {
            tasks = ThiranXDB.getInstance().getTasksAssignedTo(employee.getId());
        }
        int total = tasks.size();
        int completed = 0;
        for (Task task : tasks) {
            if (task != null && task.getStatus() == Task.TaskStatus.COMPLETED) {
                completed++;
            }
        }
        int pending = total - completed;
        return new HomeSummary(total, completed, pending);
    }

    static class HomeSummary {
        private final int total;
        private final int completed;
        private final int pending;

        HomeSummary(int total, int completed, int pending) {
            this.total = total;
            this.completed = completed;
            this.pending = pending;
        }

        int getTotal() {
            return total;
        }

        int getCompleted() {
            return completed;
        }

        int getPending() {
            return pending;
        }
    }
}