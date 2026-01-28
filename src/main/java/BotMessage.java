public enum BotMessage {
    START("Hello! I'm Pie\nWhat can I do for you? :)\n"),

    BYE("Bye. Have a good day! Hope to see you again soon!\n"),

    ERROR_EMPTY_COMMAND("Empty command. Please provide a valid command.\n"),

    ERROR_INVALID_COMMAND("Invalid command. Please provide a valid command.\n"),

    ERROR_INVALID_FORMAT("Invalid command format.\n"),

    ERROR_INVALID_INDEX("OOPS!!! This specified task does not exist!\nTry running list to check the available tasks.\n"),

    ERROR_NOT_NUMBER("Please provide a number.\n"),

    ERROR_LOAD_FAILED("Fail to load the saved tasks. Starting with an empty list.\n"),

    ERROR_SAVE_FAILED("Fail to save tasks to disk.\n"),

    ERROR_UNKNOWN("OOPS!!! Something went wrong. Please try again.\n"),

    EMPTY_LIST("There are no tasks in your list. Add tasks first!\n");

    private final String text;

    BotMessage(String text) {
        this.text = text;
    }

    public String get() {
        return text;
    }
}
