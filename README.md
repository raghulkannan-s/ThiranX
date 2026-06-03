# Work Management System

Console-based Java application to manage employees, tasks, notifications, and simple reports for a small team.

## Tech Stack
- Java

## Features
- User authentication (sign up, sign in)
- Employee management (add, list, reportees)
- Task creation, assignment, and reassignment
- Task status updates with history and remarks
- Task details with priority updates
- Task list search by title
- Notifications (mark one or all as read)
- Reports and home summary

## Roles
- Manager: add employees, create tasks, assign tasks, view team status, view reports
- Employee: view assigned tasks, update task status, reassign tasks, view details

## Project Structure
- src: application source code
- bin: compiled output (if generated)
- docs: documentation assets (includes UML diagram)

## How to Run
1. Open the project in your IDE.
2. Run the main class:
	- com.raghul.thiranx.ThiranXApplication

## Notes
- The application is console-driven; follow prompts to navigate the menus.
- The first user to sign up becomes the Manager by default.

## For Interviewer
This project is a console-based work management system built in Java to demonstrate
CRUD-style flows, role-based menus, and in-memory data handling.

### High-Level Flow
1. Launch app -> landing menu (Sign Up / Sign In).
2. First user signs up and becomes Manager.
3. Manager can add employees, create tasks, and assign tasks.
4. Employees update task status with remarks; history is recorded.
5. Notifications and reports provide visibility into task activity.

### Key Design Notes
- In-memory repository (no database) to keep the focus on logic and flow.
- Separate View/Model classes for each feature to keep UI and logic organized.
- Simple enums for role, status, and priority to make state explicit.

## UML Diagram
- docs/work-management-system-uml.mmd
