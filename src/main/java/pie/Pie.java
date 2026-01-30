package pie;

import java.util.Scanner;

import pie.command.Command;
import pie.exception.ParseException;
import pie.exception.StorageException;
import pie.parser.Parser;
import pie.storage.Storage;
import pie.task.TaskList;
import pie.ui.Ui;

public class Pie {
    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

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

            } catch (ParseException | NumberFormatException
                     | StorageException | IndexOutOfBoundsException e) {
                ui.printError(e.getMessage());
            } catch (Exception e) {
                ui.printError(BotMessage.ERROR_UNKNOWN.get());
            }
        }
    }

    public static void main(String[] args) {
        new Pie().run();
    }
}
