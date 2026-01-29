import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private static final String LINE = "________________________________________________________\n";
    protected List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void printTaskList() {
        if (this.tasks.isEmpty()) {
            System.out.println(LINE + BotMessage.EMPTY_LIST.get() + LINE);
            return;
        }

        System.out.println(LINE + "Here are the tasks in your list:");
        for (int i = 0; i < this.tasks.size(); i++) {
            Task task = this.tasks.get(i);
            System.out.println(i + 1 + "." + task.toString());
            if (i == this.tasks.size() - 1) {
                System.out.println(LINE);
            }
        }
    }

    public void markTask(int taskNumber) {
        try {
            Task task = this.tasks.get(taskNumber);
            task.markDone();
            System.out.println(LINE + "Nice! I've marked this task as done:\n"
                    + "  " + task.toString() + "\n" + LINE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(LINE + BotMessage.ERROR_INVALID_INDEX.get() + LINE);
        }
    }

    public void unmarkTask(int taskNumber) {
        try {
            Task task = this.tasks.get(taskNumber);
            task.unmarkDone();
            System.out.println(LINE + "OK, I've marked this task as not done yet:\n"
                    + "  " + task.toString() + "\n" + LINE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(LINE + BotMessage.ERROR_INVALID_INDEX.get() + LINE);
        }
    }

    public void addTodo(String description) {
        Todo newTodo = new Todo(description);
        this.tasks.add(newTodo);
        System.out.println(LINE + "Got it. I've added this task:\n"
                + "  " + newTodo.toString()
                + "\nNow you have " + this.tasks.size() + " tasks in the list.\n" + LINE);
    }

    public void addDeadline(Deadline newDeadline) {
        this.tasks.add(newDeadline);
        System.out.println(LINE + "Got it. I've added this task:\n"
                + "  " + newDeadline.toString()
                + "\nNow you have " + this.tasks.size() + " tasks in the list.\n" + LINE);
    }

    public void addEvent(Event newEvent) {
        this.tasks.add(newEvent);
        System.out.println(LINE + "Got it. I've added this task:\n"
                + "  " + newEvent.toString()
                + "\nNow you have " + this.tasks.size() + " tasks in the list.\n" + LINE);
    }

    public void deleteTask(int taskNumber) {
        try {
            Task task = this.tasks.get(taskNumber);
            this.tasks.remove(taskNumber);
            System.out.println(LINE + "Noted. I've removed this task:\n"
                    + "  " + task.toString()
                    + "\nNow you have " + this.tasks.size() + " tasks in the list.\n" + LINE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(LINE + BotMessage.ERROR_INVALID_INDEX.get() + LINE);
        }
    }

    public void printTaskListOnDate(LocalDate date) {
        try {
            ArrayList<Task> result = new ArrayList<>();
            for (Task task : this.tasks) {
                if (task.occursOn(date)) {
                    result.add(task);
                }
            }

            if (result.isEmpty()) {
                System.out.println(LINE + BotMessage.EMPTY_LIST_ON_DATE.get() + LINE);
                return;
            }

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
            System.out.println(LINE + "Here are the tasks on " + date.format(dateFormat) + ":");

            for (int i = 0; i < result.size(); i++) {
                Task task = result.get(i);
                System.out.println(i + 1 + "." + task.toString());
                if (i == result.size() - 1) {
                    System.out.println(LINE);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(LINE + BotMessage.ERROR_INVALID_INDEX.get() + LINE);
        }
    }
}
