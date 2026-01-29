public class AddTodoCommand extends Command {
    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException {
        Task todo = taskList.addTodo(this.description);
        ui.printTaskAdded(todo, taskList.getSize());
        storage.save(taskList.getAllTasks());
    }
}