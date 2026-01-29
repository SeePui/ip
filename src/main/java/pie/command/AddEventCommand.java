package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.Event;
import pie.task.Task;
import pie.task.TaskList;
import pie.ui.Ui;

public class AddEventCommand extends Command {
    private final Event event;

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