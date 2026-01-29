package pie.exception;

public class ParseException extends Exception {
    public ParseException(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "I'm sorry, but I don't know understand what you're saying! Please try again.";
    }
}
