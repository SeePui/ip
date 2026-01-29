package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.Task;
import pie.task.TaskList;
import pie.ui.Ui;

/**
 * Represents a command that unmarks a task in the task list as done.
 */
public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Instantiates a new {@code UnmarkCommand} for unmarking the task at the specified index.
     *
     * @param index The zero-based index of the task to unmark as done.
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        Task task = taskList.unmarkTask(index);
        ui.printTaskUnmarked(task);
        storage.save(taskList.getAllTasks());
    }
}