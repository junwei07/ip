package miro.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import miro.exception.MiroException;

public class EventTaskTest {
    @Test
    public void parse_validInput() {
        LocalDate currDate = LocalDate.now();
        String currDateFormatted = currDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String description = "deadline task";
        Task task = new EventTask(description, currDate, currDate);
        String expected = "E | 0 | deadline task | " + currDateFormatted + " to " + currDateFormatted;
        assertEquals(expected, task.getOutputFormat());

    }

    @Test
    public void parse_invalidUpdate_throwsException() {
        // CHECKSTYLE.OFF: AbbreviationAsWordInName
        String DESCRIPTION = "event task";
        String INVALID_DATE = "2024-10-10";
        // CHECKSTYLE.ON: AbbreviationAsWordInName
        LocalDate baseDate = LocalDate.now();
        String baseDateFormatted = baseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        Task invalidTask = new EventTask(DESCRIPTION, baseDate, baseDate);

        Exception fromDateException = assertThrows(MiroException.class, () -> {
            invalidTask.update(new String[] {"update", "1", "/from", INVALID_DATE});
        });
        assertEquals("Date cannot be in the past.", fromDateException.getMessage());

        Exception toDateException = assertThrows(MiroException.class, () -> {
            invalidTask.update(new String[] {"update", "1", "/to", INVALID_DATE});
        });
        assertEquals("Date cannot be in the past.", toDateException.getMessage());

        Exception toFromDateException = assertThrows(MiroException.class, () -> {
            invalidTask.update(new String[] {"update", "1", "/from", baseDateFormatted, "/to", INVALID_DATE});
        });
        assertEquals("Date cannot be in the past.", toFromDateException.getMessage());

    }
}
