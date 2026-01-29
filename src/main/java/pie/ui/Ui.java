package pie.ui;

import pie.BotMessage;
import pie.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Handles all user interface interactions for the Pie application.
 *
 * <p>
 * This class is responsible for displaying messages, task lists, and
 * feedback to the user.
 * </p>
 */
public class Ui {
    private static final String LINE =
            "________________________________________________________\n";

    /**
     * Prints the welcome message when the application starts.
     */
    public void printWelcome() {
        System.out.println(LINE + BotMessage.START.get() + LINE);
    }

    /**
     * Prints the goodbye message when the application exits.
     */
    public void printBye() {
        System.out.println(LINE + BotMessage.BYE.get() + LINE);
    }

    /**
     * Prints an error message.
     *
     * @param message Error message to display
     */
    public void printError(String message) {
        System.out.println(LINE + message + LINE);
    }

    /**
     * Prints a confirmation message when a task is added.
     *
     * @param task The task that was added
     * @param size The total number of tasks after addition
     */
    public void printTaskAdded(Task task, int size) {
        System.out.println(LINE +
                "Got it. I've added this task:\n  " + task +
                "\nNow you have " + size + " tasks in the list.\n" +
                LINE);
    }

    /**
     * Prints a confirmation message when a task is deleted.
     *
     * @param task The task that was deleted
     * @param size The total number of tasks after deletion
     */
    public void printTaskDeleted(Task task, int size) {
        System.out.println(LINE +
                "Noted. I've removed this task:\n  " + task +
                "\nNow you have " + size + " tasks in the list.\n" +
                LINE);
    }

    /**
     * Prints a confirmation message when a task is marked as done.
     *
     * @param task The task that was marked as done
     */
    public void printTaskMarked(Task task) {
        System.out.println(LINE +
                "Nice! I've marked this task as done:\n  " + task +
                "\n" + LINE);
    }

    /**
     * Prints a confirmation message when a task is unmarked.
     *
     * @param task The task that was marked as not done
     */
    public void printTaskUnmarked(Task task) {
        System.out.println(LINE +
                "OK, I've marked this task as not done yet:\n  " + task +
                "\n" + LINE);
    }

    /**
     * Prints all tasks in the task list.
     *
     * <p>
     * If the task list is empty, an empty list message is shown instead.
     * </p>
     *
     * @param tasks List of tasks to display
     */
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

    /**
     * Prints tasks that occur on a specific date.
     *
     * <p>
     * If no tasks occur on the given date, an empty list message is shown.
     * </p>
     *
     * @param date  The date to filter tasks by
     * @param tasks List of tasks occurring on the given date
     */
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
