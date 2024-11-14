    package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private Database database;

    // Secure login method using parameterized queries to prevent SQL Injection
    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?"; // Using parameterized query
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the parameters in the prepared statement
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return "Login successful";
                } else {
                    return "Login failed";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    // Secure updatePassword method using parameterized queries and removing hardcoded password
    @PutMapping("/update-password")
    public String updatePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ? AND password = ?"; // Using parameterized query
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the parameters in the prepared statement
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            pstmt.setString(3, oldPassword);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0 ? "Password updated" : "Update failed";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
