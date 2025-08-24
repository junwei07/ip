public class EventTask extends Task {
    private final String toDate;
    private String fromDate;

    public EventTask(String description, String fromDate, String toDate) {
        super(description);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: " + this.fromDate + " to: " + this.toDate + ")";
    }

    @Override
    public String getOutputFormat() {
        return "E | " + super.getOutputFormat() + " | " + fromDate + "-" + toDate;
    }
}
