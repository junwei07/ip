package miro.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task.
 */
public class DeadlineTask extends Task {
    private final LocalDate date;

    /**
     * The constructor for the deadline task.
     *
     * @param description The description of the task.
     * @param date The deadline of the task.
     */
    public DeadlineTask(String description, LocalDate date) {
        super(description);
        this.date = date;
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + formatDate(date) + ")";
    }

    @Override
    public String getOutputFormat() {
        return "D | " + super.getOutputFormat() + " | " + date;
    }
}
