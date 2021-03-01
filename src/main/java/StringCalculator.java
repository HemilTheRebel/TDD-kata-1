import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringCalculator {
    private static Delimiter parseDelimiters(String numbers) {
        String delimiters = "[,\n]";

        boolean hasCustomDelimiters = numbers.startsWith("//");
        if (!hasCustomDelimiters) {
            return new Delimiter(delimiters, numbers);
        }

        /// substring is to ignore the first "//"
        int indexOfEndLine = numbers.indexOf('\n');
        if (indexOfEndLine == -1) {
            throw new IllegalArgumentException();
        }

        // note we found the index of \n. But we do not want \n in the delimiter.
        // Hence, we used indexOfEndLine because that index is not included in substring
        delimiters += "|" + numbers.substring("//".length(), indexOfEndLine);

        // We want to continue from after the \n is over
        String remainingString = numbers.substring(indexOfEndLine + 1);

        return new Delimiter(delimiters, remainingString);
    }

    public static int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        numbers = numbers.trim();

        Delimiter delimiter = parseDelimiters(numbers);
        numbers = delimiter.remainingString;

        String[] ints = numbers.split(delimiter.regexToSplit);

        if (ints.length < 2 || numbers.endsWith(",")) {
            throw new IllegalArgumentException();
        }


        List<Integer> integerList = Arrays.stream(ints)
                .map(e -> Integer.parseInt(e.trim()))
                .collect(Collectors.toList());

        List<Integer> negativeNumbers = integerList
                .stream()
                .filter(e -> e < 0)
                .collect(Collectors.toList());

        if (negativeNumbers.size() > 0) {
            String spaceDelimitedNumbers = negativeNumbers.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(" "));

            throw new IllegalArgumentException(
                    "Negatives not allowed. Negative numbers are: " + spaceDelimitedNumbers
            );
        }

        return integerList.stream().
                filter(e -> e <= 1000)
                .reduce(0, Integer::sum);
    }
}
