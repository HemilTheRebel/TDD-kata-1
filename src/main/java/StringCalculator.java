import java.util.Arrays;

public class StringCalculator {
    public static int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        String[] ints = numbers.split(",");

        if (ints.length < 2 || numbers.endsWith(",")) {
            throw new IllegalArgumentException();
        }

        int sum = Arrays.stream(ints).mapToInt(e -> Integer.parseInt(e.trim())).sum();
        return sum;
    }
}
