import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {
    private static Delimiter parseDelimiters(String numbers) {
        StringBuilder delimiters = new StringBuilder("[,\n]");

        boolean hasCustomDelimiters = numbers.startsWith("//");
        if (!hasCustomDelimiters) {
            return new Delimiter(delimiters.toString(), numbers);
        }

        int indexOfEndLine = numbers.indexOf('\n');

        if (indexOfEndLine == -1) {
            throw new IllegalArgumentException();
        }

        // We want to continue from after the \n is over
        String remainingString = numbers.substring(indexOfEndLine + 1);

        /// We are allowing only one character. So the string would be "//_\n".
        // Hence index should be 3
        if (indexOfEndLine == 3) {
            String delimiter = numbers.substring(2, 3);
            // Pattern.quote ensures that if delimiter happens to be a meta character is regex, it is escaped
            return new Delimiter(delimiters + "|" + Pattern.quote(delimiter), remainingString);
        }

        String[] squareBracketDelimitedStrings = numbers.substring(2, indexOfEndLine).split("]");

        for (String delimitedString : squareBracketDelimitedStrings) {
            if (delimitedString.charAt(0) != '[') {
                throw new IllegalArgumentException();
            }

            // Hence, we used indexOfEndLine because that index is not included in substring
            // Pattern.quote ensures that if delimiter happens to be a meta character is regex, it is escaped
            delimiters.append("|").append(Pattern.quote(delimitedString.substring(1)));
        }

        return new Delimiter(delimiters.toString(), remainingString);
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
