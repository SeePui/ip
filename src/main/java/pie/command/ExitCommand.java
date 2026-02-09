package pie.command;

import pie.storage.Storage;
import pie.task.TaskList;
import pie.ui.MessageBuilder;
import pie.ui.Ui;

/**
 * Represents a command that exits the Pie application.
 */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        assert ui != null : "Ui must not be null when executing command";

        ui.setMessage(MessageBuilder.bye());
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
