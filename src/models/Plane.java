package models;

import java.util.UUID;

public class Plane {

    private final UUID uuid;
    private final String registrationNumber;
    private final int seats;

    public Plane(String registrationNumber, int seats) {
        this.uuid = UUID.randomUUID();
        this.registrationNumber = registrationNumber;
        this.seats = seats;
    }

    public Plane(UUID uuid, String registrationNumber, int seats) {
        this.uuid = uuid;
        this.registrationNumber = registrationNumber;
        this.seats = seats;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }


    public int getSeats() {
        return seats;
    }


}
