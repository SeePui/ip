public class AddDeadlineCommand extends Command {
    private final Deadline deadline;

    public AddDeadlineCommand(Deadline deadline) {
        this.deadline = deadline;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        Task deadline = taskList.addDeadline(this.deadline);
        ui.printTaskAdded(deadline, taskList.getSize());
        storage.save(taskList.getAllTasks());
    }
}