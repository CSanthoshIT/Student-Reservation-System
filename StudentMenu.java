package com.reservation;

import java.sql.*;
import java.util.Scanner;

public class StudentMenu {

    private String username;

    public StudentMenu(String username) {
        this.username = username;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n📋 Student Reservation Menu");
            System.out.println("1. View My Reservations");
            System.out.println("2. Make a Reservation");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> viewReservations();
                case 2 -> makeReservation(scanner);
                case 3 -> cancelReservation(scanner);
                case 4 -> System.out.println("👋 Exiting student menu.");
                default -> System.out.println("❌ Invalid option. Try again.");
            }

        } while (choice != 4);
    }

    private void viewReservations() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM reservations WHERE student_username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            System.out.println("\n📄 Your Reservations:");
            while (rs.next()) {
                System.out.printf("ID: %d | Seat: %d | Date: %s | Status: %s\n",
                        rs.getInt("id"),
                        rs.getInt("seat_number"),
                        rs.getDate("reservation_date"),
                        rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeReservation(Scanner scanner) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter seat number to reserve: ");
            int seatNumber = scanner.nextInt();
            scanner.nextLine();  // Clear buffer

            System.out.print("Enter reservation date (YYYY-MM-DD): ");
            String date = scanner.nextLine();

            // Optional: Check if seat is already booked for that date
            String checkSql = "SELECT * FROM reservations WHERE seat_number = ? AND reservation_date = ? AND status = 'reserved'";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, seatNumber);
            checkStmt.setDate(2, Date.valueOf(date));
            ResultSet checkResult = checkStmt.executeQuery();

            if (checkResult.next()) {
                System.out.println("⚠️ Seat already reserved for that date!");
                return;
            }

            String sql = "INSERT INTO reservations (student_username, seat_number, reservation_date) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setInt(2, seatNumber);
            stmt.setDate(3, Date.valueOf(date));

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Reservation made successfully!");
            } else {
                System.out.println("❌ Failed to make reservation.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cancelReservation(Scanner scanner) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter reservation ID to cancel: ");
            int resId = scanner.nextInt();

            String sql = "UPDATE reservations SET status = 'cancelled' WHERE id = ? AND student_username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, resId);
            stmt.setString(2, username);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("🗑️ Reservation cancelled successfully.");
            } else {
                System.out.println("❌ Reservation not found or not yours.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
