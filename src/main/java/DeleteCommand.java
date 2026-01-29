public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        Task task = taskList.deleteTask(index);
        ui.printTaskDeleted(task, taskList.getSize());
        storage.save(taskList.getAllTasks());
    }
}