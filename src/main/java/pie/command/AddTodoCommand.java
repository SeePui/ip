package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.Deadline;
import pie.task.Task;
import pie.task.TaskList;
import pie.ui.Ui;

/**
 * Represents a command that adds a {@link Todo} task to the task list.
 */
public class AddTodoCommand extends Command {
    private final String description;

    /**
     * Instantiates a new {@code AddTodoCommand} with the specified todo task.
     *
     * @param description Todo description
     */
    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        Task todo = taskList.addTodo(this.description);
        ui.printTaskAdded(todo, taskList.getSize());
        storage.save(taskList.getAllTasks());
    }
}