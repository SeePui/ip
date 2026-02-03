package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.Task;
import pie.task.TaskList;
import pie.ui.MessageBuilder;
import pie.ui.Ui;

/**
 * Represents a command that marks a task in the task list as done.
 */
public class MarkCommand extends Command {
    private final int index;

    /**
     * Instantiates a new {@code MarkCommand} for marking the task at the specified index.
     *
     * @param index The zero-based index of the task to mark as done.
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        Task task = taskList.markTask(index);
        String outputMessage = MessageBuilder.taskMarked(task);
        ui.setMessage(outputMessage);
        storage.save(taskList.getAllTasks());
    }
}
