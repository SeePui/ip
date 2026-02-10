package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.TaskList;
import pie.ui.MessageBuilder;
import pie.ui.Ui;

/**
 * Represents an executable user command in the Pie application.
 * <p>
 * Each command encapsulates a specific user action and defines how it
 * should be executed using the task list, user interface, and storage.
 * </p>
 */
public abstract class Command {
    /**
     * Executes the command to get the message to display from the
     * {@link MessageBuilder} and updating the user interface message.
     *
     * @param taskList The task list to operate on.
     * @param ui       The user interface used to store the message to be displayed.
     * @param storage  The storage used to persist the data.
     * @throws StorageException If an error occurs while saving or loading tasks.
     */
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException;
}
