package com.zsgs.thiranx.features.report;

import com.zsgs.thiranx.data.dto.Employee;
import com.zsgs.thiranx.data.dto.Task;
import com.zsgs.thiranx.util.ConsoleInput;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ReportView {

    private final ReportModel reportModel;
    private final Scanner scanner;
    private final Employee manager;

    public ReportView(Employee manager) {
        this.reportModel = new ReportModel();
        this.scanner = ConsoleInput.getScanner();
        this.manager = manager;
    }

    public void init() {
        System.out.println();
        System.out.println("Reports");
        if (manager == null || manager.getId() == null) {
            System.out.println("Unable to load reports.");
            return;
        }

        while (true) {
            System.out.println();
            System.out.println("1. My task summary");
            System.out.println("2. Team task summary");
            System.out.println("3. Return");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    showMyTaskSummary();
                    break;
                case "2":
                    showTeamTaskSummary();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showMyTaskSummary() {
        List<Task> tasks = reportModel.getTasksCreatedBy(manager.getId());
        printSummary("My Tasks", tasks);
    }

    private void showTeamTaskSummary() {
        List<Employee> reports = reportModel.getDirectReports(manager.getId());
        if (reports.isEmpty()) {
            System.out.println("You have no reporting employees.");
            return;
        }
        for (Employee report : reports) {
            List<Task> tasks = reportModel.getTasksAssignedTo(report.getId());
            String name = report.getName() == null ? "Employee" : report.getName();
            printSummary(name + " (" + report.getEmployeeId() + ")", tasks);
        }
    }

    private void printSummary(String title, List<Task> tasks) {
        System.out.println();
        System.out.println(title);
        System.out.println("Total tasks: " + tasks.size());
        Map<Task.TaskStatus, Integer> counts = reportModel.getStatusCounts(tasks);
        for (Task.TaskStatus status : Task.TaskStatus.values()) {
            Integer count = counts.get(status);
            System.out.println("  " + status.name() + ": " + (count == null ? 0 : count));
        }
        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }
}
