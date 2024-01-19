import logic.DatabaseManager;
import services.FlightService;
import services.PlaneService;
import ui.MainInterface;
import ui.ManagerInterface;
import ui.PassengerInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DatabaseManager dbManager = new DatabaseManager("test.db");
        PlaneService planeService = new PlaneService(dbManager);
        FlightService flightService = new FlightService(dbManager, planeService);
        ManagerInterface managerInterface = new ManagerInterface(flightService, planeService, scanner);
        PassengerInterface passengerInterface = new PassengerInterface(flightService, scanner, dbManager);
        MainInterface mainInterface = new MainInterface(managerInterface, passengerInterface, scanner);

        mainInterface.start();
    }
}