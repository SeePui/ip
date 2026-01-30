package pie.exception;

/**
 * Represents an exception that occurs when user input cannot be parsed
 * correctly by the parser.
 *
 * <p>
 * This exception is typically thrown when the command format is invalid,
 * required arguments are missing, or the input does not match any known
 * command pattern.
 * </p>
 */
public class ParseException extends Exception {
    /**
     * Instantiates a new {@code ParseException} with a specific error
     * message describing the parsing failure.
     *
     * @param description A detailed explanation of why parsing failed.
     */
    public ParseException(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "I'm sorry, but I don't know understand what you're saying! Please try again.";
    }
}
