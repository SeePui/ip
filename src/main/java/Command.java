public abstract class Command {
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws StorageException;

    public boolean isExit() {
        return false;
    }
}