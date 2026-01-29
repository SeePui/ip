package pie.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Deadline task.
 *
 * <p>
 * An Deadline has a description and end date-time.
 * </p>
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Instantiates a new Deadline.
     *
     * @param description Description of the deadline
     * @param by          End date and time of the deadline
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a string representation of the deadline for display.
     *
     * @return String in the format "[D][status] description (by: ...)"
     */
    @Override
    public String toString() {
        DateTimeFormatter outputFormat =
                DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

        return "[D]" + super.toString() + " (by: " + by.format(outputFormat) + ")";
    }

    /**
     * Returns a string representation of the deadline for saving to storage.
     *
     * <p>
     * Format: "D | isDone | description | by"
     * </p>
     *
     * @return Deadline task as a string suitable for saving
     */
    @Override
    public String toSaveString() {
        return "D | " + (getIsDone() ? "1" : "0") + " | "
                + getDescription() + " | " + by;
    }

    /**
     * Checks whether the deadline occurs on the specified date.
     *
     * <p>
     * An deadline is considered to occur on a date if its deadline date
     * matches the given date.
     * </p>
     *
     * @param date The date to check
     * @return true if the deadline occurs on the given date, false otherwise
     */
    @Override
    public boolean occursOn(LocalDate date) {
        return by.toLocalDate().equals(date);
    }
}