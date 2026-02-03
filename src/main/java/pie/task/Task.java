package pie.task;

import java.time.LocalDate;

/**
 * Represents a generic task in the Pie application.
 *
 * <p>
 * A task has a description and a completion status. It provides methods
 * to mark the task as done or undone, and to format the task for display
 * or saving.
 * </p>
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Instantiates a new Task.
     *
     * @param description textual description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unmarkDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }

    /**
     * Returns a string representation of the task for saving to storage.
     *
     * <p>
     * Format: "1 | description" if done, "0 | description" if not done.
     * </p>
     *
     * @return Task as a string suitable for saving.
     */
    public String toSaveString() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Determines whether the task occurs on the given date.
     *
     * @param date Date to check.
     * @return true if the task occurs on the date, false otherwise.
     */
    public boolean occursOn(LocalDate date) {
        return false;
    }
}
