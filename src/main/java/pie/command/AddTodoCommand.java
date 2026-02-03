package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.Task;
import pie.task.TaskList;
import pie.task.Todo;
import pie.ui.MessageBuilder;
import pie.ui.Ui;

/**
 * Represents a command that adds a {@link Todo} task to the task list.
 */
public class AddTodoCommand extends Command {
    private final String description;

    /**
     * Instantiates a new {@code AddTodoCommand} with the specified todo task.
     *
     * @param description Todo description.
     */
    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        Task todo = taskList.addTodo(this.description);
        String outputMessage = MessageBuilder.taskAdded(todo, taskList.getSize());
        ui.setMessage(outputMessage);
        storage.save(taskList.getAllTasks());
    }
}
