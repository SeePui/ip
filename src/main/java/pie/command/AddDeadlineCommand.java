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
        assert deadline != null : "Deadline must not be null";

        this.deadline = deadline;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        assert taskList != null : "TaskList must not be null when executing command";
        assert ui != null : "Ui must not be null when executing command";
        assert storage != null : "Storage must not be null when executing command";

        Task deadline = taskList.addDeadline(this.deadline);
        String outputMessage = MessageBuilder.taskAdded(deadline, taskList.getSize());
        ui.setMessage(outputMessage);
        storage.save(taskList.getAllTasks());
    }
}
