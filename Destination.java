import java.util.Scanner;

public class Destination {

    public static String selectDestination(Scanner sc) {

        System.out.println("\n----- Destination Selection -----");
        System.out.println("1. Mumbai");
        System.out.println("2. Delhi");
        System.out.println("3. Goa");
        System.out.println("4. Bangalore");

        System.out.print("Enter destination choice: ");
        int choice = sc.nextInt();

        while (choice < 1 || choice > 4) {
            System.out.println("Invalid destination choice.");
            System.out.print("Enter choice again: ");
            choice = sc.nextInt();
        }

        switch (choice) {

            case 1:
                return "Mumbai";

            case 2:
                return "Delhi";

            case 3:
                return "Goa";

            default:
                return "Bangalore";
        }
    }

    public static double getBaseFare(String destination) {

        switch (destination) {

            case "Mumbai":
                return 5000;

            case "Delhi":
                return 7000;

            case "Goa":
                return 3000;

            default:
                return 4500;
        }
    }
}