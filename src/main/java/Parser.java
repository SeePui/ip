import java.util.regex.*;

public class Parser {
    private static final String INDEX_REGEX = "^(mark|unmark|delete)\\s+(\\d+)$";
    private static final String TODO_REGEX = "^todo\\s+(.*)$";
    private static final String DEADLINE_REGEX = "^deadline\\s+(.+)\\s+/by\\s+(.+)$";
    private static final String EVENT_REGEX = "^event\\s+(.+)\\s+/from\\s+(.+)\\s+/to\\s+(.+)$";

    public static CommandType parseCommand(String input) throws ParseException {
        if (input == null || input.trim().isEmpty()) {
            throw new ParseException(BotMessage.ERROR_EMPTY_COMMAND.get());
        }
        return CommandType.from(input);
    }

    public static int parseIndex(String input) throws ParseException {
        Matcher m = Pattern.compile(INDEX_REGEX).matcher(input.trim());

        if (!m.matches()) {
            throw new ParseException(BotMessage.ERROR_INVALID_FORMAT.get()
                    + "Use: <command> <number>\n");
        }

        try {
            return Integer.parseInt(m.group(2)) - 1;
        } catch (NumberFormatException e) {
            throw new ParseException(BotMessage.ERROR_NOT_NUMBER.get());
        }
    }


    public static String parseTodo(String input)
            throws ParseException {
        Matcher m = Pattern.compile(TODO_REGEX).matcher(input.trim());

        if (m.matches()) {
            return m.group(1);
        } else {
            throw new ParseException(BotMessage.ERROR_INVALID_FORMAT.get()
                    + "Use: todo <description>\n");
        }
    }

    public static Deadline parseDeadline(String input)
            throws ParseException {
        Matcher m = Pattern.compile(DEADLINE_REGEX).matcher(input.trim());

        if (m.matches()) {
            return new Deadline(m.group(1), m.group(2));
        } else {
            throw new ParseException(BotMessage.ERROR_INVALID_FORMAT.get()
                    + "Use: deadline <description> /by <date>\n");
        }
    }

    public static Event parseEvent(String input)
            throws ParseException {
        Matcher m = Pattern.compile(EVENT_REGEX).matcher(input.trim());

        if (m.matches()) {
            return new Event(m.group(1), m.group(2), m.group(3));
        } else {
            throw new ParseException(BotMessage.ERROR_INVALID_FORMAT.get()
                    + "Use: event <description> /from <date> /to <date>\n");
        }
    }
}