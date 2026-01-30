package pie.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import pie.BotMessage;
import pie.task.Task;

public class Ui {
    private static final String LINE =
            "________________________________________________________\n";

    public void printWelcome() {
        System.out.println(LINE + BotMessage.START.get() + LINE);
    }

    public void printBye() {
        System.out.println(LINE + BotMessage.BYE.get() + LINE);
    }

    public void printError(String message) {
        System.out.println(LINE + message + LINE);
    }

    public void printTaskAdded(Task task, int size) {
        System.out.println(LINE + "Got it. I've added this task:\n" + task
                + "\nNow you have " + size + " tasks in the list.\n" + LINE);
    }

    public void printTaskDeleted(Task task, int size) {
        System.out.println(LINE + "Noted. I've removed this task:\n" + task
                + "\nNow you have " + size + " tasks in the list.\n" + LINE);
    }

    public void printTaskMarked(Task task) {
        System.out.println(LINE + "Nice! I've marked this task as done:\n"
                + task + "\n" + LINE);
    }

    public void printTaskUnmarked(Task task) {
        System.out.println(LINE + "OK, I've marked this task as not done yet:\n"
                + task + "\n" + LINE);
    }

    public void printTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println(LINE + BotMessage.EMPTY_LIST.get() + LINE);
            return;
        }

        System.out.println(LINE + "Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        System.out.println(LINE);
    }

    public void printTasksOnDate(LocalDate date, List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println(LINE + BotMessage.EMPTY_LIST_ON_DATE.get() + LINE);
            return;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        System.out.println(LINE + "Here are the tasks on " + date.format(fmt) + ":");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        System.out.println(LINE);
    }
}
