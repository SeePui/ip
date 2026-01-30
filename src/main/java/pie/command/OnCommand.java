package pie.command;

import java.time.LocalDate;
import java.util.List;

import pie.storage.Storage;
import pie.task.Task;
import pie.task.TaskList;
import pie.ui.Ui;

public class OnCommand extends Command {
    private final LocalDate date;

    public OnCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        List<Task> result = taskList.getTasksOnDate(date);
        ui.printTasksOnDate(date, result);
    }
}
