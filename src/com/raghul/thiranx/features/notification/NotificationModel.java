package com.raghul.thiranx.features.notification;

import com.raghul.thiranx.data.dto.Notification;
import com.raghul.thiranx.data.repository.ThiranXDB;
import java.util.List;

class NotificationModel {

    private final NotificationView notificationView;

    NotificationModel(NotificationView notificationView) {
        this.notificationView = notificationView;
    }

    List<Notification> getNotifications(Long employeeId) {
        return ThiranXDB.getInstance().getNotificationsFor(employeeId);
    }

    int markAllRead(Long employeeId) {
        return ThiranXDB.getInstance().markNotificationsRead(employeeId);
    }

    boolean markRead(Long employeeId, Long notificationId) {
        return ThiranXDB.getInstance().markNotificationRead(employeeId, notificationId);
    }
}
