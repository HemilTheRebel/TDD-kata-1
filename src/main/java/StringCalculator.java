import java.util.Arrays;

public class StringCalculator {
    public static int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        String[] ints = numbers.split(",");
        if (ints.length != 2) {
            throw new IllegalStateException();
        }

        int sum = Integer.parseInt(ints[0]) + Integer.parseInt(ints[1]);
        return sum;
    }
}
