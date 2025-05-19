# A Student-Reservation-System using Java and MySQL.

---

# 🎓 Student Reservation System

A simple Java-based application that allows students to reserve seats using a login system. The system is connected to a MySQL database for managing users and reservations.

---

## 📌 Features

* 🔐 **Login Authentication** for Students
* 🪑 **Seat Reservation** system with status tracking
* 📅 **Reservation Date** selection
* ❌ **Cancel Reservation** feature
* 🛠 Built using **Java**, **JDBC**, **MySQL**, and **IntelliJ IDEA**

---

## 🧰 Technologies Used

* **Java 17+** (OpenJDK 24 recommended)
* **MySQL 8+**
* **JDBC (MySQL Connector/J)**
* **IntelliJ IDEA**

---

## 📁 Project Structure

```
Student Reservation System/
├── src/
│   ├── com/reservation/
│   │   ├── DBConnection.java
│   │   ├── Login.java
│   │   ├── Reservation.java
│   │   └── Main.java
├── README.md
```

---

## ⚙️ Database Setup

1. **Create the database** in MySQL:

```sql
CREATE DATABASE student_reservation;
USE student_reservation;
```

2. **Create `users` table**:

```sql
CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL
);
```

3. **Create `reservations` table**:

```sql
CREATE TABLE reservations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_username VARCHAR(50) NOT NULL,
    seat_number INT NOT NULL,
    reservation_date DATE NOT NULL,
    status ENUM('reserved', 'cancelled') DEFAULT 'reserved',
    FOREIGN KEY (student_username) REFERENCES users(username)
);
```

4. **Insert sample data**:

```sql
INSERT INTO users (username, password) VALUES ('student1', 'pass123'), ('student2', 'pass456');
```

---

## 🚀 How to Run

1. Clone or download the repository.
2. Open the project in **IntelliJ IDEA**.
3. Add the MySQL JDBC driver to your project:

   * Download from: [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
   * Add `.jar` to `Project Structure` > `Libraries`.
4. Configure the DB connection in `DBConnection.java`.
5. Run `Login.java` or `Main.java`.

---

## 🧪 Sample Workflow

1. Login with a valid student username and password.
2. Choose a seat number and reservation date.
3. Reserve or cancel a reservation.
4. View output in the console.

---

## 🙋‍♂️ Author

**Santhosh C**
Built as a resume project for academic and personal learning.

---

## 📄 License

This project is for educational purposes and is free to use and modify.

---
