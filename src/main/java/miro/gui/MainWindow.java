package miro.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import miro.main.Miro;


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
    private Button sendButton;

    private Miro miro;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image miroImage = new Image(this.getClass().getResourceAsStream("/images/Miro.jpg"));

    /**
     * Initializes the dialog container.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Miro instance */
    public void setMiro(Miro d) {
        miro = d;
    }

    /** Creates a dialog gox that contains the Miro's initial greeting message */
    public void greetUser() {
        // initial greeting
        String response = miro.greet();
        dialogContainer.getChildren().addAll(
                DialogBox.getMiroDialog(response, miroImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Miro's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response;
        DialogBox dialogBox;
        try {
            response = miro.getResponse(input);
            dialogBox = DialogBox.getMiroDialog(response, miroImage);
        } catch (Exception e) {
            response = e.getMessage();
            dialogBox = DialogBox.getErrorDialog(response, miroImage);
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                dialogBox
        );
        userInput.clear();
    }

}
