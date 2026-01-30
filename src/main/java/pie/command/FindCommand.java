package pie.command;

import pie.storage.Storage;
import pie.task.Task;
import pie.task.TaskList;
import pie.ui.Ui;

import java.util.List;

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
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        List<Task> matchingTasks = taskList.findTasks(keyword);
        ui.printMatchingTasks(keyword, matchingTasks);
    }
}
