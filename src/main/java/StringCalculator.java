import java.util.Arrays;

public class StringCalculator {
    public static int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        numbers = numbers.trim();

        String delimiters = "[,\n]";

        boolean hasCustomDelimiters = numbers.startsWith("//");
        if (hasCustomDelimiters) {
            /// substring is to ignore the first "//"
            int indexOfEndLine = numbers.indexOf('\n');
            if (indexOfEndLine == -1) {
                throw new IllegalArgumentException();
            }

            // note we found the index of \n. But we do not want \n in the delimiter.
            // Hence, we used indexOfEndLine because that index is not included in substring
            delimiters += "|" + numbers.substring(2, indexOfEndLine);

            // We want to continue from after the string is over
            numbers = numbers.substring(indexOfEndLine + 1);
        }

        String[] ints = numbers.split(delimiters);

        if (ints.length < 2 || numbers.endsWith(",")) {
            throw new IllegalArgumentException();
        }

        // Separate variable is declared for readability. Ignore IntelliJ's big brains
        // noinspection UnnecessaryLocalVariable
        int sum = Arrays.stream(ints).mapToInt(e -> Integer.parseInt(e.trim())).sum();
        return sum;
    }
}
