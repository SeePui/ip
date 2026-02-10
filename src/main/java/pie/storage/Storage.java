package pie.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pie.BotMessage;
import pie.exception.StorageException;
import pie.task.Deadline;
import pie.task.Event;
import pie.task.Task;
import pie.task.Todo;
import pie.ui.Ui;

/**
 * Handles loading and saving tasks to persistent storage.
 *
 * <p>
 * Tasks are stored in a text file located at {@code data/pie.txt}.
 * Each task is saved in a single line. This class is responsible for
 * converting between file data and in-memory {@link Task} objects.
 * </p>
 */
public class Storage {

    private static final Path FILE_PATH = Paths.get("data", "pie.txt");

    // Indices for parsing stored task format
    private static final int TYPE_INDEX = 0;
    private static final int DONE_INDEX = 1;
    private static final int DESCRIPTION_INDEX = 2;
    private static final int DEADLINE_INDEX = 3;
    private static final int EVENT_FROM_INDEX = 3;
    private static final int EVENT_TO_INDEX = 4;

    /**
     * Loads tasks from the storage file.
     *
     * @return A list of tasks loaded from storage.
     * @throws StorageException If an I/O error occurs.
     */
    public List<Task> load() throws StorageException {
        List<Task> tasks = new ArrayList<>();

        try {
            ensureStorageExists();
            for (String line : Files.readAllLines(FILE_PATH)) {
                try {
                    tasks.add(parseTask(line));
                } catch (StorageException se) {
                    Ui ui;
                    ui = new Ui();
                    ui.setMessage(se.getMessage());
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new StorageException(BotMessage.ERROR_LOAD_FAILED.get());
        }
    }

    /**
     * Ensures the storage directory and file exist.
     *
     * @throws IOException If directory or file creation fails.
     */
    private void ensureStorageExists() throws IOException {
        Path directory = FILE_PATH.getParent();
        if (directory != null && !Files.exists(directory)) {
            Files.createDirectories(directory);
        }
        if (!Files.exists(FILE_PATH)) {
            Files.createFile(FILE_PATH);
        }
    }

    /**
     * Parses a single line from the storage file into a Task object.
     *
     * <p>
     * Expected formats:
     * <ul>
     *   <li>{@code T | isDone | description}</li>
     *   <li>{@code D | isDone | description | by}</li>
     *   <li>{@code E | isDone | description | from | to}</li>
     * </ul>
     * </p>
     *
     * @param input A single line from the storage file.
     * @return Parsed Task object.
     * @throws StorageException If the line is invalid or corrupted.
     */
    public Task parseTask(String input) throws StorageException {
        try {
            String[] parts = input.split("\\s*\\|\\s*");

            String taskType = parts[TYPE_INDEX];
            boolean isDone = parts[DONE_INDEX].equals("1");

            Task task = switch (taskType) {
            case "T" -> new Todo(parts[DESCRIPTION_INDEX]);
            case "D" -> new Deadline(
                    parts[DESCRIPTION_INDEX],
                    LocalDateTime.parse(parts[DEADLINE_INDEX])
            );
            case "E" -> new Event(
                    parts[DESCRIPTION_INDEX],
                    LocalDateTime.parse(parts[EVENT_FROM_INDEX]),
                    LocalDateTime.parse(parts[EVENT_TO_INDEX])
            );
            default -> throw new StorageException("Skipping unknown task type: " + input);
            };

            if (isDone) {
                task.markDone();
            }

            return task;
        } catch (Exception e) {
            throw new StorageException("Skipping corrupted line: " + input);
        }
    }

    /**
     * Saves all tasks to the storage file.
     *
     * <p>
     * Each task is converted to its storage string format using
     * {@link Task#toSaveString()} before being written to disk.
     * </p>
     *
     * @param tasks List of tasks to save.
     * @throws StorageException If an I/O error occurs while writing the file.
     */
    public void save(List<Task> tasks) throws StorageException {
        try {
            List<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(task.toSaveString());
            }

            Files.write(FILE_PATH, lines);
        } catch (IOException e) {
            throw new StorageException(BotMessage.ERROR_SAVE_FAILED.get());
        }
    }
}
