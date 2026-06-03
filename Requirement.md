# Work Management System

## Project Details
- Tech Stack: Java
- Timeline: 1 Month
- Team Size: 1

## Overview
Console-based system to manage employees, tasks, and notifications. Managers create
and assign tasks, employees update task status, and the system keeps a history of
status changes and notifications.

## Phases
### Phase 1
- Feasibility and scope finalization.
- User authentication (register/sign in) and role setup.
- Employee management (add, list, reportees).
- Core task creation and assignment (title, description, priority, due date).
- Basic task list view for assigned tasks.

### Phase 2
- Task status updates with remarks and history tracking.
- Task details view with history and priority update.
- Task list search/filter by title.
- Notifications (view, mark one/all as read).
- Reports and home summary.
- Final testing and bug fixes.

## Core Features
- User Authentication: Register and sign in as Employee or Manager.
- Employee Management: Add employees, view list, and view reportees.
- Task Creation: Create tasks with title, description, priority, and due date.
- Task Assignment: Assign or reassign tasks to employees.
- Task Status Update: Update task status with remarks and history tracking.
- Task Details: View task details with history and optionally update priority.
- Task List Search: Filter your task list by title.
- Notifications: View notifications, mark one or all as read.
- Reports: View simple task or team status reports.
- Home Summary: Quick summary of total, completed, and pending tasks.

## Roles
### Manager
- Add employees.
- Create tasks.
- Assign tasks.
- View team status.
- View reports.

### Employee
- View assigned tasks.
- Update task status.
- Reassign a task.
- View details.

## Model Classes
### Employee
- id : Long
- employeeId : String
- name : String
- email : String
- password : String
- role : Enum (MANAGER / EMPLOYEE)
- status : Enum (ACTIVE / INACTIVE)
- reportingTo : Long
- createdAt : Long

### Task
- id : Long
- title : String
- description : String
- assignedBy : Long
- assignedTo : Long
- priority : Enum (P1 / P2 / P3)
- status : Enum (OPEN / IN_PROGRESS / COMPLETED / ON_HOLD / CANCELLED / REOPENED)
- createdTime : Long
- updatedTime : Long
- dueDate : Long
- completedTime : Long
- remarks : String

### TaskStatusHistory
- id : Long
- taskId : Long
- changedBy : Long
- oldStatus : Enum
- newStatus : Enum
- remarks : String
- changedTime : Long

### Notification
- id : Long
- employeeId : Long
- taskId : Long
- type : Enum (TASK_ASSIGNED / STATUS_UPDATED / DUE_REMINDER)
- message : String
- isRead : Boolean
- createdTime : Long
