package miro.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import miro.exception.MiroException;

public class Utils {
    public static boolean isValidDate(String date) throws MiroException {
        try {
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
}
