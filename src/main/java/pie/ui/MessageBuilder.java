package pie.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import pie.BotMessage;
import pie.task.Task;

/**
 * Utility class for building messages for the Pie application.
 *
 * <p>
 * This class centralizes the formatting of messages that are displayed to the user
 * when tasks are added, deleted, marked, or listed.
 * </p>
 */
public class MessageBuilder {
    /**
     * Builds a goodbye message for the application exit.
     *
     * @return Formatted goodbye message.
     */
    public static String bye() {
        return BotMessage.BYE.get();
    }

    /**
     * Builds a message confirming a task has been added.
     *
     * @param task Task that was added.
     * @param size Total number of tasks after addition.
     * @return Formatted task-added message.
     */
    public static String taskAdded(Task task, int size) {
        return "Got it. I've added this task:\n" + task
                + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Builds a message confirming a task has been deleted.
     *
     * @param task Task that was deleted.
     * @param size Total number of tasks after deletion.
     * @return Formatted task-deleted message.
     */
    public static String taskDeleted(Task task, int size) {
        return "Noted. I've removed this task:\n" + task
                + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Builds a message confirming a task has been marked as done.
     *
     * @param task Task that was marked as done.
     * @return Formatted task-marked message.
     */
    public static String taskMarked(Task task) {
        return "Nice! I've marked this task as done:\n" + task;
    }

    /**
     * Builds a message confirming a task has been unmarked.
     *
     * @param task Task that was marked as not done.
     * @return Formatted task-unmarked message.
     */
    public static String taskUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n" + task;
    }

    /**
     * Builds a formatted list of all tasks.
     *
     * <p>If the task list is empty, returns a message indicating so.</p>
     *
     * @param tasks List of tasks.
     * @return Formatted task list message.
     */
    public static String taskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return BotMessage.EMPTY_LIST.get();
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Builds a formatted list of tasks occurring on a specific date.
     *
     * <p>If no tasks occur on the given date, returns a message indicating so.</p>
     *
     * @param date  The date to filter tasks.
     * @param tasks List of tasks on that date.
     * @return Formatted tasks-on-date message.
     */
    public static String tasksOnDate(LocalDate date, List<Task> tasks) {
        if (tasks.isEmpty()) {
            return BotMessage.EMPTY_LIST_ON_DATE.get();
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        StringBuilder sb = new StringBuilder("Here are the tasks on " + date.format(fmt) + ":\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Builds a formatted list of tasks matching a search keyword.
     *
     * <p>If no tasks match, returns a message indicating so.</p>
     *
     * @param keyword The search keyword.
     * @param tasks   List of matching tasks.
     * @return Formatted matching-tasks message.
     */
    public static String matchingTasks(String keyword, List<Task> tasks) {
        if (tasks.isEmpty()) {
            return "No tasks match the keyword: \"" + keyword + "\"";
        }

        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }
}
