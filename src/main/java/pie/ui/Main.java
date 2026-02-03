package pie.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pie.Pie;

/**
 * A GUI for Pie using FXML.
 */
public class Main extends Application {

    private Pie pie = new Pie();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Pie Chatbot");
            stage.setMinHeight(250);
            stage.setMinWidth(420);
            fxmlLoader.<MainWindow>getController().setPie(pie); // inject the Pie instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
