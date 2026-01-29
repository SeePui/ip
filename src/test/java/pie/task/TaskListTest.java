package pie.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    @Test
    public void markTask_validIndex_marksTaskAsDone() {
        TaskList taskList = new TaskList();
        taskList.addTodo("return book");
        Task markedTask = taskList.markTask(0);

        assertTrue(markedTask.getIsDone());
    }

    @Test
    public void markTask_invalidIndex_exceptionThrown() {
        TaskList tasks = new TaskList();

        assertThrows(IndexOutOfBoundsException.class, () ->
                tasks.markTask(0)
        );
    }

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
}
