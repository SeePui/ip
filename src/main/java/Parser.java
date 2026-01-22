import java.util.regex.*;

public class Parser {
    private static final String COMMANDS_REGEX = "^(list|mark|unmark|todo|deadline|event|delete|bye)$";
    private static final String MARK_REGEX = "^mark\\s+(\\d+)$";
    private static final String UNMARK_REGEX = "^unmark\\s+(\\d+)$";
    private static final String TODO_REGEX = "^todo\\s+(.*)$";
    private static final String DEADLINE_REGEX = "^deadline\\s+(.+)\\s+/by\\s+(.+)$";
    private static final String EVENT_REGEX = "^event\\s+(.+)\\s+/from\\s+(.+)\\s+/to\\s+(.+)$";
    private static final String DELETE_REGEX = "^delete\\s+(\\d+)$";

    public static String parseCommand(String input) throws ParseException {
        if (input == null || input.trim().isEmpty()) {
            throw new ParseException("Empty command. Please provide a valid command.");
        }

        String command = input.trim().split("\\s+", 2)[0];
        Matcher m = Pattern.compile(COMMANDS_REGEX).matcher(command);

        if (!m.matches()) {
            throw new ParseException("Invalid command. Please provide a valid command.");
        }
        return command;
    }

    public static int parseMarkNumber(String input)
            throws ParseException, NumberFormatException {
        Matcher m = Pattern.compile(MARK_REGEX).matcher(input.trim());

        if (m.matches()) {
            String numberString = m.group(1);
            try {
                return Integer.parseInt(numberString) - 1;
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Task number must be a number.");
            }
        } else {
            throw new ParseException("Invalid mark command format. Use: mark <number>");
        }
    }

    public static int parseUnmarkNumber(String input)
            throws ParseException, NumberFormatException {
        Matcher m = Pattern.compile(UNMARK_REGEX).matcher(input.trim());

        if (m.matches()) {
            String numberString = m.group(1);
            try {
                return Integer.parseInt(numberString) - 1;
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Task number must be a number.");
            }
        } else {
            throw new ParseException("Invalid unmark command format. Use: unmark <number>");
        }
    }

    public static String parseTodo(String input)
            throws ParseException {
        Matcher m = Pattern.compile(TODO_REGEX).matcher(input.trim());

        if (m.matches()) {
            return m.group(1);
        } else {
            throw new ParseException("Invalid todo command format. Use: todo <description>");
        }
    }

    public static Deadline parseDeadline(String input)
            throws ParseException {
        Matcher m = Pattern.compile(DEADLINE_REGEX).matcher(input.trim());

        if (m.matches()) {
            return new Deadline(m.group(1), m.group(2));
        } else {
            throw new ParseException("Invalid deadline command format. Use: deadline <description> /by <date>");
        }
    }

    public static Event parseEvent(String input)
            throws ParseException {
        Matcher m = Pattern.compile(EVENT_REGEX).matcher(input.trim());

        if (m.matches()) {
            return new Event(m.group(1), m.group(2), m.group(3));
        } else {
            throw new ParseException("Invalid event command format. Use: event <description> /from <date> /to <date>");
        }
    }

    public static int parseDeleteNumber(String input)
            throws ParseException, NumberFormatException {
        Matcher m = Pattern.compile(DELETE_REGEX).matcher(input.trim());

        if (m.matches()) {
            String numberString = m.group(1);
            try {
                return Integer.parseInt(numberString) - 1;
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Task number must be a number.");
            }
        } else {
            throw new ParseException("Invalid delete command format. Use: delete <number>");
        }
    }
}