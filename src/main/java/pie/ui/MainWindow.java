package pie.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import pie.BotMessage;
import pie.Pie;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Pie pie;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image pieImage = new Image(this.getClass().getResourceAsStream("/images/chatbotPie.png"));

    /**
     * Initializes the main window controller after the FXML has been loaded.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        displayWelcomeMessage();
    }

    /**
     * Displays a welcome message for the user.
     */
    public void displayWelcomeMessage() {
        dialogContainer.getChildren().add(DialogBox.getPieDialog(BotMessage.START.get(),
                pieImage, false));
    }

    /**
     * Injects the Pie instance.
     */
    public void setPie(Pie p) {
        pie = p;
    }

    /**
     * Creates two dialog boxes, one user input and the other
     * containing Pie's reply and then appends them to the dialog
     * container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        pie.run(input);
        String response = pie.getUi().getMessage();
        boolean isErrorMessage = pie.getUi().isErrorMessage();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getPieDialog(response, pieImage, isErrorMessage)
        );
        userInput.clear();
    }
}
