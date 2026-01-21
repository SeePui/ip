import java.util.*;

public class Pie {
    private static final String chatbotName = "Pie";
    private static final String line = "\n________________________________________________________\n";
    private static List<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {
        startMessage();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (true) {
            String[] commands = input.split(" ");
            String command = commands[0];

            if (input.equals("bye")) {
                byeMessage();
                break;
            } else if (input.equals("list")) {
                printTasks();
            } else if (command.equals("mark")) {
                int tempTaskNum = Integer.parseInt(commands[1]) - 1;
                markTask(tempTaskNum);
            } else if (command.equals("unmark")) {
                int tempTaskNum = Integer.parseInt(commands[1]) - 1;
                unmarkTask(tempTaskNum);
            } else {
                addTask(input);
            }

            input = scanner.nextLine();
        }
        scanner.close();
    }

    private static void startMessage() {
        System.out.println(line + "Hello! I'm " + chatbotName + "\nWhat can I do for you?" + line);
    }

    private static void byeMessage() {
        System.out.println(line + "Bye. Hope to see you again soon!" + line);
    }

    private static void addTask(String command) {
        System.out.println(line + "added: " + command + line);
        taskList.add(new Task(command));
    }

    private static void printTasks() {
        System.out.println(line + "Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            System.out.println(i + 1 + ".["
                    + task.getStatusIcon() + "] "
                    + task.getDescription());
            if (i == taskList.size() - 1) {
                System.out.println(line);
            }
        }
    }

    private static void markTask(int taskNumber) {
        taskList.get(taskNumber).markDone();
        Task task = taskList.get(taskNumber);
        System.out.println(line + "Nice! I've marked this task as done:\n"
                + "   [" + task.getStatusIcon() + "] " + task.getDescription() + line);
    }

    private static void unmarkTask(int taskNumber) {
        taskList.get(taskNumber).unmarkDone();
        Task task = taskList.get(taskNumber);
        System.out.println(line + "OK, I've marked this task as not done yet:\n"
                + "   [" + task.getStatusIcon() + "] " + task.getDescription() + line);
    }
}