package pie.ui;

/**
 * Represents the user interface for the Pie application.
 *
 */
public class Ui {
    private String message;

    /**
     * Returns the latest message stored in the UI.
     *
     * @return The last message set via {@link #setMessage(String)}
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets a message to be displayed to the user.
     *
     * <p>
     * Commands or other components can call this method to update the message
     * instead of printing it directly. This allows the UI layer (such as JavaFX)
     * to handle the actual display.
     * </p>
     *
     * @param message The message to store
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
