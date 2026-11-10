package gui;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Setting up controls and setting the stage.
 *
 * Builds the pink "HELLO!" window:
 *   Stage  -> the window
 *   Scene  -> what is shown in the window
 *   BorderPane (root node) -> five spots: Top, Left, Center, Right, Bottom
 *   Label and Buttons -> the controls placed on those spots
 *
 * The buttons do nothing yet. That is Part 3 (see EventDemo).
 */
public class HelloWorld extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        BorderPane mainPane = new BorderPane();               // make layout to hold controls
        mainPane.setStyle("-fx-background-color: pink;");     // make background pink
        setupControls(mainPane);                              // create and place controls
        Scene scene = new Scene(mainPane);                    // set up the main scene
        setStage(mainStage, scene);                           // finalize and show the stage
    }

    private void setupControls(BorderPane mainPane) {
        // 1. Create the controls
        Label lbl = new Label("Maybe something will happen here?");
        lbl.setPadding(new Insets(20));

        Button btn1 = new Button();
        btn1.setText("Say 'Hello World'");

        Button btn2 = new Button();
        btn2.setText("Say 'Goodbye World'");

        // 2. Place them on the BorderPane's spots
        BorderPane.setAlignment(lbl, Pos.CENTER);
        mainPane.setTop(lbl);
        mainPane.setLeft(btn1);
        mainPane.setRight(btn2);
        mainPane.setBottom(new Label(" "));   // cheap way to get some space

        // Try it: nothing is in the Center. Put a new Label there and run again.
        // mainPane.setCenter(new Label("I'm in the center!"));
    }

    private void setStage(Stage mainStage, Scene scene) {
        mainStage.setWidth(400);
        mainStage.setTitle("HELLO!");
        mainStage.setScene(scene);
        mainStage.show();                     // nothing is visible until show() is called
    }
}
