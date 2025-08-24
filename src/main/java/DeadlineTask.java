import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {
    private final LocalDate date;

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
