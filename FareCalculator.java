public class FareCalculator {

    public static double calculateBasicFare(
            double baseFare,
            double classMultiplier,
            int duration,
            int numberOfTravelers) {

        return baseFare
                * classMultiplier
                * duration
                * numberOfTravelers;
    }

    public static double calculateDiscount(
            double totalFare,
            int numberOfTravelers) {

        double discountRate;

        if (numberOfTravelers >= 5) {
            discountRate = 0.10;
        } else if (numberOfTravelers >= 3) {
            discountRate = 0.05;
        } else {
            discountRate = 0;
        }

        return totalFare * discountRate;
    }

    public static double calculateFinalFare(
            double totalFare,
            double discount) {

        return totalFare - discount;
    }

    public static double getDiscountRate(
            int numberOfTravelers) {

        if (numberOfTravelers >= 5) {
            return 10;
        } else if (numberOfTravelers >= 3) {
            return 5;
        } else {
            return 0;
        }
    }
}