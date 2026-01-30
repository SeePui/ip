package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.Task;
import pie.task.TaskList;
import pie.ui.Ui;

public class AddTodoCommand extends Command {
    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        Task todo = taskList.addTodo(this.description);
        ui.printTaskAdded(todo, taskList.getSize());
        storage.save(taskList.getAllTasks());
    }
}
