package ui;

import java.util.Scanner;

public class MainInterface {

    private ManagerInterface managerInterface;
    private PassengerInterface passengerInterface;
    private Scanner scanner;

    public MainInterface(ManagerInterface managerInterface, PassengerInterface passengerInterface, Scanner scanner) {
        this.managerInterface = managerInterface;
        this.passengerInterface = passengerInterface;
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("Select an option:");
        System.out.println("1. Manager interface");
        System.out.println("2. Passenger interface");
        System.out.println("0. Exit");

        String input = scanner.nextLine();

        switch (input) {
            case "1":
                managerInterface.start();
                break;
            case "2":
                passengerInterface.start();
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid input. Try again.");
        }
    }

}
