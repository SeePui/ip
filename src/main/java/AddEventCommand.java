public class AddEventCommand extends Command {
    private final Event event;

    public AddEventCommand(Event event) {
        this.event = event;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        Task event = taskList.addEvent(this.event);
        ui.printTaskAdded(event, taskList.getSize());
        storage.save(taskList.getAllTasks());
    }
}