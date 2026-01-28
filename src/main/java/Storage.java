import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final String LINE = "________________________________________________________\n";
    private static final Path FILE_PATH = Paths.get("data", "pie.txt");

    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();

        try {
            Path directory = FILE_PATH.getParent();
            if (directory != null && !Files.exists(directory)) {
//                System.out.println("Directory doesn't exist");
                Files.createDirectories(directory);
            }

            if (!Files.exists(FILE_PATH)) {
//                System.out.println("File doesn't exist");
                Files.createFile(FILE_PATH);
                return tasks;
            }

            List<String> lines = Files.readAllLines(FILE_PATH);
            for (String line : lines) {
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }

        } catch (IOException e) {
            System.out.println(LINE + BotMessage.ERROR_LOAD_FAILED.get() + LINE);
        }

        return tasks;
    }

    public Task parseTask(String input) {
        try {
            String[] parts = input.split("\\s*\\|\\s*");

            String taskType = parts[0];
            int isDone = Integer.parseInt(parts[1]);

            Task task = switch (taskType) {
                case "T" -> new Todo(parts[2]);
                case "D" -> {
                    LocalDateTime by = LocalDateTime.parse(parts[3]);
                    yield new Deadline(parts[2], by);
                }
                case "E" -> {
                    LocalDateTime from = LocalDateTime.parse(parts[3]);
                    LocalDateTime to = LocalDateTime.parse(parts[3]);
                    yield new Event(parts[2], from, to);
                }
                default -> throw new IllegalArgumentException();
            };

            if (isDone == 1) {
                task.markDone();
            }

            return task;

        } catch (Exception e) {
            System.out.println(LINE + "Skipping corrupted line: " + input + "\n" + LINE);
            return null;
        }
    }

    public void save(List<Task> tasks) {
        try {
//            System.out.println("Saving tasks:");
            List<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(task.toSaveString());
            }

            Files.write(FILE_PATH, lines);
        } catch (IOException e) {
            System.out.println(LINE + BotMessage.ERROR_SAVE_FAILED.get() + LINE);
        }
    }
}
