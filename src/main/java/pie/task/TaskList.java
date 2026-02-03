package pie.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pie.BotMessage;

/**
 * Represents a list of tasks.
 *
 * <p>
 * TaskList manages the creation, retrieval, modification, and deletion of
 * tasks. It also provides helper methods for querying tasks based on date
 * and updating their completion status.
 * </p>
 */
public class TaskList {
    protected List<Task> tasks;

    /**
     * Instantiates an empty Task ist.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Instantiates a new Task list.
     *
     * @param tasks A list of tasks to initialize the TaskList with.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Checks whether the task list is empty.
     *
     * @return true if the task list contains no tasks, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return Total number of tasks.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Retrieves a task at the specified index.
     *
     * <p>
     * This method validates the index and throws an exception if the index
     * is invalid.
     * </p>
     *
     * @param index Index of the task to retrieve.
     * @return The task at the specified index.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    private Task getTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException(BotMessage.ERROR_INVALID_INDEX.get());
        }
        return tasks.get(index);
    }

    /**
     * Returns a list of tasks that occur on the specified date.
     *
     * @param date The date to check against.
     * @return A list of tasks occurring on the given date.
     */
    public List<Task> getTasksOnDate(LocalDate date) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.occursOn(date)) {
                result.add(task);
            }
        }
        return result;
    }

    /**
     * Returns all tasks in the task list.
     *
     * @return List of all tasks.
     */
    public List<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index Index of the task to mark.
     * @return The updated task.
     */
    public Task markTask(int index) {
        Task task = getTask(index);
        task.markDone();
        return task;
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param index Index of the task to unmark.
     * @return The updated task.
     */
    public Task unmarkTask(int index) {
        Task task = getTask(index);
        task.unmarkDone();
        return task;
    }

    /**
     * Adds a new Todo task to the task list.
     *
     * @param description Description of the todo task.
     * @return The added Todo task.
     */
    public Task addTodo(String description) {
        Task todo = new Todo(description);
        tasks.add(todo);
        return todo;
    }

    /**
     * Adds a Deadline task to the task list.
     *
     * @param deadline The deadline task to add.
     * @return The added Deadline task.
     */
    public Task addDeadline(Deadline deadline) {
        tasks.add(deadline);
        return deadline;
    }

    /**
     * Adds an Event task to the task list.
     *
     * @param event The event task to add.
     * @return The added Event task.
     */
    public Task addEvent(Event event) {
        tasks.add(event);
        return event;
    }

    /**
     * Deletes the task at the specified index.
     *
     * @param index Index of the task to delete.
     * @return The deleted task.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public Task deleteTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException(BotMessage.ERROR_INVALID_INDEX.get());
        }

        return tasks.remove(index);
    }

    /**
     * Returns a list of tasks whose descriptions contain the given keyword.
     * Case-insensitive.
     *
     * @param keyword The keyword to search for.
     * @return List of tasks containing the keyword.
     */
    public List<Task> findTasks(String keyword) {
        String lowerCaseKeyword = keyword.toLowerCase();
        return tasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(lowerCaseKeyword))
                .collect(Collectors.toList());
    }
}
