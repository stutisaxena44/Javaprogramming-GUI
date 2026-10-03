import java.util.Scanner;

public class TravelClass {

    public static String selectClass(Scanner sc) {

        System.out.println("\n----- Travel Class -----");
        System.out.println("1. Economy");
        System.out.println("2. Business");
        System.out.println("3. First Class");

        System.out.print("Enter travel class choice: ");
        int choice = sc.nextInt();

        while (choice < 1 || choice > 3) {
            System.out.println("Invalid travel class choice.");
            System.out.print("Enter choice again: ");
            choice = sc.nextInt();
        }

        switch (choice) {

            case 1:
                return "Economy";

            case 2:
                return "Business";

            default:
                return "First Class";
        }
    }

    public static double getClassMultiplier(String travelClass) {

        switch (travelClass) {

            case "Economy":
                return 1.0;

            case "Business":
                return 1.5;

            default:
                return 2.0;
        }
    }
}