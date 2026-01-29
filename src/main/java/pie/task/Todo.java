package pie.task;

/**
 * Represents a Todo task.
 *
 * <p>
 * A Todo is a simple task with only a description and no date or time.
 * </p>
 */
public class Todo extends Task {

    /**
     * Instantiates a new Todo.
     *
     * @param description Description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the todo task for display.
     *
     * @return String in the format "[T][status] description"
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a string representation of the todo task for saving to storage.
     *
     * <p>
     * Format: "T | isDone | description"
     * </p>
     *
     * @return Todo task as a string suitable for saving
     */
    @Override
    public String toSaveString() {
        return "T | " + (getIsDone() ? "1" : "0") + " | " + getDescription();
    }
}
