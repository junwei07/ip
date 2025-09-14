package miro.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import miro.exception.MiroException;
import miro.utils.Utils;

/**
 * Represents an event task.
 */
public class EventTask extends Task {
    private LocalDate toDate;
    private LocalDate fromDate;

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

    @Override
    public void update(String[] words) throws MiroException {
        String[] params = getUpdatedParams(words);
        String newDescription = params[0];
        String newFromDate = params[1];
        String newToDate = params[2];

        System.out.println(newFromDate);
        System.out.println(newToDate);

        if (!newDescription.isEmpty()) {
            super.updateDescription(newDescription);
        }

        if (!newFromDate.isEmpty()) {
            this.fromDate = LocalDate.parse(newFromDate);
        }

        if (!newToDate.isEmpty()) {
            this.toDate = LocalDate.parse(newToDate);
        }
    }

    private String[] getUpdatedParams(String[] words) throws MiroException {
        boolean isFrom = false;
        boolean isTo = false;

        StringBuilder taskSb = new StringBuilder();
        StringBuilder fromSb = new StringBuilder();
        StringBuilder toSb = new StringBuilder();

        for (int i = 2; i < words.length; i++) {
            if (isFrom && !words[i].equals("/to")) {
                fromSb.append(words[i]);
                fromSb.append(" ");

            } else if (isTo) {
                toSb.append(words[i]);
                toSb.append(" ");
            } else {
                if (!words[i].equals("/from") && !words[i].equals("/to")) {
                    taskSb.append(words[i]);
                    taskSb.append(" ");
                }
            }

            if (words[i].equals("/from")) {
                isFrom = true;
            } else if (words[i].equals("/to")) {
                isFrom = false;
                isTo = true;
            }
        }

        String inputFromDate = fromSb.toString().strip();
        String inputToDate = toSb.toString().strip();

        if (!inputFromDate.isEmpty() && !inputToDate.isEmpty()) {
            Utils.isValidToFromDates(inputFromDate, inputToDate);
        } else if (!inputFromDate.isEmpty()) {
            Utils.isValidToFromDates(inputFromDate, this.toDate.toString());
        } else if (!inputToDate.isEmpty()) {
            Utils.isValidToFromDates(this.fromDate.toString(), inputToDate);
        }

        if (taskSb.isEmpty() && fromSb.isEmpty() && toSb.isEmpty()) {
            throw new MiroException("Nothing to update!");
        } else if (!fromSb.isEmpty() && !Utils.isValidDate(inputFromDate)) {
            throw new MiroException("Please specify a date using \"/by ...\"");
        } else if (!toSb.isEmpty() && !Utils.isValidDate(inputToDate)) {
            throw new MiroException("Please specify a date using \"/by ...\"");
        }

        return new String[]{taskSb.toString().strip(), inputFromDate, inputToDate};
    }
}
