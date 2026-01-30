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

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toSaveString() {
        return "T | " + (getIsDone() ? "1" : "0") + " | " + getDescription();
    }
}
