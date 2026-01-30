package pie.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task.
 *
 * <p>
 * An Event has a description and start and end date-time.
 * </p>
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Instantiates a new Event.
     *
     * @param description Description of the event
     * @param from        Start date and time of the event
     * @param to          End date and time of the event
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the event for display.
     *
     * @return String in the format "[E][status] description (from: ... to: ...)"
     */
    @Override
    public String toString() {
        DateTimeFormatter outputFormat =
                DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

        return "[E]" + super.toString()
                + " (from: " + from.format(outputFormat)
                + " to: " + to.format(outputFormat) + ")";
    }

    /**
     * Returns a string representation of the event for saving to storage.
     *
     * <p>
     * Format: "E | isDone | description | from | to"
     * </p>
     *
     * @return Event task as a string suitable for saving
     */
    @Override
    public String toSaveString() {
        return "E | " + (getIsDone() ? "1" : "0") + " | "
                + getDescription() + " | " + from + " | " + to;
    }

    /**
     * Checks whether the event occurs on the specified date.
     *
     * <p>
     * An event is considered to occur on a date if its start date
     * matches the given date.
     * </p>
     *
     * @param date The date to check
     * @return true if the event occurs on the given date, false otherwise
     */
    @Override
    public boolean occursOn(LocalDate date) {
        return from.toLocalDate().equals(date);
    }
}
