package miro.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import miro.exception.MiroException;

/**
 * The utils class for global methods.
 */
public class Utils {
    /**
     * Validates the date input by user.
     *
     * @param date The deadline of the task.
     */
    public static boolean isValidDate(String date) throws MiroException {
        try {
            if (date.isEmpty()) {
                throw new MiroException("Date cannot be empty.");
            }
            LocalDate inputDate = LocalDate.parse(date);
            if (!inputDate.isBefore(LocalDate.now())) {
                return true;
            } else {
                throw new MiroException("Date cannot be in the past.");
            }
        } catch (DateTimeParseException e) {
            throw new MiroException("Invalid date. Date must be in format YYYY-MM-DD.");
        }
    }

    /**
     * Validates the 'to' and 'from' dates input by user.
     *
     * @param fromDate The deadline of the task.
     * @param toDate The deadline of the task.
     */
    public static boolean isValidToFromDates(String fromDate, String toDate) throws MiroException {
        if (isValidDate(fromDate) && isValidDate(toDate)) {
            LocalDate inputFromDate = LocalDate.parse(fromDate);
            LocalDate inputToDate = LocalDate.parse(toDate);

            if (inputToDate.isBefore(inputFromDate)) {
                throw new MiroException("Start date cannot be after end date.");
            }
        }

        return true;
    }

    public static String[] getDeadlineParams(String[] words) throws MiroException {
        boolean isDate = false;
        StringBuilder taskSb = new StringBuilder();
        StringBuilder dateSb = new StringBuilder();

        for (int i = 1; i < words.length; i++) {
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

        if (!isDate || !Utils.isValidDate(inputDate)) {
            throw new MiroException("Please specify a date using \"/by ...\"");
        } else if (inputDate.isEmpty()) {
            throw new MiroException("Task description cannot be empty.");
        } else if (taskSb.isEmpty()) {
            throw new MiroException("Task description cannot be empty.");
        }

        return new String[]{taskSb.toString().strip(), inputDate};
    }


}
