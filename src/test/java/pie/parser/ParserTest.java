package pie.parser;

import org.junit.jupiter.api.Test;
import pie.BotMessage;
import pie.command.AddTodoCommand;
import pie.command.Command;
import pie.command.MarkCommand;
import pie.exception.ParseException;
import pie.task.Deadline;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    @Test
    public void parseCommand_todoCommand_success() throws Exception {
        Command c = Parser.parseCommand("todo read book");

        assertInstanceOf(AddTodoCommand.class, c);
    }

    @Test
    public void parseCommand_markCommand_success() throws Exception {
        Command c = Parser.parseCommand("mark 1");

        assertInstanceOf(MarkCommand.class, c);
    }

    @Test
    public void parseCommand_invalidCommand_parseExceptionThrown() {
        assertThrows(ParseException.class, () ->
                Parser.parseCommand("unknown")
        );
    }

    @Test
    public void parseCommand_emptyInput_parseExceptionThrown() {
        assertThrows(ParseException.class, () ->
                Parser.parseCommand("")
        );
    }

    @Test
    public void parseDeadline_validInput_returnsDeadline() throws Exception {
        Deadline deadline = Parser.parseDeadline(
                "deadline submit assignment 1 /by 2026-02-10 2359"
        );

        assertEquals("submit assignment 1", deadline.getDescription());
        assertEquals(
                LocalDateTime.of(2026, 2, 10, 23, 59),
                deadline.getBy()
        );
    }

    @Test
    public void parseDeadline_invalidDateFormat_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseDeadline(
                        "deadline submit assignment 1 /by tomorrow"
                )
        );

        assertEquals(
                BotMessage.ERROR_INVALID_DATE_FORMAT.get(),
                e.getMessage()
        );
    }
}
