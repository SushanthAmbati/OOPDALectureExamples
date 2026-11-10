package gui;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Advanced controls -- Slider, TreeView (single and multi-select), TableView.
 * Uses the Song class for the table rows.
 */
public class AdvancedControlsDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        BorderPane mainPane = new BorderPane();
        mainPane.setPadding(new Insets(10));

        mainPane.setTop(createSlider());
        mainPane.setCenter(createTrees());
        mainPane.setBottom(createTable());

        mainStage.setTitle("Advanced Controls");
        mainStage.setScene(new Scene(mainPane, 600, 650));
        mainStage.show();
    }

    // ---------------------------------------------------------------- Slider
    private VBox createSlider() {
        Slider slider = new Slider(-40, 110, 63.7);    // min, max, starting value
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(30);

        Label reading = new Label("63.7");
        // Run this code every time the slider's value changes
        slider.valueProperty().addListener((observable, oldValue, newValue) ->
                reading.setText(String.format("%.1f", newValue.doubleValue())));

        VBox box = new VBox(5, new Label("What is the current temperature?"), slider, reading);
        box.setPadding(new Insets(0, 0, 10, 0));
        return box;
    }

    // ------------------------------------------------------------- TreeViews
    private HBox createTrees() {
        TreeView<String> singleTree = new TreeView<>(createFamilyTree());
        // Single selection is the default

        TreeView<String> multiTree = new TreeView<>(createFamilyTree());
        multiTree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        VBox left = new VBox(5, new Label("TreeView (single select)"), singleTree);
        VBox right = new VBox(5, new Label("TreeView (multi-select)"), multiTree);
        return new HBox(10, left, right);
    }

    /** Builds a new tree each time (one TreeItem cannot be in two TreeViews). */
    private TreeItem<String> createFamilyTree() {
        TreeItem<String> root = new TreeItem<>("Pick Your Favorite");
        root.setExpanded(true);
        addHouse(root, "Lannister", "Tywin", "Cersei", "Jamie", "Tyrion");
        addHouse(root, "Targaryen", "Aegon", "Aemon", "Daenerys");
        addHouse(root, "Baratheon", "Robert", "Stannis", "Renly");
        return root;
    }

    private void addHouse(TreeItem<String> root, String house, String... members) {
        TreeItem<String> houseItem = new TreeItem<>(house);
        for (String member : members) {
            houseItem.getChildren().add(new TreeItem<>(member));
        }
        root.getChildren().add(houseItem);
    }

    // ------------------------------------------------------------- TableView
    private TableView<Song> createTable() {
        TableView<Song> table = new TableView<>();

        TableColumn<Song, String> titleCol = new TableColumn<>("Song Title");
        titleCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));

        TableColumn<Song, String> artistCol = new TableColumn<>("Artist");
        artistCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getArtist()));

        TableColumn<Song, String> releasedCol = new TableColumn<>("Released");
        releasedCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getReleased()));

        TableColumn<Song, String> movieCol = new TableColumn<>("Featured Movie");
        movieCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMovie()));

        table.getColumns().add(titleCol);
        table.getColumns().add(artistCol);
        table.getColumns().add(releasedCol);
        table.getColumns().add(movieCol);

        table.getItems().add(new Song("Come As You Are", "Nirvana", "1991", "Captain Marvel"));
        table.getItems().add(new Song("Man on the Moon", "R.E.M.", "1992", "Captain Marvel"));
        table.getItems().add(new Song("It's No Good", "Depeche Mode", "1997", "Aquaman"));
        table.getItems().add(new Song("Comfortably Numb", "Pink Floyd", "1979", "The Departed"));
        table.getItems().add(new Song("Don't Stop Me Now", "Queen", "1978", "Bohemian Rhapsody"));

        table.setPrefHeight(180);
        return table;
    }
}
