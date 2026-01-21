import java.util.Scanner;

public class Pie {
    static String chatbotName = "Pie";
    static String line = "\n________________________________________________________\n";

    public static void main(String[] args) {
        System.out.println(line + "Hello! I'm " + chatbotName + "\nWhat can I do for you?" + line);
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while (true) {
            if (command.equals("bye")) {
                System.out.println(line + "Bye. Hope to see you again soon!" + line);
                break;
            } else {
                System.out.println(line + command + line);
                command = scanner.nextLine();
            }
        }
        scanner.close();
    }
}