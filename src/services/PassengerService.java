package services;

import logic.DatabaseManager;
import models.Passenger;

import java.util.List;
import java.util.UUID;

public class PassengerService {

    private DatabaseManager dbManager;

    public PassengerService(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        createTable();
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Passenger " +
                "(uuid VARCHAR(36) PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "flight_uuid VARCHAR(36), " +
                "FOREIGN KEY(flight_uuid) REFERENCES Flight(uuid))";


        dbManager.executeUpdate(sql);
    }

    public void save(Passenger passenger) {
        String sql = "INSERT INTO Passenger(uuid, name, flight_uuid) VALUES(" +
                passenger.getUuid().toString() + ", " +
                passenger.getName() + ", " +
                passenger.getFlightUuid().toString() + ")";

        dbManager.executeUpdate(sql);
    }

    public List<Passenger> getAll() {
        String sql = "SELECT * FROM Passenger";
        return getPassengerList(sql, dbManager);
    }

    public List<Passenger> getAllByFlightUuid(UUID uuid) {
        String sql = "SELECT * FROM Plane WHERE uuid = " + uuid.toString();
        return getPassengerList(sql, dbManager);
    }

    private List<Passenger> getPassengerList(String sql, DatabaseManager dbManager) {
        return getPassengerList(sql, this.dbManager);
    }

}
