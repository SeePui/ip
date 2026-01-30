package pie.task;

import pie.BotMessage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskList {
    protected List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int getSize() {
        return tasks.size();
    }

    private Task getTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException(BotMessage.ERROR_INVALID_INDEX.get());
        }
        return tasks.get(index);
    }

    public List<Task> getTasksOnDate(LocalDate date) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.occursOn(date)) {
                result.add(task);
            }
        }
        return result;
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task markTask(int index) {
        Task task = getTask(index);
        task.markDone();
        return task;
    }

    public Task unmarkTask(int index) {
        Task task = getTask(index);
        task.unmarkDone();
        return task;
    }

    public Task addTodo(String description) {
        Task todo = new Todo(description);
        tasks.add(todo);
        return todo;
    }

    public Task addDeadline(Deadline deadline) {
        tasks.add(deadline);
        return deadline;
    }

    public Task addEvent(Event event) {
        tasks.add(event);
        return event;
    }

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
