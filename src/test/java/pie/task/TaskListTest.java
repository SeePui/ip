package pie.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    // ---------------------- Mark/Unmark Tasks ----------------------
    @Test
    public void markTask_validIndex_marksTaskAsDone() {
        TaskList taskList = new TaskList();
        taskList.addTodo("return book");
        Task markedTask = taskList.markTask(0);

        assertTrue(markedTask.getIsDone());
    }

    @Test
    public void markTask_invalidIndex_exceptionThrown() {
        TaskList taskList = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.markTask(0));
    }

    @Test
    public void unmarkTask_validIndex_unmarksTask() {
        TaskList taskList = new TaskList();
        Task task = taskList.addTodo("read book");
        taskList.markTask(0); // mark it done
        Task unmarkedTask = taskList.unmarkTask(0);

        assertFalse(unmarkedTask.getIsDone());
    }

    @Test
    public void unmarkTask_invalidIndex_exceptionThrown() {
        TaskList taskList = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.unmarkTask(0));
    }

    // ---------------------- Add Tasks ----------------------
    @Test
    public void addTodo_validDescription_todoAdded() {
        TaskList taskList = new TaskList();
        Task task = taskList.addTodo("read book");

        assertNotNull(task);
        assertInstanceOf(Todo.class, task);
        assertEquals("read book", task.getDescription());
        assertEquals(1, taskList.getSize());
        assertEquals(task, taskList.getAllTasks().get(0));
    }

    @Test
    public void addDeadline_validDeadline_deadlineAdded() {
        TaskList taskList = new TaskList();
        Deadline deadline = new Deadline("submit homework", LocalDateTime.of(2026, 2, 10, 23, 59));
        Task added = taskList.addDeadline(deadline);

        assertEquals(deadline, added);
        assertEquals(1, taskList.getSize());
    }

    @Test
    public void addEvent_validEvent_eventAdded() {
        TaskList taskList = new TaskList();
        Event event = new Event("meeting", LocalDateTime.of(2026, 2, 2, 20, 0),
                LocalDateTime.of(2026, 2, 4, 21, 0));
        Task added = taskList.addEvent(event);

        assertEquals(event, added);
        assertEquals(1, taskList.getSize());
    }

    // ---------------------- Delete Tasks ----------------------
    @Test
    public void deleteTask_validIndex_taskDeleted() {
        TaskList taskList = new TaskList();
        Task task = taskList.addTodo("read book");
        Task deleted = taskList.deleteTask(0);

        assertEquals(task, deleted);
        assertEquals(0, taskList.getSize());
    }

    @Test
    public void deleteTask_invalidIndex_exceptionThrown() {
        TaskList taskList = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.deleteTask(0));
    }

    // ---------------------- Get Tasks On Date ----------------------
    @Test
    public void getTasksOnDate_returnsCorrectTasks() {
        TaskList taskList = new TaskList();
        Deadline d1 = new Deadline("submit homework", LocalDateTime.of(2026, 2, 10, 23, 59));
        Event e1 = new Event("meeting", LocalDateTime.of(2026, 2, 10, 10, 0), LocalDateTime.of(2026, 2, 10, 12, 0));
        taskList.addDeadline(d1);
        taskList.addEvent(e1);

        List<Task> tasksOnFeb10 = taskList.getTasksOnDate(LocalDate.of(2026, 2, 10));
        assertEquals(2, tasksOnFeb10.size());
        assertTrue(tasksOnFeb10.contains(d1));
        assertTrue(tasksOnFeb10.contains(e1));
    }

    // ---------------------- Find Tasks ----------------------
    @Test
    public void findTasks_caseInsensitive_returnsCorrectTasks() {
        TaskList taskList = new TaskList();
        taskList.addTodo("Read book");
        taskList.addTodo("Buy book");
        taskList.addTodo("Shop clothes");

        List<Task> results = taskList.findTasks("book");
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(t -> t.getDescription().toLowerCase().contains("book")));
    }

    // ---------------------- Sort Tasks ----------------------
    @Test
    public void sortTasks_byDescription_asc() {
        TaskList taskList = new TaskList();
        taskList.addTodo("b task");
        taskList.addTodo("a task");
        taskList.sortTasks("description", "asc");

        assertEquals("a task", taskList.getAllTasks().get(0).getDescription());
        assertEquals("b task", taskList.getAllTasks().get(1).getDescription());
    }

    @Test
    public void sortTasks_byStatus_desc() {
        TaskList taskList = new TaskList();
        Task t1 = taskList.addTodo("task1"); // not done
        Task t2 = taskList.addTodo("task2"); // not done
        taskList.markTask(0); // t1 done
        taskList.sortTasks("status", "desc"); // done tasks last?

        assertTrue(taskList.getAllTasks().get(0).getIsDone()); // ascending by false->true, reversed = true->false
    }

    @Test
    public void sortTasks_byDeadline_asc() {
        TaskList taskList = new TaskList();
        Deadline d1 = new Deadline("d1", LocalDateTime.of(2026, 2, 10, 12, 0));
        Deadline d2 = new Deadline("d2", LocalDateTime.of(2026, 2, 9, 12, 0));
        taskList.addDeadline(d1);
        taskList.addDeadline(d2);

        taskList.sortTasks("deadline", "asc");
        assertEquals(d2, taskList.getAllTasks().get(0));
        assertEquals(d1, taskList.getAllTasks().get(1));
    }
}
