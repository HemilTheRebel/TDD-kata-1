public class Delimiter {
    public final String regexToSplit, remainingString;

    public Delimiter(String delimiter, String remainingString) {
        this.regexToSplit = delimiter;
        this.remainingString = remainingString;
    }
}
