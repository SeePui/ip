package pie;

import pie.command.Command;
import pie.exception.ParseException;
import pie.exception.StorageException;
import pie.parser.Parser;
import pie.storage.Storage;
import pie.task.TaskList;
import pie.ui.Ui;

/**
 * Represents the main class for the Pie application.
 *
 * <p>
 * This class is responsible for initializing the application components
 * (UI, storage, and task list), and running the main event loop that
 * accepts user commands, executes them, and handles errors.
 * </p>
 */
public class Pie {
    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

    /**
     * Constructs a new Pie application instance.
     *
     * <p>
     * Initializes the UI, storage, and loads tasks from storage.
     * If the storage loading fails, starts with an empty task list
     * and prints an error message.
     * </p>
     */
    public Pie() {
        ui = new Ui();
        storage = new Storage();

        TaskList loadedTaskList;
        try {
            loadedTaskList = new TaskList(storage.load());
        } catch (StorageException e) {
            ui.setMessage(e.getMessage());
            loadedTaskList = new TaskList();
        }
        taskList = loadedTaskList;
    }

    /**
     * Processes a single user input by parsing and executing the corresponding command.
     *
     * <p>
     * Reads user inputs, parses them and executes them.
     * Exceptions are caught and handled by printing error messages.
     * </p>
     *
     * @param input The user input to process.
     */
    public void run(String input) {
        try {
            Command command = Parser.parseCommand(input);
            command.execute(taskList, ui, storage);
            ui.setErrorMessage(false);
        } catch (ParseException | NumberFormatException
                 | StorageException | IndexOutOfBoundsException e) {
            ui.setMessage(e.getMessage());
            ui.setErrorMessage(true);
        } catch (Exception e) {
            ui.setMessage(BotMessage.ERROR_UNKNOWN.get());
            ui.setErrorMessage(true);
        }
    }

    /**
     * Returns the {@link Ui} instance used by this application.
     *
     * @return The user interface instance.
     */
    public Ui getUi() {
        return ui;
    }
}
