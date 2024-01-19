package models;

import enums.FlightStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Flight {

    private final UUID uuid;
    private Plane plane;
    private String destination;
    private int availableSeats;
    private List<Passenger> passengerList;
    private FlightStatus flightStatus;

    public Flight(Plane plane, String destination) {
        this.uuid = UUID.randomUUID();
        this.plane = plane;
        this.destination = destination;
        this.availableSeats = plane.getSeats();
        this.passengerList = new ArrayList<>();
        this.flightStatus = FlightStatus.SCHEDULED;
    }

    public Flight(UUID uuid, Plane plane, String destination, int availableSeats, List<Passenger> passengerList, FlightStatus flightStatus) {
        this.uuid = uuid;
        this.plane = plane;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.passengerList = passengerList;
        this.flightStatus = flightStatus;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Plane getPlane() {
        return plane;
    }

    public String getDestination() {
        return destination;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }

    public void addPassenger(Passenger passenger) {
        passengerList.add(passenger);
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

}
