import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final String INDEX_REGEX = "^(mark|unmark|delete)\\s+(\\d+)$";
    private static final String TODO_REGEX = "^todo\\s+(.*)$";
    private static final String DEADLINE_REGEX = "^deadline\\s+(.+)\\s+/by\\s+(.+)$";
    private static final String EVENT_REGEX = "^event\\s+(.+)\\s+/from\\s+(.+)\\s+/to\\s+(.+)$";

    public static CommandType parseCommand(String input)
            throws ParseException {
        if (input == null || input.trim().isEmpty()) {
            throw new ParseException(BotMessage.ERROR_EMPTY_COMMAND.get());
        }
        return CommandType.from(input);
    }

    public static int parseIndex(String input)
            throws ParseException {
        Matcher m = Pattern.compile(INDEX_REGEX).matcher(input.trim());

        if (!m.matches()) {
            throw new ParseException(BotMessage.ERROR_INVALID_FORMAT.get()
                    + "Use: <command> <positive number>\n");
        }

        try {
            return Integer.parseInt(m.group(2)) - 1;
        } catch (NumberFormatException e) {
            throw new ParseException(BotMessage.ERROR_NOT_NUMBER.get());
        }
    }


    public static String parseTodo(String input) throws ParseException {
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

        if (!m.matches()) {
            throw new ParseException(BotMessage.ERROR_INVALID_FORMAT.get()
                    + "Use: deadline <description> /by yyyy-MM-dd HHmm\n");
        }

        try {
            String description = m.group(1);
            String byStr = m.group(2);

            DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            LocalDateTime by = LocalDateTime.parse(byStr, customFormat);
            return new Deadline(description, by);
        } catch (DateTimeParseException e) {
            throw new ParseException(BotMessage.ERROR_INVALID_DATE_FORMAT.get());
        }
    }

    public static Event parseEvent(String input)
            throws ParseException {
        Matcher m = Pattern.compile(EVENT_REGEX).matcher(input.trim());

        if (!m.matches()) {
            throw new ParseException(BotMessage.ERROR_INVALID_FORMAT.get()
                    + "Use: event <description> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm\n");
        }

        try {
            String description = m.group(1);
            String fromStr = m.group(2);
            String toStr = m.group(3);

            DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            LocalDateTime from = LocalDateTime.parse(fromStr, customFormat);
            LocalDateTime to = LocalDateTime.parse(toStr, customFormat);
            return new Event(description, from, to);
        } catch (DateTimeParseException e) {
            throw new ParseException(BotMessage.ERROR_INVALID_DATE_FORMAT.get());
        }
    }
}