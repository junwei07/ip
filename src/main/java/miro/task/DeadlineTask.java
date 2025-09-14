package miro.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import miro.exception.MiroException;
import miro.utils.Utils;

/**
 * Represents a deadline task.
 */
public class DeadlineTask extends Task {
    private LocalDate date;

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

    @Override
    public void update(String[] words) throws MiroException {
        String[] params = getUpdatedParams(words);
        String newDescription = params[0];
        String newDate = params[1];

        if (!newDescription.isEmpty()) {
            super.updateDescription(newDescription);
        }

        if (!newDate.isEmpty()) {
            this.date = LocalDate.parse(newDate);
        }
    }

    private String[] getUpdatedParams(String[] words) throws MiroException {
        boolean isDate = false;
        StringBuilder taskSb = new StringBuilder();
        StringBuilder dateSb = new StringBuilder();

        for (int i = 2; i < words.length; i++) {
            if (isDate) {
                dateSb.append(words[i]);
                dateSb.append(" ");
            } else {
                if (words[i].equals("/by")) {
                    isDate = true;
                } else {
                    taskSb.append(words[i]);
                    taskSb.append(" ");
                }
            }
        }
        String inputDate = dateSb.toString().strip();

        if (taskSb.isEmpty() && dateSb.isEmpty()) {
            throw new MiroException("Nothing to update!");
        } else if (!dateSb.isEmpty() && !Utils.isValidDate(inputDate)) {
            throw new MiroException("Please specify a date using \"/by ...\"");
        }

        return new String[]{taskSb.toString().strip(), inputDate};
    }

}
