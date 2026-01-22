import java.util.*;

public class Pie {
    private static final String chatbotName = "Pie";
    private static final String line = "\n________________________________________________________\n";
    private static List<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {
        startMessage();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String input = scanner.nextLine();
                String command = Parser.parseCommand(input);

                if (command.equals("bye")) {
                    byeMessage();
                    break;
                } else if (command.equals("list")) {
                    printTasks();
                } else if (command.equals("mark")) {
                    markTask(Parser.parseMarkNumber(input));
                } else if (command.equals("unmark")) {
                    unmarkTask(Parser.parseUnmarkNumber(input));
                } else if (command.equals("todo")) {
                    addTodo(Parser.parseTodo(input));
                } else if (command.equals("deadline")) {
                    addDeadline(Parser.parseDeadline(input));
                } else if (command.equals("event")) {
                    addEvent(Parser.parseEvent(input));
                } else if (command.equals("delete")) {
                    deleteTask(Parser.parseDeleteNumber(input));
                }
            } catch (ParseException | NumberFormatException e) {
                System.out.println(line + e.getMessage() + line);
            }
        }

        scanner.close();
    }

    private static void startMessage() {
        System.out.println(line + "Hello! I'm " + chatbotName + "\nWhat can I do for you? :)" + line);
    }

    private static void byeMessage() {
        System.out.println(line + "Bye. Have a good day! Hope to see you again soon!" + line);
    }

    private static void printTasks() {
        if (taskList.isEmpty()) {
            System.out.println(line + "There are no tasks in your list. Add tasks first!" + line);
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
                    + "  " + task.toString() + line);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(line + "OOPS!!! This specified task does not exist!"
                    + "\nTry running list to check the available tasks." + line);
        }
    }

    private static void unmarkTask(int taskNumber) {
        try {
            taskList.get(taskNumber).unmarkDone();
            Task task = taskList.get(taskNumber);
            System.out.println(line + "OK, I've marked this task as not done yet:\n"
                    + "  " + task.toString() + line);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(line + "OOPS!!! This specified task does not exist!"
                    + "\nTry running list to check the available tasks." + line);
        }
    }

    private static void addTodo(String description) {
        Todo newTodo = new Todo(description);
        taskList.add(newTodo);
        System.out.println(line + "Got it. I've added this task:\n"
                + "  " + newTodo.toString()
                + "\nNow you have " + taskList.size() + " tasks in the list." + line);
    }

    private static void addDeadline(Deadline newDeadline) {
        taskList.add(newDeadline);
        System.out.println(line + "Got it. I've added this task:\n"
                + "  " + newDeadline.toString()
                + "\nNow you have " + taskList.size() + " tasks in the list." + line);
    }

    private static void addEvent(Event newEvent) {
        taskList.add(newEvent);
        System.out.println(line + "Got it. I've added this task:\n"
                + "  " + newEvent.toString()
                + "\nNow you have " + taskList.size() + " tasks in the list." + line);
    }

    private static void deleteTask(int taskNumber) {
        try {
            Task task = taskList.get(taskNumber);
            taskList.remove(taskNumber);
            System.out.println(line + "Noted. I've removed this task:\n"
                    + "  " + task.toString()
                    + "\nNow you have " + taskList.size() + " tasks in the list." + line);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(line + "OOPS!!! This specified task does not exist!"
                    + "\nTry running list to check the available tasks." + line);
        }
    }
}