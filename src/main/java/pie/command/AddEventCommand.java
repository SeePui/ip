package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.Event;
import pie.task.Task;
import pie.task.TaskList;
import pie.ui.Ui;

/**
 * Represents a command that adds a {@link Event} task to the task list.
 */
public class AddEventCommand extends Command {
    private final Event event;

    /**
     * Instantiates a new {@code AddEventCommand} with the specified event task.
     *
     * @param event The {@link Event} task to be added.
     */
    public AddEventCommand(Event event) {
        this.event = event;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        Task event = taskList.addEvent(this.event);
        ui.printTaskAdded(event, taskList.getSize());
        storage.save(taskList.getAllTasks());
    }
}
