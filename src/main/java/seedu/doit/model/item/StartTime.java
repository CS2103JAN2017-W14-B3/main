package seedu.doit.model.item;

import java.time.LocalDateTime;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.parser.DateTimeParser;

/**
 * Represents a Item's start time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_STARTTIME_CONSTRAINTS =
        "Item Start Time should be 2 alphanumeric/period strings separated by '@'";
    public static final String STARTTIME_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given startTime.
     *
     * @throws IllegalValueException if given startTime string is invalid.
     */
    public StartTime(String startTime) throws IllegalValueException {
        assert startTime != null;
        String trimmedStartTime = startTime.trim();

        LocalDateTime date = DateTimeParser.parseDateTime(trimmedStartTime).orElseThrow(()
            -> new IllegalValueException("Invalid Date Format: " + trimmedStartTime));
        String dateInString = date.toString();

        if (!isValidStartTime(dateInString)) {
            throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
        }

        this.value = dateInString;
    }

    /**
     * Returns if a given string is a valid task start time.
     */
    public static boolean isValidStartTime(String test) {
        return test.matches(STARTTIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof StartTime
            && this.value.equals(((StartTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
