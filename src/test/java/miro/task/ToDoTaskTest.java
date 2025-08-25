package miro.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTaskTest {
    @Test
    public void testOutputFormat() {
        String description = "test task";
        Task task = new ToDoTask(description);
        String expected = "T | 0 | test task";
        assertEquals(expected, task.getOutputFormat());

        description = "";
        task = new ToDoTask(description);
        expected = "T | 0 | ";
        assertEquals(expected, task.getOutputFormat());
    }

}
