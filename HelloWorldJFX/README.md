# Client Scheduling System (JavaFX)

A desktop scheduling application built for a global consulting organization scenario, developed as a capstone project for a B.S. in Software Engineering. The system manages customers, appointments, and contacts across multiple countries and time zones, with role-based login and an audit log of user activity.

## Features

- **Customer management** — add, update, and view customer records with country/division lookups
- **Appointment scheduling** — create, update, and cancel appointments; view schedules by contact, by type, and by month
- **Time zone handling** — appointments are stored and converted correctly across user, business, and UTC time zones using `ZoneId` and `DateTimeFormatter`
- **Login & audit logging** — authenticates users against the database and records every login attempt (user, timestamp, success/failure) to a local log file for auditability
- **Localization** — UI supports resource-bundle-based localization via `ResourceBundle` and `Locale`

## Architecture

The project follows a layered MVC structure:

- `Model/` — plain data classes (`Customer`, `Appointment`, `Contact`, `Division`, `Country`, `User`, `VirtualAppointment`)
- `DBAccess/` — data access layer; one class per entity, each using `PreparedStatement` for all queries to prevent SQL injection
- `sample/` — JavaFX controllers, one per screen, handling UI logic and navigation
- `fxml/` — FXML view definitions for each screen
- `Database/` — connection management (`JDBC.java`)
- `Interfaces/` — shared interfaces used across controllers

## Tech Stack

- **Java** with **JavaFX** for the UI
- **MySQL** via **JDBC**, using parameterized `PreparedStatement` queries throughout the data access layer
- Built and run in IntelliJ IDEA

## Running Locally

This project expects a local MySQL database (`client_schedule`) and reads credentials from environment variables rather than hardcoding them:

```bash
export DB_USER=your_mysql_username
export DB_PASSWORD=your_mysql_password
```

You'll also need the JavaFX SDK and MySQL Connector/J on your classpath (originally configured as IntelliJ project libraries).

## Notes

This was built as an academic capstone project and reflects the schema and scope required for that assignment.
