package ui;

import logic.DatabaseManager;
import models.Flight;
import models.Passenger;
import services.FlightService;
import services.PassengerService;

import java.util.List;
import java.util.Scanner;

public class PassengerInterface {

    private PassengerService passengerService;
    private FlightService flightService;
    private Scanner scanner;
    private DatabaseManager dbManager;

    public PassengerInterface(FlightService flightService, Scanner scanner, DatabaseManager dbManager) {
        this.passengerService = new PassengerService(dbManager);
        this.flightService = flightService;
        this.scanner = scanner;
    }

    public void start() {
        System.out.println();
        System.out.println("Enter your name:");
        String passengerName = scanner.nextLine();



        System.out.println();
        System.out.println("Hello, " + passengerName + ", please choose the flight you want to book.");
        bookFlightInterface(passengerName);
    }

    private void bookFlightInterface(String passengerName) {
        List<Flight> flightList = flightService.getAll();

        while (true) {
            System.out.println();

            for (int i = 0; i < flightList.size(); i++) {
                System.out.println((i + 1) + ". " + flightList.get(i).getDestination());
            }
            System.out.println("0. Cancel");

            String flightChoice = scanner.nextLine();
            int convertedChoice = convertStringToInt(flightChoice);

            if (convertedChoice < 0) {
                System.out.println("Invalid choice. Try again.");
            } else if (convertedChoice == 0) {
                System.out.println("Canceling flight booking...");
                return;
            } else {
                Flight flight = flightList.get(convertedChoice - 1);
                Passenger passenger = new Passenger(passengerName, flight.getUuid());
                passengerService.save(passenger);
                flight.addPassenger(passenger);
                System.out.println("Your flight has been booked successfully.");
                return;
            }
        }
    }

    private int convertStringToInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
