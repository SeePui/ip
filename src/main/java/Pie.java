import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pie {
    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

    public Pie() {
        ui = new Ui();
        storage = new Storage();

        ui.printWelcome();

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
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String input = scanner.nextLine();
                CommandType command = Parser.parseCommand(input);

                switch (command) {
                case BYE:
                    ui.printBye();
                    scanner.close();
                    return;
                case LIST:
                    ui.printTaskList(taskList.getAllTasks());
                    break;
                case MARK:
                    Task mt = taskList.markTask(Parser.parseIndex(input));
                    ui.printTaskMarked(mt);
                    storage.save(taskList.getAllTasks());
                    break;
                case UNMARK:
                    Task umt = taskList.unmarkTask(Parser.parseIndex(input));
                    ui.printTaskUnmarked(umt);
                    storage.save(taskList.getAllTasks());
                    break;
                case TODO:
                    Task todo = taskList.addTodo(Parser.parseTodo(input));
                    ui.printTaskAdded(todo, taskList.getSize());
                    storage.save(taskList.getAllTasks());
                    break;
                case DEADLINE:
                    Task deadline = taskList.addDeadline(Parser.parseDeadline(input));
                    ui.printTaskAdded(deadline, taskList.getSize());
                    storage.save(taskList.getAllTasks());
                    break;
                case EVENT:
                    Task event = taskList.addEvent(Parser.parseEvent(input));
                    ui.printTaskAdded(event, taskList.getSize());
                    storage.save(taskList.getAllTasks());
                    break;
                case DELETE:
                    Task dt = taskList.deleteTask(Parser.parseIndex(input));
                    ui.printTaskDeleted(dt, taskList.getSize());
                    storage.save(taskList.getAllTasks());
                    break;
                case ON:
                    LocalDate inputDate = Parser.parseOnCommand(input);
                    List<Task> result = taskList.getTasksOnDate(inputDate);
                    ui.printTasksOnDate(inputDate, result);
                }
            } catch (ParseException | NumberFormatException | StorageException | IndexOutOfBoundsException e) {
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