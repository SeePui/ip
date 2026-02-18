package pie;

/**
 * Represents predefined user-facing messages used by the Pie application.
 *
 * <p>
 * Each enum constant stores a message string that can be displayed to the user.
 * </p>
 */
public enum BotMessage {
    START("""
            Hello! I\'m Pie!
            Ready to help you stay on top of your tasks.
            Type a command and let\'s begin!"""),

    BYE("Bye. Have a good day! Hope to see you again soon!\n"),

    ERROR_EMPTY_COMMAND("Empty command. Please provide a valid command.\n"),

    ERROR_INVALID_COMMAND("Invalid command. Please provide a valid command.\n"),

    ERROR_INVALID_FORMAT("Invalid command format.\n"),

    ERROR_INVALID_DATE_FORMAT("Invalid date format. Use yyyy-MM-dd HHmm.\n"),

    ERROR_INVALID_DATE_RANGE("Invalid date format.\nStart date/time must be before end date/time.\n"),

    ERROR_INVALID_ON_DATE_FORMAT("Invalid date format. Use yyyy-MM-dd.\n"),

    ERROR_INVALID_INDEX("""
            OOPS!!! This specified task does not exist!
            Try running list to check the available tasks.
            """),

    ERROR_NOT_NUMBER("Please provide a number.\n"),

    ERROR_LOAD_FAILED("Fail to load the saved tasks. Starting with an empty list.\n"),

    ERROR_SAVE_FAILED("Fail to save tasks to disk.\n"),

    ERROR_UNKNOWN("OOPS!!! Something went wrong. Please try again.\n"),

    EMPTY_LIST("There are no tasks in your list. Add tasks first!\n"),

    EMPTY_LIST_ON_DATE("There are no tasks in your list on this date!\n");

    private final String text;

    /**
     * Constructs a BotMessage with the given message text.
     *
     * @param text Message string associated with the enum constant
     */
    BotMessage(String text) {
        this.text = text;
    }

    public String get() {
        return text;
    }
}
