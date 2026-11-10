package gui;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Owner windows, modal vs. non-modal, and showAndWait().
 *
 * Try this in class:
 *   1. Open the NON-modal window, then type in the main window's text field. It works.
 *   2. Open the MODAL window, then try to type in the main window. It is blocked
 *      until you answer the dialog.
 *   3. Open a few non-modal windows, then close the main window.
 *      They all close too, because the main window is their owner.
 */
public class ModalityDemo extends Application {

    private Label status = new Label("Nothing opened yet.");
    private String answer = "(no answer)";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        TextField typingTest = new TextField();
        typingTest.setPromptText("Try typing here while a window is open");

        Button nonModal = new Button("Open NON-modal window");
        nonModal.setOnAction(e -> openNonModal(mainStage));

        Button modal = new Button("Open MODAL dialog");
        modal.setOnAction(e -> openModal(mainStage));

        VBox mainPane = new VBox(15, typingTest, nonModal, modal, status);
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setPadding(new Insets(20));

        mainStage.setTitle("Main Window (the owner)");
        mainStage.setScene(new Scene(mainPane, 380, 250));
        mainStage.show();
    }

    private void openNonModal(Stage owner) {
        Stage note = new Stage();
        note.initOwner(owner);                    // "owner" = parent window
        note.initModality(Modality.NONE);         // does not block anything

        Label text = new Label("I'm non-modal.\nYou can still use the main window.");
        VBox pane = new VBox(text);
        pane.setPadding(new Insets(20));

        note.setTitle("Non-modal");
        note.setScene(new Scene(pane, 260, 100));
        note.show();                              // show() returns immediately

        status.setText("Non-modal window opened. Main window still works.");
    }

    private void openModal(Stage owner) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);   // blocks the rest of the app
        dialog.setResizable(false);

        Label question = new Label("Save changes before quitting?");
        Button yes = new Button("Yes");
        Button no = new Button("No");
        yes.setOnAction(e -> {
            answer = "Yes";
            dialog.close();
        });
        no.setOnAction(e -> {
            answer = "No";
            dialog.close();
        });

        HBox buttons = new HBox(10, yes, no);
        buttons.setAlignment(Pos.CENTER);
        VBox pane = new VBox(15, question, buttons);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20));

        dialog.setTitle("Modal");
        dialog.setScene(new Scene(pane, 260, 120));

        dialog.showAndWait();     // this method PAUSES here until the dialog is closed...
        status.setText("You answered: " + answer);   // ...so this line runs afterwards
    }
}
