package pie.command;

import pie.exception.StorageException;
import pie.storage.Storage;
import pie.task.TaskList;
import pie.task.Todo;
import pie.ui.MessageBuilder;
import pie.ui.Ui;

/**
 * Represents a command that adds a {@link Todo} task to the task list.
 */
public class SortCommand extends Command {
    private final String field;
    private final String order;

    /**
     * Instantiates a new {@code SortCommand} with the specified field and order.
     *
     * @param field Field to sort by (deadline)
     * @param order Order of sorting (asc, desc)
     */
    public SortCommand(String field, String order) {
        assert field != null : "Field must not be null";
        assert !field.isBlank() : "field must not be blank";
        assert order != null : "Order must not be null";
        assert !order.isBlank() : "Order must not be blank";

        this.field = field;
        this.order = order;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        assert taskList != null : "TaskList must not be null when executing command";
        assert ui != null : "Ui must not be null when executing command";
        assert storage != null : "Storage must not be null when executing command";

        taskList.sortTasks(field, order);
        String outputSortedMessage = MessageBuilder.taskSorted(field, order);
        String outputListMessage = MessageBuilder.taskList(taskList.getAllTasks());
        String outputMessage = outputSortedMessage + "\n" + outputListMessage;
        ui.setMessage(outputMessage);
        storage.save(taskList.getAllTasks());
    }
}
