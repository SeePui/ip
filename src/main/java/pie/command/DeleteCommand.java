package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.Task;
import pie.task.TaskList;
import pie.ui.Ui;

/**
 * Represents a command that deletes a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Instantiates a new {@code DeleteCommand} for deleting the task at the given index.
     *
     * @param index The zero-based index of the task to be deleted.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        Task task = taskList.deleteTask(index);
        ui.printTaskDeleted(task, taskList.getSize());
        storage.save(taskList.getAllTasks());
    }
}