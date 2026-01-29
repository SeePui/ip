package pie;

import pie.command.Command;
import pie.exception.ParseException;
import pie.exception.StorageException;
import pie.parser.Parser;
import pie.storage.Storage;
import pie.task.TaskList;
import pie.ui.Ui;

import java.util.Scanner;

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
            ui.printError(e.getMessage());
            loadedTaskList = new TaskList();
        }
        taskList = loadedTaskList;
    }

    /**
     * Starts the main loop of the application.
     *
     * <p>
     * Reads user inputs, parses them, executes them,
     * and prints the results. Carry on until an exit command is given.
     * Exceptions are caught and handled by printing error messages.
     * </p>
     */
    public void run() {
        ui.printWelcome();
        boolean isExit = false;
        Scanner scanner = new Scanner(System.in);

        while (!isExit) {
            try {
                String input = scanner.nextLine();
                Command command = Parser.parseCommand(input);
                command.execute(taskList, ui, storage);
                isExit = command.isExit();

            } catch (ParseException | NumberFormatException | StorageException | IndexOutOfBoundsException e) {
                ui.printError(e.getMessage());
            } catch (Exception e) {
                ui.printError(BotMessage.ERROR_UNKNOWN.get());
            }
        }
    }

    /**
     * Represents the starting point of Pie application.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Pie().run();
    }
}