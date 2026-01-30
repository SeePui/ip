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

    @Override
    public String toString() {
        DateTimeFormatter outputFormat =
                DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

        return "[D]" + super.toString() + " (by: " + by.format(outputFormat) + ")";
    }

    @Override
    public String toSaveString() {
        return "D | " + (getIsDone() ? "1" : "0") + " | "
                + getDescription() + " | " + by;
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return by.toLocalDate().equals(date);
    }
}
