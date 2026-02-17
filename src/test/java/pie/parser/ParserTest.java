package pie.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import pie.BotMessage;
import pie.command.AddTodoCommand;
import pie.command.Command;
import pie.command.DeleteCommand;
import pie.command.ExitCommand;
import pie.command.ListCommand;
import pie.command.MarkCommand;
import pie.command.UnmarkCommand;
import pie.exception.ParseException;
import pie.task.Deadline;
import pie.task.Event;

public class ParserTest {
    // ---------------------- Empty Commands ----------------------
    @Test
    public void parseCommand_emptyInput_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseCommand("")
        );
        assertEquals(BotMessage.ERROR_EMPTY_COMMAND.get(), e.getMessage());
    }

    // ---------------------- Single Commands ----------------------
    @Test
    public void parseSingleCommand_bye_success() throws Exception {
        assertInstanceOf(ExitCommand.class, Parser.parseCommand("bye"));
    }

    @Test
    public void parseSingleCommand_list_success() throws Exception {
        assertInstanceOf(ListCommand.class, Parser.parseCommand("list"));
    }

    @Test
    public void parseSingleCommand_extraArgs_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseCommand("bye hello world")
        );
        assertEquals(BotMessage.ERROR_INVALID_COMMAND.get(), e.getMessage());
    }

    @Test
    public void parseCommand_unknownCommand_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseCommand("hello")
        );
        assertEquals(BotMessage.ERROR_INVALID_COMMAND.get(), e.getMessage());
    }

    // ---------------------- Index-based Commands ----------------------
    @Test
    public void parseMarkUnmarkDelete_validIndex_success() throws Exception {
        assertInstanceOf(MarkCommand.class, Parser.parseCommand("mark 1"));
        assertInstanceOf(UnmarkCommand.class, Parser.parseCommand("unmark 2"));
        assertInstanceOf(DeleteCommand.class, Parser.parseCommand("delete 3"));
    }

    @Test
    public void parseIndex_invalidIndex_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseCommand("mark zero")
        );
        assertTrue(e.getMessage().contains("Use: <command> <positive number>"));
    }

    // ---------------------- Todo Command ----------------------
    @Test
    public void parseTodo_validDescription_success() throws Exception {
        Command c = Parser.parseCommand("todo read book");
        assertInstanceOf(AddTodoCommand.class, c);
    }

    @Test
    public void parseTodo_emptyDescription_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseCommand("todo ")
        );
        assertTrue(e.getMessage().contains("Use: todo <description>"));
    }

    // ---------------------- Deadline Command ----------------------
    @Test
    public void parseDeadline_validInput_success() throws Exception {
        Deadline deadline = Parser.parseDeadline(
                "deadline submit homework 1 /by 2026-02-10 2359"
        );
        assertEquals("submit homework 1", deadline.getDescription());
        assertEquals(LocalDateTime.of(2026, 2, 10, 23, 59), deadline.getBy());
    }

    @Test
    public void parseDeadline_invalidFormat_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseDeadline("deadline submit homework 1 by 2026-02-10 2359")
        );
        assertTrue(e.getMessage().contains("Use: deadline <description> /by yyyy-MM-dd HHmm"));
    }

    @Test
    public void parseDeadline_nonExistentDate_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseDeadline("deadline impossible /by 2026-02-30 1200")
        );
        assertEquals(BotMessage.ERROR_INVALID_DATE_FORMAT.get(), e.getMessage());
    }

    // ---------------------- Event Command ----------------------
    @Test
    public void parseEvent_validInput_success() throws Exception {
        Event event = Parser.parseEvent(
                "event meeting /from 2026-02-02 2000 /to 2026-02-04 2100"
        );
        assertEquals("meeting", event.getDescription());
        assertEquals(LocalDateTime.of(2026, 2, 2, 20, 0), event.getFrom());
        assertEquals(LocalDateTime.of(2026, 2, 4, 21, 0), event.getTo());
    }

    @Test
    public void parseEvent_startAfterEnd_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseEvent(
                        "event meeting /from 2026-02-04 2100 /to 2026-02-02 2000"
                )
        );
        assertEquals(BotMessage.ERROR_INVALID_DATE_RANGE.get(), e.getMessage());
    }

    @Test
    public void parseEvent_nonExistentDate_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseEvent(
                        "event impossible /from 2026-02-30 1200 /to 2026-03-01 1200"
                )
        );
        assertEquals(BotMessage.ERROR_INVALID_DATE_FORMAT.get(), e.getMessage());
    }

    @Test
    public void parseEvent_invalidFormat_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseEvent("event wrongformat /from tomorrow /to nextweek")
        );
        assertTrue(e.getMessage().contains(BotMessage.ERROR_INVALID_DATE_FORMAT.get()));
    }

    // ---------------------- On Command ----------------------
    @Test
    public void parseOnCommand_validDate_success() throws Exception {
        LocalDate date = Parser.parseOnCommand("on 2026-02-18");
        assertEquals(LocalDate.of(2026, 2, 18), date);
    }

    @Test
    public void parseOnCommand_invalidFormat_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseOnCommand("on 2026/04/03")
        );
        assertTrue(e.getMessage().contains(BotMessage.ERROR_INVALID_FORMAT.get()), e.getMessage());
    }

    // ---------------------- Find Command ----------------------
    @Test
    public void parseFind_validKeyword_success() throws Exception {
        String keyword = Parser.parseFind("find homework");
        assertEquals("homework", keyword);
    }

    @Test
    public void parseFind_missingKeyword_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseFind("find")
        );
        assertTrue(e.getMessage().contains("Use: find <keyword>"));
    }

    // ---------------------- Sort Command ----------------------
    @Test
    public void parseSort_validInput_success() throws Exception {
        String[] args = Parser.parseSort("sort by description asc");
        assertEquals("description", args[0]);
        assertEquals("asc", args[1]);
    }

    @Test
    public void parseSort_invalidField_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseSort("sort by priority asc")
        );
        assertTrue(e.getMessage().contains("Use: sort by <field> <order>"));
    }

    @Test
    public void parseSort_invalidOrder_parseExceptionThrown() {
        ParseException e = assertThrows(ParseException.class, () ->
                Parser.parseSort("sort by description upward")
        );
        assertTrue(e.getMessage().contains("Use: sort by <field> <order>"));
    }
}
