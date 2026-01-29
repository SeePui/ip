import java.time.LocalDate;
import java.util.List;

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
