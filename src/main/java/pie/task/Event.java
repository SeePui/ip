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
     * @param description Description of the event.
     * @param from        Start date and time of the event.
     * @param to          End date and time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormat =
                DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

        return "[E]" + super.toString()
                + " (from: " + from.format(outputFormat)
                + " to: " + to.format(outputFormat) + ")";
    }

    @Override
    public String toSaveString() {
        return "E | " + (getIsDone() ? "1" : "0") + " | "
                + getDescription() + " | " + from + " | " + to;
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return from.toLocalDate().equals(date);
    }
}
