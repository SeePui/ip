package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.Task;
import pie.task.TaskList;
import pie.ui.Ui;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        Task task = taskList.markTask(index);
        ui.printTaskMarked(task);
        storage.save(taskList.getAllTasks());
    }
}
