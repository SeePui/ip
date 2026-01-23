import java.util.*;

public class Pie {
    private static final String line = "________________________________________________________\n";
    private static List<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {
        startMessage();
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
                        printTasks();
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
                System.out.println(line + e.getMessage() + line);
            }
        }
    }

    private static void startMessage() {
        System.out.println(line + BotMessage.START.get() + line);
    }

    private static void byeMessage() {
        System.out.println(line + BotMessage.BYE.get() + line);
    }

    private static void printTasks() {
        if (taskList.isEmpty()) {
            System.out.println(line + BotMessage.EMPTY_LIST.get() + line);
            return;
        }

        System.out.println(line + "Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            System.out.println(i + 1 + "." + task.toString());
            if (i == taskList.size() - 1) {
                System.out.println(line);
            }
        }
    }

    private static void markTask(int taskNumber) {
        try {
            taskList.get(taskNumber).markDone();
            Task task = taskList.get(taskNumber);
            System.out.println(line + "Nice! I've marked this task as done:\n"
                    + "  " + task.toString() + "\n" + line);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(line + BotMessage.ERROR_INVALID_INDEX.get() + line);
        }
    }

    private static void unmarkTask(int taskNumber) {
        try {
            taskList.get(taskNumber).unmarkDone();
            Task task = taskList.get(taskNumber);
            System.out.println(line + "OK, I've marked this task as not done yet:\n"
                    + "  " + task.toString() + "\n" + line);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(line + BotMessage.ERROR_INVALID_INDEX.get() + line);
        }
    }

    private static void addTodo(String description) {
        Todo newTodo = new Todo(description);
        taskList.add(newTodo);
        System.out.println(line + "Got it. I've added this task:\n"
                + "  " + newTodo.toString()
                + "\nNow you have " + taskList.size() + " tasks in the list.\n" + line);
    }

    private static void addDeadline(Deadline newDeadline) {
        taskList.add(newDeadline);
        System.out.println(line + "Got it. I've added this task:\n"
                + "  " + newDeadline.toString()
                + "\nNow you have " + taskList.size() + " tasks in the list.\n" + line);
    }

    private static void addEvent(Event newEvent) {
        taskList.add(newEvent);
        System.out.println(line + "Got it. I've added this task:\n"
                + "  " + newEvent.toString()
                + "\nNow you have " + taskList.size() + " tasks in the list.\n" + line);
    }

    private static void deleteTask(int taskNumber) {
        try {
            Task task = taskList.get(taskNumber);
            taskList.remove(taskNumber);
            System.out.println(line + "Noted. I've removed this task:\n"
                    + "  " + task.toString()
                    + "\nNow you have " + taskList.size() + " tasks in the list.\n" + line);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(line + BotMessage.ERROR_INVALID_INDEX.get() + line);
        }
    }
}