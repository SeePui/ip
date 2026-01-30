package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.TaskList;
import pie.ui.Ui;

/**
 * Represents a command that lists all tasks in the task list.
 */
public class ListCommand extends Command {

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        ui.printTaskList(taskList.getAllTasks());
    }
}