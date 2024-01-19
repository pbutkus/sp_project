package services;

import enums.FlightStatus;
import logic.DatabaseManager;
import models.Flight;
import models.Passenger;
import models.Plane;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlightService {

    private DatabaseManager dbManager;
    private PlaneService planeService;

    public FlightService(DatabaseManager dbManager, PlaneService planeService) {
        this.dbManager = dbManager;
        this.planeService = planeService;
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Flight " +
                "(uuid VARCHAR(36) PRIMARY KEY, " +
                "plane_uuid VARCHAR(36), " +
                "destination VARCHAR(255), " +
                "availableSeats INTEGER, " +
                "flightStatus VARCHAR(50), " +
                "FOREIGN KEY(plane_uuid) REFERENCES Plane(uuid))";
        try (Connection conn = dbManager.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save(Flight flight) {
        String sql = "INSERT INTO Flight (uuid, plane_uuid, destination, availableSeats, flightStatus) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, flight.getUuid().toString());
            pstmt.setString(2, flight.getPlane().getUuid().toString());
            pstmt.setString(3, flight.getDestination());
            pstmt.setInt(4, flight.getAvailableSeats());
            pstmt.setString(5, flight.getFlightStatus().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Flight> getAll() {
        String sql = "SELECT f.uuid, f.plane_uuid, f.destination, f.availableSeats, f.flightStatus, p.uuid, p.registrationNumber, p.seats " +
                "FROM Flight f JOIN Plane p ON f.plane_uuid = p.uuid";
        List<Flight> flightList = new ArrayList<>();

        try (Connection conn = dbManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                UUID flightUuid = UUID.fromString(resultSet.getString("f.uuid"));
                String destination = resultSet.getString("f.destination");
                int availableSeats = resultSet.getInt("f.availableSeats");
                String flightStatus = resultSet.getString("f.flightStatus");

                UUID planeUuid = UUID.fromString(resultSet.getString("p.uuid"));
                String registrationNumber = resultSet.getString("p.registrationNumber");
                int seats = resultSet.getInt("p.seats");
                Plane plane = new Plane(planeUuid, registrationNumber, seats);

                List<Passenger> passengerList = getPassengerListByFlightUuid(flightUuid);

                Flight flight = new Flight(flightUuid, plane, destination, availableSeats, passengerList, FlightStatus.valueOf(flightStatus));
                flightList.add(flight);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return flightList;
    }


    public void update(Flight flight) {
        String sql = "UPDATE Flight SET plane_uuid = ?, destination = ?, availableSeats = ?, flightStatus = ? WHERE uuid = ?";
        try (Connection conn = dbManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, flight.getPlane().getUuid().toString());
            pstmt.setString(2, flight.getDestination());
            pstmt.setInt(3, flight.getAvailableSeats());
            pstmt.setString(4, flight.getFlightStatus().toString());
            pstmt.setString(5, flight.getUuid().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Flight flight) {
        String sql = "DELETE FROM Flight WHERE uuid = ?";
        try (Connection conn = dbManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, flight.getUuid().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<Passenger> getPassengerListByFlightUuid(UUID uuid) {
        String sql = "SELECT * FROM Plane WHERE uuid = " + uuid.toString();

        return getPassengerList(sql, dbManager);
    }

    static List<Passenger> getPassengerList(String sql, DatabaseManager dbManager) {
        List<Passenger> passengerList = new ArrayList<>();

        ResultSet resultSet = dbManager.getAll(sql);

        try {
            while (resultSet.next()) {
                String passengerUuid = resultSet.getString(1);
                String passengerName = resultSet.getString(2);
                String flightUuid = resultSet.getString(3);

                Passenger passenger = new Passenger(UUID.fromString(passengerUuid), passengerName, UUID.fromString(flightUuid));

                passengerList.add(passenger);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return passengerList;
    }

}
