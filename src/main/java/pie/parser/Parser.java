package pie.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pie.BotMessage;
import pie.command.AddDeadlineCommand;
import pie.command.AddEventCommand;
import pie.command.AddTodoCommand;
import pie.command.Command;
import pie.command.DeleteCommand;
import pie.command.ExitCommand;
import pie.command.FindCommand;
import pie.command.ListCommand;
import pie.command.MarkCommand;
import pie.command.OnCommand;
import pie.command.SortCommand;
import pie.command.UnmarkCommand;
import pie.exception.ParseException;
import pie.task.Deadline;
import pie.task.Event;

/**
 * Parses user input into executable commands and task objects.
 *
 * <p>
 * This class is responsible for validating user input, extracting
 * required parameters, and constructing appropriate {@link Command}
 * objects. All parsing-related errors are reported using
 * {@link ParseException}.
 * </p>
 */
public class Parser {
    private static final String INDEX_REGEX = "^(mark|unmark|delete)\\s+(\\d+)$";
    private static final String TODO_REGEX = "^todo\\s+(.*)$";
    private static final String DEADLINE_REGEX = "^deadline\\s+(.+)\\s+/by\\s+(.+)$";
    private static final String EVENT_REGEX = "^event\\s+(.+)\\s+/from\\s+(.+)\\s+/to\\s+(.+)$";
    private static final String ON_REGEX = "^on\\s+(\\d{4}-\\d{2}-\\d{2})$";
    private static final String FIND_REGEX = "^find\\s+(.*)$";
    private static final String SORT_REGEX = "^sort\\s+by\\s+(description|deadline|status)\\s+(asc|desc)$";

    /**
     * Parses the user input and returns the corresponding Command.
     *
     * @param input Raw user input.
     * @return A Command object representing the user instruction.
     * @throws ParseException If the command is empty, invalid, or unsupported.
     */
    public static Command parseCommand(String input) throws ParseException {
        if (input == null || input.trim().isEmpty()) {
            throw new ParseException(BotMessage.ERROR_EMPTY_COMMAND.get());
        }

        String[] parts = input.trim().split("\\s+", 2);
        String commandType = parts[0].toLowerCase();

        return switch (commandType) {
        case "bye" -> new ExitCommand();
        case "list" -> new ListCommand();
        case "mark" -> new MarkCommand(parseIndex(input));
        case "unmark" -> new UnmarkCommand(parseIndex(input));
        case "delete" -> new DeleteCommand(parseIndex(input));
        case "todo" -> new AddTodoCommand(parseTodo(input));
        case "deadline" -> new AddDeadlineCommand(parseDeadline(input));
        case "event" -> new AddEventCommand(parseEvent(input));
        case "on" -> new OnCommand(parseOnCommand(input));
        case "find" -> new FindCommand(parseFind(input));
        case "sort" -> {
            String[] sortArgs = parseSort(input);
            yield new SortCommand(sortArgs[0], sortArgs[1]);
        }
        default -> throw new ParseException(BotMessage.ERROR_INVALID_COMMAND.get());
        };
    }

    /**
     * Parses an index from commands that require one.
     *
     * <p>
     * Converts user-provided 1-based index into a 0-based index.
     * </p>
     *
     * @param input User input containing the index.
     * @return Zero-based task index.
     * @throws ParseException If the format or index is invalid.
     */
    public static int parseIndex(String input) throws ParseException {
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

    /**
     * Parses a todo command and extracts its description.
     *
     * @param input User input.
     * @return String Todo description.
     * @throws ParseException If the format is invalid.
     */
    public static String parseTodo(String input) throws ParseException {
        Matcher m = Pattern.compile(TODO_REGEX).matcher(input.trim());

        if (m.matches()) {
            return m.group(1);
        } else {
            throw new ParseException(BotMessage.ERROR_INVALID_FORMAT.get()
                    + "Use: todo <description>\n");
        }
    }

    /**
     * Parses a deadline command and creates a Deadline task.
     *
     * @param input User input.
     * @return Parsed Deadline object.
     * @throws ParseException If the format or date-time is invalid.
     */
    public static Deadline parseDeadline(String input) throws ParseException {
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

    /**
     * Parses an event command and creates an Event task.
     *
     * @param input User input.
     * @return Parsed Event object.
     * @throws ParseException If the format or date-time is invalid.
     */
    public static Event parseEvent(String input) throws ParseException {
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

            if (!from.isBefore(to)) {
                throw new ParseException(BotMessage.ERROR_INVALID_DATE_RANGE.get());
            }

            return new Event(description, from, to);
        } catch (DateTimeParseException e) {
            throw new ParseException(BotMessage.ERROR_INVALID_DATE_FORMAT.get());
        }
    }

    /**
     * Parses the on-date command and extracts the date.
     *
     * @param input User input.
     * @return Parsed LocalDate.
     * @throws ParseException If the date format is invalid.
     */
    public static LocalDate parseOnCommand(String input) throws ParseException {
        Matcher m = Pattern.compile(ON_REGEX).matcher(input.trim());
        if (!m.matches()) {
            throw new ParseException(BotMessage.ERROR_INVALID_FORMAT.get()
                    + "Use: on yyyy-MM-dd\n");
        }

        try {
            String dateStr = m.group(1);
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new ParseException(BotMessage.ERROR_INVALID_ON_DATE_FORMAT.get());
        }
    }

    /**
     * Parses the find command and extracts the word to find.
     *
     * @param input User input.
     * @return keyword to find.
     * @throws ParseException If the find format is invalid.
     */
    public static String parseFind(String input) throws ParseException {
        Matcher m = Pattern.compile(FIND_REGEX).matcher(input.trim());

        if (m.matches()) {
            return m.group(1);
        } else {
            throw new ParseException(BotMessage.ERROR_INVALID_FORMAT.get()
                    + "Use: find <keyword>\n");
        }
    }

    /**
     * Parses the sort command and extracts sorting type and order.
     *
     * @param input User input.
     * @return Sort parameters as a String array: [type, order].
     * @throws ParseException If the sort format is invalid.
     */
    public static String[] parseSort(String input) throws ParseException {
        Matcher m = Pattern.compile(SORT_REGEX).matcher(input.trim());

        if (!m.matches()) {
            throw new ParseException(BotMessage.ERROR_INVALID_FORMAT.get() + """
                    Use: sort by <field> <order>
                    Fields: deadline, description, status
                    Order: asc, desc""");
        }

        String field = m.group(1);
        String order = m.group(2);

        return new String[]{field, order};
    }
}
