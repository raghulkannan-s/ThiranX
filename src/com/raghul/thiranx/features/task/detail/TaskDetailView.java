package com.raghul.thiranx.features.task.detail;

import com.raghul.thiranx.data.dto.Employee;
import com.raghul.thiranx.data.dto.Task;
import com.raghul.thiranx.data.dto.TaskStatusHistory;
import com.raghul.thiranx.util.ConsoleInput;
import com.raghul.thiranx.util.ParseHelper;
import java.util.List;
import java.util.Scanner;

public class TaskDetailView {

    private final TaskDetailModel taskDetailModel;
    private final Scanner scanner;
    private final Employee currentUser;

    public TaskDetailView(Employee currentUser) {
        this.taskDetailModel = new TaskDetailModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.currentUser = currentUser;
    }

    public void init() {
        System.out.println();
        System.out.println("Task details");
        List<Task> tasks = taskDetailModel.getVisibleTasks(currentUser);
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks to view.");
            return;
        }

        Task task = pickTask(tasks);
        if (task == null) return;

        printTask(task);
        printHistory(task);
        maybeUpdatePriority(task);

        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }

    private void maybeUpdatePriority(Task task) {
        if (!canEditPriority(task)) return;
        System.out.print("Update priority? (Y/N): ");
        if (!ParseHelper.isYes(scanner.nextLine())) return;
        Task.Priority priority = promptPriority();
        if (priority == null) return;
        boolean updated = taskDetailModel.updatePriority(task, priority);
        if (updated) {
            System.out.println("Priority updated to " + nameOr(priority) + ".");
        } else {
            System.out.println("Could not update priority.");
        }
    }

    private boolean canEditPriority(Task task) {
        if (currentUser == null || task == null) return false;
        if (currentUser.getRole() == Employee.Role.MANAGER) return true;
        Long assignedBy = task.getAssignedBy();
        return assignedBy != null && assignedBy.equals(currentUser.getId());
    }

    private Task.Priority promptPriority() {
        while (true) {
            System.out.println("Select priority:");
            System.out.println("1. P1");
            System.out.println("2. P2");
            System.out.println("3. P3");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            if (choice == null) return null;
            String c = choice.trim();
            if (c.equals("1") || c.equalsIgnoreCase("P1")) return Task.Priority.P1;
            if (c.equals("2") || c.equalsIgnoreCase("P2")) return Task.Priority.P2;
            if (c.equals("3") || c.equalsIgnoreCase("P3")) return Task.Priority.P3;
            System.out.println("Select a valid priority.");
        }
    }

    private Task pickTask(List<Task> tasks) {
        while (true) {
            System.out.println("Select a task:");
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                System.out.println((i + 1) + ". " + t.getTitle() + " [" + nameOr(t.getStatus()) + "]");
            }
            System.out.print("Choose an option: ");
            Integer index = ParseHelper.parseNonNegativeInt(scanner.nextLine());
            if (index != null && index >= 1 && index <= tasks.size()) {
                return tasks.get(index - 1);
            }
            System.out.println("Select a valid option.");
        }
    }

    private void printTask(Task task) {
        System.out.println();
        System.out.println("Id           : " + task.getId());
        System.out.println("Title        : " + task.getTitle());
        System.out.println("Description  : " + task.getDescription());
        System.out.println("Priority     : " + nameOr(task.getPriority()));
        System.out.println("Status       : " + nameOr(task.getStatus()));
        System.out.println("Assigned by  : " + taskDetailModel.getEmployeeName(task.getAssignedBy()));
        System.out.println("Assigned to  : " + taskDetailModel.getEmployeeName(task.getAssignedTo()));
        System.out.println("Due date     : " + ParseHelper.formatDate(task.getDueDate()));
        System.out.println("Created at   : " + ParseHelper.formatDateTime(task.getCreatedTime()));
        System.out.println("Updated at   : " + ParseHelper.formatDateTime(task.getUpdatedTime()));
        System.out.println("Completed at : " + ParseHelper.formatDateTime(task.getCompletedTime()));
    }

    private void printHistory(Task task) {
        System.out.println();
        System.out.println("Status history");
        List<TaskStatusHistory> history = taskDetailModel.getHistoryFor(task.getId());
        if (history.isEmpty()) {
            System.out.println("No status history yet.");
            return;
        }
        for (TaskStatusHistory entry : history) {
            System.out.println(ParseHelper.formatDateTime(entry.getChangedTime())
                    + " | " + taskDetailModel.getEmployeeName(entry.getChangedBy())
                    + " | " + nameOr(entry.getOldStatus()) + " -> " + nameOr(entry.getNewStatus())
                    + " | " + (entry.getRemarks() == null ? "" : entry.getRemarks()));
        }
    }

    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }
}
