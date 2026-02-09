package pie.command;

import java.util.List;

import pie.storage.Storage;
import pie.task.Task;
import pie.task.TaskList;
import pie.ui.MessageBuilder;
import pie.ui.Ui;

/**
 * Represents a command that finds tasks containing a given keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Instantiates a new {@code FindCommand} with the specified keyword.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        assert keyword != null : "Find keyword must not be null";
        assert !keyword.isBlank() : "Find keyword must not be blank";

        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        assert taskList != null : "TaskList must not be null when executing command";
        assert ui != null : "Ui must not be null when executing command";

        List<Task> matchingTasks = taskList.findTasks(keyword);
        String outputMessage = MessageBuilder.matchingTasks(keyword, matchingTasks);
        ui.setMessage(outputMessage);
    }
}
