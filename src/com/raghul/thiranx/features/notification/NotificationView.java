package com.raghul.thiranx.features.notification;

import com.raghul.thiranx.data.dto.Employee;
import com.raghul.thiranx.data.dto.Notification;
import com.raghul.thiranx.util.ConsoleInput;
import com.raghul.thiranx.util.ParseHelper;
import java.util.List;
import java.util.Scanner;

public class NotificationView {

    private final NotificationModel notificationModel;
    private final Scanner scanner;
    private final Employee currentUser;

    public NotificationView(Employee currentUser) {
        this.notificationModel = new NotificationModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.currentUser = currentUser;
    }

    public void init() {
        System.out.println();
        System.out.println("Notifications");
        Long userId = (currentUser == null) ? null : currentUser.getId();
        List<Notification> notifications = notificationModel.getNotifications(userId);
        if (notifications.isEmpty()) {
            System.out.println("You have no notifications.");
            System.out.print("Press Enter to return: ");
            scanner.nextLine();
            return;
        }

        int unreadCount = 0;
        for (int i = 0; i < notifications.size(); i++) {
            Notification n = notifications.get(i);
            boolean unread = !Boolean.TRUE.equals(n.getIsRead());
            if (unread) unreadCount++;
            String marker = unread ? "*" : " ";
            System.out.println((i + 1) + ". [" + marker + "] "
                    + ParseHelper.formatDateTime(n.getCreatedTime())
                    + " | " + nameOr(n.getType())
                    + " | " + safe(n.getMessage()));
        }

        if (unreadCount == 0) {
            System.out.print("Press Enter to return: ");
            scanner.nextLine();
            return;
        }

        System.out.println();
        System.out.println("1. Mark a notification as read");
        System.out.println("2. Mark all as read");
        System.out.println("3. Return");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                markOneAsRead(userId, notifications);
                break;
            case "2":
                int marked = notificationModel.markAllRead(userId);
                System.out.println(marked + " notification(s) marked as read.");
                break;
            default:
                break;
        }
    }

    private void markOneAsRead(Long userId, List<Notification> notifications) {
        System.out.print("Enter notification number: ");
        Integer index = ParseHelper.parseNonNegativeInt(scanner.nextLine());
        if (index == null || index < 1 || index > notifications.size()) {
            System.out.println("Select a valid notification number.");
            return;
        }
        Notification selected = notifications.get(index - 1);
        if (Boolean.TRUE.equals(selected.getIsRead())) {
            System.out.println("Notification already marked as read.");
            return;
        }
        boolean updated = notificationModel.markRead(userId, selected.getId());
        if (updated) {
            System.out.println("Notification marked as read.");
        } else {
            System.out.println("Could not update notification.");
        }
    }

    private String safe(String value) {
        return value == null ? "-" : value;
    }

    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }
}
