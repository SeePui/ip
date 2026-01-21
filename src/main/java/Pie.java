import java.util.*;

public class Pie {
    private static String chatbotName = "Pie";
    private static final String line = "\n________________________________________________________\n";

    public static void main(String[] args) {
        startMessage();

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        List<Task> taskList = new ArrayList<>();

        while (true) {
            if (command.equals("bye")) {
                byeMessage();
                break;
            } else if(command.equals("list")) {
                System.out.println(line);
                for (int i = 0; i < taskList.size(); i++) {
                    System.out.println(i+1 + ". " + taskList.get(i).getDescription());
                    if (i  == taskList.size() - 1) {
                        System.out.println(line);
                    }
                }
            }
            else {
                System.out.println(line + "added: " + command + line);
                taskList.add(new Task(command));
            }
            command = scanner.nextLine();
        }
        scanner.close();
    }

    private static void startMessage() {
        System.out.println(line + "Hello! I'm " + chatbotName + "\nWhat can I do for you?" + line);
    }

    private static void byeMessage() {
        System.out.println(line + "Bye. Hope to see you again soon!" + line);
    }
}