package gui;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Stage styles.
 *
 * Each button opens a new Stage with a different StageStyle.
 * UNDECORATED and TRANSPARENT windows have no title bar and no X,
 * so every window gets its own "Close me" button.
 *
 * (setOnAction is explained in Part 3. For now, read it as
 *  "when this button is clicked, do this.")
 */
public class StageStylesDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        Button decorated = new Button("Open DECORATED");
        decorated.setOnAction(e -> openStage(StageStyle.DECORATED));

        Button undecorated = new Button("Open UNDECORATED");
        undecorated.setOnAction(e -> openStage(StageStyle.UNDECORATED));

        Button transparent = new Button("Open TRANSPARENT");
        transparent.setOnAction(e -> openStage(StageStyle.TRANSPARENT));

        Button utility = new Button("Open UTILITY");
        utility.setOnAction(e -> openStage(StageStyle.UTILITY));

        VBox mainPane = new VBox(10, decorated, undecorated, transparent, utility);
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setPadding(new Insets(20));

        mainStage.setTitle("Stage Styles");
        mainStage.setScene(new Scene(mainPane, 300, 220));
        mainStage.show();
    }

    private void openStage(StageStyle style) {
        Stage stage = new Stage(style);       // the style must be chosen BEFORE show()

        Label message = new Label("This stage is of style " + style);
        Button close = new Button("Close me");
        close.setOnAction(e -> stage.close());

        VBox pane = new VBox(15, message, close);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20));

        Scene scene = new Scene(pane, 320, 150);

        if (style == StageStyle.TRANSPARENT) {
            // For a see-through window, both the pane and the scene must be transparent.
            pane.setStyle("-fx-background-color: transparent;");
            scene.setFill(Color.TRANSPARENT);
        }

        stage.setTitle(style.toString());
        stage.setScene(scene);
        stage.show();
    }
}
