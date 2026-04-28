
public class Main {
    public static void main(String[] args) {

    }

    static double calculateDosage(int age, double weight, boolean isHighRisk, boolean hasAllergy) {

        if ((age > 65 || isHighRisk) && weight < 50 && !hasAllergy) {

            return weight * 0.8;

        } else {

            return weight * 1.2 + age * 0.1;

        }

    }
}