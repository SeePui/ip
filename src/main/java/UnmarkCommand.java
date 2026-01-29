public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        Task task = taskList.unmarkTask(index);
        ui.printTaskUnmarked(task);
        storage.save(taskList.getAllTasks());
    }
}