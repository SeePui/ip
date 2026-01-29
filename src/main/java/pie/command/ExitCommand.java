package pie.command;

import pie.storage.Storage;
import pie.task.TaskList;
import pie.ui.Ui;

public class ExitCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.printBye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}