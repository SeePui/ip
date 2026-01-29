package pie.storage;

import pie.BotMessage;
import pie.exception.StorageException;
import pie.task.Deadline;
import pie.task.Event;
import pie.task.Task;
import pie.task.Todo;
import pie.ui.Ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final Path FILE_PATH = Paths.get("data", "pie.txt");

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
                    ui.printError(e.getMessage());
                }
            }

        } catch (IOException e) {
            throw new StorageException(BotMessage.ERROR_LOAD_FAILED.get());
        }

        return tasks;
    }

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
                default -> throw new IllegalArgumentException("Skipping unknown task type: " + input + "\n");
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
