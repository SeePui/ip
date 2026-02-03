package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.Deadline;
import pie.task.Task;
import pie.task.TaskList;
import pie.ui.MessageBuilder;
import pie.ui.Ui;

/**
 * Represents a command that adds a {@link Deadline} task to the task list.
 */
public class AddDeadlineCommand extends Command {
    private final Deadline deadline;

    /**
     * Instantiates a new {@code AddDeadlineCommand} with the specified deadline task.
     *
     * @param deadline The {@link Deadline} task to be added.
     */
    public AddDeadlineCommand(Deadline deadline) {
        this.deadline = deadline;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        Task deadline = taskList.addDeadline(this.deadline);
        String outputMessage = MessageBuilder.taskAdded(deadline, taskList.getSize());
        ui.setMessage(outputMessage);
        storage.save(taskList.getAllTasks());
    }
}
