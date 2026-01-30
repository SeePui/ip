package pie.command;

import pie.storage.Storage;
import pie.task.TaskList;
import pie.ui.Ui;

/**
 * Represents a command that exits the Pie application.
 */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.printBye();
    }

    /**
     * Indicates that this command causes the application to exit.
     *
     * @return {@code true}, as this is an exit command.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
