package miro.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import miro.exception.MiroException;

public class DeadlineTaskTest {
    @Test
    public void parse_validInput() {
        LocalDate currDate = LocalDate.now();
        String inputDate = currDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"));

        String description = "deadline task";
        Task task = new DeadlineTask(description, currDate);
        String expected = "D | 0 | deadline task | " + currDate;
        assertEquals(expected, task.getOutputFormat());

    }

    @Test
    public void parse_invalidUpdate_throwsException() {
        String DESCRIPTION = "deadline task";
        String INVALID_DATE = "2024-10-10";
        LocalDate baseDate = LocalDate.now();

        Task task = new DeadlineTask(DESCRIPTION, baseDate);
        String expected = "D | 0 | deadline task | " + baseDate;
        assertEquals(expected, task.getOutputFormat());

        Task invalidTask = new DeadlineTask(DESCRIPTION, baseDate);
        Exception deadlineException = assertThrows(MiroException.class, () -> {
            invalidTask.update(new String[] {"update", "1", "/by", INVALID_DATE});
        });
        assertEquals("Date cannot be in the past.", deadlineException.getMessage());

    }
}
