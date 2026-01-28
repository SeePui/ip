import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pie {
    private static final String LINE = "________________________________________________________\n";
    private static List<Task> taskList;
    private static final Storage storage = new Storage();

    public static void main(String[] args) {
        startMessage();
        loadTasks();

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
                    printTaskList();
                    break;
                case MARK:
                    markTask(Parser.parseIndex(input));
                    break;
                case UNMARK:
                    unmarkTask(Parser.parseIndex(input));
                    break;
                case TODO:
                    addTodo(Parser.parseTodo(input));
                    break;
                case DEADLINE:
                    addDeadline(Parser.parseDeadline(input));
                    break;
                case EVENT:
                    addEvent(Parser.parseEvent(input));
                    break;
                case DELETE:
                    deleteTask(Parser.parseIndex(input));
                    break;
                }
            } catch (ParseException | NumberFormatException e) {
                System.out.println(LINE + e.getMessage() + LINE);
            } catch (Exception e) {
                System.out.println(LINE + BotMessage.ERROR_UNKNOWN.get() + LINE);
            }
        }
    }

    private static void loadTasks() {
        try {
            taskList = storage.load();
        } catch (Exception e) {
            System.out.println(LINE + BotMessage.ERROR_LOAD_FAILED.get() + LINE);
            taskList = new ArrayList<>();
        }
    }

    private static void saveTasks() {
        try {
            storage.save(taskList);
        } catch (Exception e) {
            System.out.println(LINE + BotMessage.ERROR_SAVE_FAILED.get() + LINE);
        }
    }

    private static void startMessage() {
        System.out.println(LINE + BotMessage.START.get() + LINE);
    }

    private static void byeMessage() {
        System.out.println(LINE + BotMessage.BYE.get() + LINE);
    }

    private static void printTaskList() {
        if (taskList.isEmpty()) {
            System.out.println(LINE + BotMessage.EMPTY_LIST.get() + LINE);
            return;
        }

        System.out.println(LINE + "Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            System.out.println(i + 1 + "." + task.toString());
            if (i == taskList.size() - 1) {
                System.out.println(LINE);
            }
        }
    }

    private static void markTask(int taskNumber) {
        try {
            Task task = taskList.get(taskNumber);
            task.markDone();
            saveTasks();
            System.out.println(LINE + "Nice! I've marked this task as done:\n"
                    + "  " + task.toString() + "\n" + LINE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(LINE + BotMessage.ERROR_INVALID_INDEX.get() + LINE);
        }
    }

    private static void unmarkTask(int taskNumber) {
        try {
            Task task = taskList.get(taskNumber);
            task.unmarkDone();
            saveTasks();
            System.out.println(LINE + "OK, I've marked this task as not done yet:\n"
                    + "  " + task.toString() + "\n" + LINE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(LINE + BotMessage.ERROR_INVALID_INDEX.get() + LINE);
        }
    }

    private static void addTodo(String description) {
        Todo newTodo = new Todo(description);
        taskList.add(newTodo);
        saveTasks();
        System.out.println(LINE + "Got it. I've added this task:\n"
                + "  " + newTodo.toString()
                + "\nNow you have " + taskList.size() + " tasks in the list.\n" + LINE);
    }

    private static void addDeadline(Deadline newDeadline) {
        taskList.add(newDeadline);
        saveTasks();
        System.out.println(LINE + "Got it. I've added this task:\n"
                + "  " + newDeadline.toString()
                + "\nNow you have " + taskList.size() + " tasks in the list.\n" + LINE);
    }

    private static void addEvent(Event newEvent) {
        taskList.add(newEvent);
        saveTasks();
        System.out.println(LINE + "Got it. I've added this task:\n"
                + "  " + newEvent.toString()
                + "\nNow you have " + taskList.size() + " tasks in the list.\n" + LINE);
    }

    private static void deleteTask(int taskNumber) {
        try {
            Task task = taskList.get(taskNumber);
            taskList.remove(taskNumber);
            saveTasks();
            System.out.println(LINE + "Noted. I've removed this task:\n"
                    + "  " + task.toString()
                    + "\nNow you have " + taskList.size() + " tasks in the list.\n" + LINE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(LINE + BotMessage.ERROR_INVALID_INDEX.get() + LINE);
        }
    }
}