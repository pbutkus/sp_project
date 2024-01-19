package services;

import logic.DatabaseManager;
import models.Plane;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlaneService {

    private DatabaseManager dbManager;

    public PlaneService(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Plane " +
                "(uuid VARCHAR(36) PRIMARY KEY, " +
                "registrationNumber VARCHAR(255) NOT NULL, " +
                "seats INTEGER NOT NULL)";
        try (Connection conn = dbManager.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save(Plane plane) {
        String sql = "INSERT INTO Plane (uuid, registrationNumber, seats) VALUES (?, ?, ?)";
        try (Connection conn = dbManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plane.getUuid().toString());
            pstmt.setString(2, plane.getRegistrationNumber());
            pstmt.setInt(3, plane.getSeats());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Plane> getAll() {
        String sql = "SELECT * FROM Plane";
        List<Plane> planeList = new ArrayList<>();
        try (Connection conn = dbManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {
            while (resultSet.next()) {
                UUID planeUuid = UUID.fromString(resultSet.getString("uuid"));
                String registrationNumber = resultSet.getString("registrationNumber");
                int seats = resultSet.getInt("seats");
                Plane plane = new Plane(planeUuid, registrationNumber, seats);
                planeList.add(plane);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return planeList;
    }

    public Plane getByUuid(UUID uuid) {
        String sql = "SELECT * FROM Plane WHERE uuid = ?";
        try (Connection conn = dbManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uuid.toString());
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    UUID planeUuid = UUID.fromString(resultSet.getString("uuid"));
                    String registrationNumber = resultSet.getString("registrationNumber");
                    int seats = resultSet.getInt("seats");
                    return new Plane(planeUuid, registrationNumber, seats);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void delete(Plane plane) {
        String sql = "DELETE FROM Plane WHERE uuid = ?";
        try (Connection conn = dbManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plane.getUuid().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}