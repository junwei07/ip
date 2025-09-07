package miro.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task.
 */
public class EventTask extends Task {
    private final LocalDate toDate;
    private final LocalDate fromDate;

    /**
     * The constructor for the event task.
     *
     * @param description The description of the task.
     * @param fromDate The start date of the event task.
     * @param toDate The end date of the event task.
     */
    public EventTask(String description, LocalDate fromDate, LocalDate toDate) {
        super(description);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: " + formatDate(fromDate) + " to: " + formatDate(toDate) + ")";
    }

    @Override
    public String getOutputFormat() {
        return "E | " + super.getOutputFormat() + " | " + fromDate + " to " + toDate;
    }
}
