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

    /**
     * Loads the tasks from the storage file.
     *
     * <p>
     * If the storage directory or file does not exist, they will be created.
     * Corrupted or invalid task lines are skipped with an error message shown
     * to the user.
     * </p>
     *
     * @return A list of tasks loaded from storage.
     * @throws StorageException If an I/O error occurs while reading the file.
     */
    public List<Task> load() throws StorageException {
        List<Task> tasks = new ArrayList<>();

        try {
            Path directory = FILE_PATH.getParent();
            if (directory != null && !Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            if (!Files.exists(FILE_PATH)) {
                Files.createFile(FILE_PATH);
                return tasks;
            }

            List<String> lines = Files.readAllLines(FILE_PATH);
            for (String line : lines) {
                try {
                    tasks.add(parseTask(line));
                } catch (StorageException e) {
                    Ui ui = new Ui();
                    ui.setMessage(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new StorageException(BotMessage.ERROR_LOAD_FAILED.get());
        }

        return tasks;
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

            String taskType = parts[0];
            boolean isDone = parts[1].equals("1");

            Task task = switch (taskType) {
            case "T" -> new Todo(parts[2]);
            case "D" -> {
                LocalDateTime by = LocalDateTime.parse(parts[3]);
                yield new Deadline(parts[2], by);
            }
            case "E" -> {
                LocalDateTime from = LocalDateTime.parse(parts[3]);
                LocalDateTime to = LocalDateTime.parse(parts[4]);
                yield new Event(parts[2], from, to);
            }
            default -> throw new IllegalArgumentException(
                    "Skipping unknown task type: " + input + "\n");
            };

            if (isDone) {
                task.markDone();
            }

            return task;

        } catch (IllegalArgumentException iea) {
            throw new StorageException(iea.getMessage());
        } catch (Exception e) {
            throw new StorageException("Skipping corrupted line: " + input + "\n");
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
