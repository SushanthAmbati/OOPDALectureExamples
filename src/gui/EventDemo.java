package gui;
import javafx.application.Application;
import javafx.event.ActionEvent;     // these three are used by the
import javafx.event.EventHandler;    // commented-out anonymous classes
import javafx.event.EventTarget;     // in setupControls
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Event handling.
 *
 * Three ways to handle a button click, then three other kinds of event:
 *   - btn1 uses a lambda. The commented-out block below it is the same handler
 *     written the long way, as an anonymous class implementing EventHandler<ActionEvent>
 *   - btn2 uses a lambda that asks the event which button was clicked
 *     (lambdas work because EventHandler has only one method)
 *   - btn3 uses a lambda that calls a private method (keeps handlers short)
 *   - KeyEvent:    press any key while the window has focus
 *   - MouseEvent:  move the mouse over the window
 *   - WindowEvent: close the window and watch the console
 *
 * The label is a FIELD, not a local variable, so the handlers can change it.
 */
public class EventDemo extends Application {

    private Label message = new Label("Maybe something will happen here?");
    private Label mouseInfo = new Label("Move the mouse over the window");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        BorderPane mainPane = new BorderPane();
        mainPane.setStyle("-fx-background-color: pink;");
        setupControls(mainPane);

        Scene scene = new Scene(mainPane);

        // KeyEvent: fires when a key is pressed while this window has focus
        scene.setOnKeyPressed(e -> message.setText("You pressed: " + e.getCode()));

        // MouseEvent: fires every time the mouse moves over the pane
        mainPane.setOnMouseMoved(e ->
                mouseInfo.setText("Mouse at x=" + (int) e.getX() + ", y=" + (int) e.getY()));

        // WindowEvent: fires when the user tries to close the window
        mainStage.setOnCloseRequest(e -> System.out.println("The window is closing. Goodbye!"));

        mainStage.setWidth(450);
        mainStage.setTitle("HELLO!");
        mainStage.setScene(scene);
        mainStage.show();
    }

    private void setupControls(BorderPane mainPane) {
        message.setPadding(new Insets(20));
        BorderPane.setAlignment(message, Pos.CENTER);

        // Way 1: a lambda. The anonymous class below is the "long form" of the same thing.
        Button btn1 = new Button();
        btn1.setText("Say 'Hello World'");
        btn1.setOnAction(e -> message.setText("Hello World!"));
        /*btn1.setText("Say 'Hello World'");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                message.setText("Hello World!");
                System.out.println("Hello World!");
            }
        });*/

        // Way 2: lambda (the "short form"); asks the event which button was clicked
        Button btn2 = new Button();
        btn2.setText("Say 'Goodbye World'");
        btn2.setOnAction(e -> message.setText(((Button) e.getTarget()).getText()));

        /*btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                EventTarget target = e.getTarget();
                Button clicked = (Button) target;
                String text = clicked.getText();
                message.setText(text);
            }
        });*/

        // Way 3: lambda that calls a method
        Button btn3 = new Button("Reset");
        btn3.setOnAction(e -> resetMessage());

        VBox center = new VBox(btn3);
        center.setAlignment(Pos.CENTER);

        mainPane.setTop(message);
        mainPane.setLeft(btn1);
        mainPane.setRight(btn2);
        mainPane.setCenter(center);
        mainPane.setBottom(mouseInfo);
        BorderPane.setMargin(mouseInfo, new Insets(10));
    }

    private void resetMessage() {
        message.setText("Maybe something will happen here?");
    }
}
