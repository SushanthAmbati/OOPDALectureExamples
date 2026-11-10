package gui;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Other controls.
 *
 * Shows each control from the slides, then a "Show my choices" button
 * reads the current value out of every control. The controls are fields
 * so that the button's method can reach them.
 */
public class ControlsDemo extends Application {

    private ToggleGroup potatoGroup = new ToggleGroup();
    private CheckBox spamBox = new CheckBox("Send me spam");
    private TextField nameField = new TextField("I am textfield 1.");
    private TextArea notesArea = new TextArea("I am text area 1");
    private ChoiceBox<String> genreBox = new ChoiceBox<>();
    private ComboBox<String> showBox = new ComboBox<>();
    private ListView<String> cardList = new ListView<>();
    private Label summary = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        VBox mainPane = new VBox(10);
        mainPane.setPadding(new Insets(15));
        setupControls(mainPane);

        mainStage.setTitle("Other Controls");
        mainStage.setScene(new Scene(mainPane, 480, 620));
        mainStage.show();
    }

    private void setupControls(VBox mainPane) {
        // Radio buttons: the ToggleGroup allows only ONE to be selected
        RadioButton rButton1 = new RadioButton("1 potato");
        RadioButton rButton2 = new RadioButton("2 potato");
        RadioButton rButton3 = new RadioButton("3 potato");
        rButton1.setToggleGroup(potatoGroup);
        rButton2.setToggleGroup(potatoGroup);
        rButton3.setToggleGroup(potatoGroup);
        HBox radios = new HBox(15, rButton1, rButton2, rButton3);

        // Text area: several lines of text
        notesArea.setPrefRowCount(3);
        notesArea.setWrapText(true);

        // Choice box: a simple drop-down
        genreBox.getItems().addAll("EDM", "Rock", "Country", "Hip-Hop/Rap");
        genreBox.setValue("Rock");

        // Combo box: a drop-down that can show prompt text
        showBox.getItems().addAll("Haunting at Hill House", "60 Minutes",
                "Game of Thrones", "Black Mirror");
        showBox.setPromptText("Favorite TV Show?");

        // List view: shows all items; MULTIPLE lets the user pick several
        cardList.getItems().addAll("Visa", "MasterCard", "Discover", "AMEX");
        cardList.setOrientation(Orientation.HORIZONTAL);
        cardList.setMaxHeight(40);
        cardList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Button show = new Button("Show my choices");
        show.setOnAction(e -> showChoices());     // explained in Part 3

        summary.setWrapText(true);

        mainPane.getChildren().addAll(
                new Label("RadioButtons:"), radios,
                new Label("CheckBox:"), spamBox,
                new Label("TextField:"), nameField,
                new Label("TextArea:"), notesArea,
                new Label("ChoiceBox:"), genreBox,
                new Label("ComboBox:"), showBox,
                new Label("ListView (Ctrl/Cmd-click to pick several):"), cardList,
                show, summary);
    }

    /** Reads the current value out of every control. */
    private void showChoices() {
        RadioButton picked = (RadioButton) potatoGroup.getSelectedToggle();
        String potato = (picked == null) ? "none" : picked.getText();

        summary.setText(
                "Potato: " + potato
                + "\nSpam: " + spamBox.isSelected()
                + "\nTextField: " + nameField.getText()
                + "\nTextArea: " + notesArea.getText()
                + "\nGenre: " + genreBox.getValue()
                + "\nShow: " + showBox.getValue()
                + "\nCards: " + cardList.getSelectionModel().getSelectedItems());
    }
}
