public enum CommandType {
    LIST,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    BYE,
    ON;

    public static CommandType from(String input) throws ParseException {
        String command = input.trim().split("\\s+", 2)[0].toUpperCase();
        try {
            return CommandType.valueOf(command);
        } catch (IllegalArgumentException e) {
            throw new ParseException(BotMessage.ERROR_INVALID_COMMAND.get());
        }
    }
}