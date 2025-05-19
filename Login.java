package com.reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("👤 Enter username: ");
        String username = scanner.nextLine();

        System.out.print("🔒 Enter password: ");
        String password = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return;

            String sql = "SELECT role FROM users WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                System.out.println("✅ Login successful. Role: " + role);

                if (role.equals("student")) {
                    com.reservation.StudentMenu studentMenu = new StudentMenu(username);
                    studentMenu.showMenu();
                } else {
                    System.out.println("🔐 Admin functionality coming soon!");
                }
            } else {
                System.out.println("❌ Invalid credentials.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
