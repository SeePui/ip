package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.Task;
import pie.task.TaskList;
import pie.ui.MessageBuilder;
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
        assert taskList != null : "TaskList must not be null when executing command";
        assert ui != null : "Ui must not be null when executing command";
        assert storage != null : "Storage must not be null when executing command";

        Task task = taskList.unmarkTask(index);
        String outputMessage = MessageBuilder.taskUnmarked(task);
        ui.setMessage(outputMessage);
        storage.save(taskList.getAllTasks());
    }
}
