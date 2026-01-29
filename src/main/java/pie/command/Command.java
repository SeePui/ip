package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.TaskList;
import pie.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException;

    public boolean isExit() {
        return false;
    }
}