package models;

import java.util.UUID;

public class Passenger {

    private final UUID uuid;
    private final String name;
    private final UUID flightUuid;

    public Passenger(String name, UUID flightUuid) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.flightUuid = flightUuid;
    }

    public Passenger(UUID uuid, String name, UUID flightUuid) {
        this.uuid = uuid;
        this.name = name;
        this.flightUuid = flightUuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getFlightUuid() {
        return flightUuid;
    }

}
