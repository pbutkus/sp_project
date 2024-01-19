package ui;

import enums.FlightStatus;
import logic.DatabaseManager;
import models.Flight;
import models.Plane;
import services.FlightService;
import services.PlaneService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ManagerInterface {

    private FlightService flightService;
    private PlaneService planeService;
    private Scanner scanner;

    public ManagerInterface(FlightService flightService, PlaneService planeService, Scanner scanner) {
        this.flightService = flightService;
        this.planeService = planeService;
        this.scanner = scanner;
    }

    private int intInput() {
        while (true) {
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");;
            }
        }
    }

    public void start() {
        System.out.println("Welcome to Airport Management Interface");

        while (true) {
            System.out.println("1. Plane management");
            System.out.println("2. Flight management");
            System.out.println("0. Back");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    planeManagementMenu();
                    break;
                case "2":
                    flightManagementMenu();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input. Try again.");
            }
        }
    }

    private void planeManagementMenu() {
        while (true) {
            System.out.println();
            System.out.println("PLANE MANAGEMENT");
            System.out.println("1. Add new plane to fleet");
            System.out.println("2. Remove plane from fleet");
            System.out.println("0. Back");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    addPlaneMenu();
                    break;
                case "2":
                    removePlaneMenu();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input. Try again.");
            }
        }
    }

    private void addPlaneMenu() {
        System.out.println();
        System.out.println("Enter registration number:");
        String registrationNumber = scanner.nextLine();
        System.out.println("Enter number of seats:");
        int seats = intInput();

        while (seats < 0) {
            System.out.println();
            System.out.println("Number of seats should be > 0");
            seats = intInput();
        }

        Plane plane = new Plane(registrationNumber, seats);

        planeService.save(plane);
    }

    private void removePlaneMenu() {
        List<Plane> planeList = planeService.getAll();

        if (planeList.isEmpty()) {
            System.out.println("There are no planes in the fleet.");
        }

        System.out.println("Select plane to delete:");

        for (int i = 0; i < planeList.size(); i++) {
            System.out.println((i + 1) + ". " + planeList.get(i).getRegistrationNumber());
        }
        System.out.println("0. Back");

        int input = intInput();

        if (input == 0) {
            return;
        }

        planeService.delete(planeList.get(input - 1));
    }

    private void flightManagementMenu() {
        while (true) {
            System.out.println();
            System.out.println("FLIGHT MANAGEMENT");
            System.out.println("1. Create a flight");
            System.out.println("2. Edit a flight");
            System.out.println("0. Back");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    createFlightMenu();
                    break;
                case "2":
                    editFlightMenu();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input. Try again.");
            }
        }
    }

    private void createFlightMenu() {
        System.out.println("Select a plane from the list:");

        List<Plane> planeList = planeService.getAll();

        for (int i = 0; i < planeList.size(); i++) {
            System.out.println((i + 1) + ". " + planeList.get(i).getRegistrationNumber());
        }
        System.out.println("0. Back");

        int input = intInput();
        boolean validInput = false;

        while (!validInput) {
            if (input == 0) {
                return;
            }

            if (input > 0 && input <= planeList.size()) {
                validInput = true;
            } else {
                System.out.println("Invalid input. Try again.");
                input = intInput();
            }
        }

        System.out.println("Enter flight destination:");
        String flightDestination = scanner.nextLine();

        Flight flight = new Flight(planeList.get(input - 1), flightDestination);
        flightService.save(flight);
    }

    private void editFlightMenu() {
        System.out.println();
        System.out.println("Select a flight from the list:");

        List<Flight> flightList = flightService.getAll();

        if (flightList.isEmpty()) {
            System.out.println();
            System.out.println("There are no scheduled flights.");
            return;
        }

        for (int i = 0; i < flightList.size(); i++) {
            Flight flight = flightList.get(i);
            System.out.println((i + 1) + ". Destination: " + flight.getDestination() + "; Plane: " + flight.getPlane());
        }
        System.out.println("0. Back");

        int input = intInput();
        boolean validInput = false;

        while (!validInput) {
            if (input == 0) {
                return;
            }

            if (input > 0 && input <= flightList.size()) {
                validInput = true;
            } else {
                System.out.println("Invalid input. Try again.");
                input = intInput();
            }
        }

        flightEditorMenu(flightList.get(input - 1));
    }

    private void flightEditorMenu(Flight flight) {
        System.out.println();
        System.out.println("1. Change destination");
        System.out.println("2. Change plane");
        System.out.println("3. Change status");
        System.out.println("0. Back");

        String input = scanner.nextLine();

        switch (input) {
            case "1":
                changeDestinationMenu(flight);
                break;
            case "2":
                changePlaneMenu(flight);
                break;
            case "3":
                changeStatusMenu(flight);
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid input. Try again.");
        }
    }

    public void changeDestinationMenu(Flight flight) {
        System.out.println();
        System.out.println("Enter new destination:");
        String destination = scanner.nextLine();
        flight.setDestination(destination);
        flightService.update(flight);
    }

    public void changePlaneMenu(Flight flight) {
        System.out.println();
        System.out.println("Select plane from the list:");

        List<Plane> planeList = null;

        planeList = planeService.getAll();

        for (int i = 0; i < planeList.size(); i++) {
            System.out.println((i + 1) + ". " + planeList.get(i).getRegistrationNumber());
        }

        int input = intInput();
        boolean isInputValid = false;

        while (!isInputValid) {
            if (input > 0 && input <= planeList.size()) {
                isInputValid = true;
            } else {
                System.out.println("Invalid input. Try again.");
                input = intInput();
            }
        }

        flight.setPlane(planeList.get(input - 1));
        flightService.update(flight);
        System.out.println("Plane has successfully been changed.");
    }

    public void changeStatusMenu(Flight flight) {
        System.out.println();
        System.out.println("Select flight status:");
        System.out.println("1. SCHEDULED");
        System.out.println("2. DEPARTED");
        System.out.println("3. ARRIVED");
        System.out.println("0. Cancel");

        String input = scanner.nextLine();

        switch (input) {
            case "1":
                flight.setFlightStatus(FlightStatus.SCHEDULED);
                break;
            case "2":
                flight.setFlightStatus(FlightStatus.DEPARTED);
                break;
            case "3":
                flight.setFlightStatus(FlightStatus.ARRIVED);
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid input. Try again.");
        }

        flightService.update(flight);
    }

}
