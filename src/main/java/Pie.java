import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pie {
    private static final String LINE = "________________________________________________________\n";
    private final Storage storage;
    private final TaskList taskList;

    public Pie() {
        startMessage();
        storage = new Storage();

        TaskList loadedTaskList;
        try {
            loadedTaskList = new TaskList(storage.load());
        } catch (StorageException e) {
            System.out.println(LINE + e.getMessage() + LINE);
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
                    byeMessage();
                    scanner.close();
                    return;
                case LIST:
                    taskList.printTaskList();
                    break;
                case MARK:
                    taskList.markTask(Parser.parseIndex(input));
                    storage.save(taskList.getTasks());
                    break;
                case UNMARK:
                    taskList.unmarkTask(Parser.parseIndex(input));
                    storage.save(taskList.getTasks());
                    break;
                case TODO:
                    taskList.addTodo(Parser.parseTodo(input));
                    storage.save(taskList.getTasks());
                    break;
                case DEADLINE:
                    taskList.addDeadline(Parser.parseDeadline(input));
                    storage.save(taskList.getTasks());
                    break;
                case EVENT:
                    taskList.addEvent(Parser.parseEvent(input));
                    storage.save(taskList.getTasks());
                    break;
                case DELETE:
                    taskList.deleteTask(Parser.parseIndex(input));
                    storage.save(taskList.getTasks());
                    break;
                case ON:
                    taskList.printTaskListOnDate(Parser.parseOnCommand(input));
                }
            } catch (ParseException | NumberFormatException | StorageException e) {
                System.out.println(LINE + e.getMessage() + LINE);
            } catch (Exception e) {
                System.out.println(LINE + BotMessage.ERROR_UNKNOWN.get() + LINE);
            }
        }
    }

    private static void startMessage() {
        System.out.println(LINE + BotMessage.START.get() + LINE);
    }

    private static void byeMessage() {
        System.out.println(LINE + BotMessage.BYE.get() + LINE);
    }

    public static void main(String[] args) {
        new Pie().run();
    }
}