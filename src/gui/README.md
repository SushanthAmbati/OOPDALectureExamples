# GUIs with JavaFX

**Big idea:** A JavaFX program is a **`Stage`** (the window) showing a **`Scene`** (its contents), which holds a tree of **nodes**: layout panes such as `BorderPane` and `VBox`, with controls such as `Button` and `Label` inside them. The program doesn't run top to bottom. It sets up the window and then **waits for events**, such as clicks, key presses and mouse moves. Each event runs a **handler**, which is usually a lambda.

> JavaFX doesn't come with the JDK. **Do the [setup](#setup-javafx) first**, or none of these files will compile.

## Files

Work through the demos in this order:

| File | Role |
|------|------|
| [`HelloWorld.java`](HelloWorld.java) | ▶ The pink "HELLO!" window: `Stage` → `Scene` → `BorderPane` → `Label` and `Button`s. The buttons don't do anything yet. |
| [`StageStylesDemo.java`](StageStylesDemo.java) | ▶ Opens a new `Stage` in each `StageStyle`: `DECORATED`, `UNDECORATED`, `TRANSPARENT`, `UTILITY`. |
| [`ModalityDemo.java`](ModalityDemo.java) | ▶ Owner windows, modal vs. non-modal, and `show()` vs. `showAndWait()`. |
| [`ControlsDemo.java`](ControlsDemo.java) | ▶ `RadioButton`, `CheckBox`, `TextField`, `TextArea`, `ChoiceBox`, `ComboBox` and `ListView`. A button reads every control's current value. |
| [`EventDemo.java`](EventDemo.java) | ▶ Three ways to handle a click, then `KeyEvent`, `MouseEvent` and `WindowEvent`. |
| [`AdvancedControlsDemo.java`](AdvancedControlsDemo.java) | ▶ `Slider` with a value listener, `TreeView` (single- and multi-select) and `TableView`. |
| [`Song.java`](Song.java) | A plain data class: one row of the `TableView`. |

## Setup: JavaFX

You need the **JavaFX SDK** (version 21 or newer), plus two pieces of setup: one so the code **compiles** and one so it **runs**.

1. **Download** the SDK for your operating system from [gluonhq.com/products/javafx](https://gluonhq.com/products/javafx/). On a Mac, pick **aarch64** for Apple silicon (M1 or later) or **x64** for Intel. Unzip it somewhere permanent, for example your home folder. Inside, you'll find a `lib` folder full of `.jar` files. Its full path is your **JavaFX lib path**:
   - macOS: `/Users/you/javafx-sdk-21/lib`
   - Windows: `C:\Users\you\javafx-sdk-21\lib` (inside JSON, write every `\` twice: `C:\\Users\\...`)

2. **Compile:** in VS Code's **Explorer**, open **Java Projects → Referenced Libraries → +** and select every `.jar` in your JavaFX lib path. The red errors in `src/gui` should disappear.

3. **Run:** create `.vscode/launch.json` (the repository ignores this file, so it stays on your machine) with this content, replacing the path with your JavaFX lib path:

   ```json
   {
       "version": "0.2.0",
       "configurations": [
           {
               "type": "java",
               "name": "Current File (JavaFX)",
               "request": "launch",
               "mainClass": "${file}",
               "vmArgs": "--module-path /Users/you/javafx-sdk-21/lib --add-modules javafx.controls"
           }
       ]
   }
   ```

   Then open a GUI file, go to **Run and Debug** (Ctrl/Cmd + Shift + D), choose **Current File (JavaFX)** and press **F5**.

**From a terminal instead**, run these from the repository root, with your own path in `FX`:

```sh
FX=/Users/you/javafx-sdk-21/lib
javac --module-path "$FX" --add-modules javafx.controls -d bin src/gui/*.java
java  --module-path "$FX" --add-modules javafx.controls -cp bin gui.HelloWorld
```

## Key concepts

### The pieces of a window

| Piece | What it is | In `HelloWorld` |
|-------|------------|-----------------|
| `Application` | The class every JavaFX program extends. `main` calls `launch(args)`, and JavaFX then calls **your** `start(Stage)`. | `HelloWorld extends Application` |
| `Stage` | The window itself: title, size, style. Nothing appears until you call `show()`. | `mainStage` |
| `Scene` | Everything inside the window. It's given one **root** node. | `new Scene(mainPane)` |
| Layout pane | A node that arranges other nodes | `BorderPane` |
| Control | Something the user sees or uses | `Label`, `Button` |

### Layout panes used in these demos

| Pane | Arranges its children… | Used in |
|------|-------------------------|---------|
| `BorderPane` | in five spots: `Top`, `Left`, `Center`, `Right`, `Bottom` | `HelloWorld`, `EventDemo`, `AdvancedControlsDemo` |
| `VBox` | in a vertical column | almost every demo |
| `HBox` | in a horizontal row | `ControlsDemo` (radio buttons), `ModalityDemo` (Yes/No) |

### Stages: style and modality (`StageStylesDemo`, `ModalityDemo`)

| Setting | Options | Must be set… |
|---------|---------|--------------|
| `StageStyle` | `DECORATED` (normal), `UNDECORATED` (no title bar), `TRANSPARENT` (no title bar or background), `UTILITY` (small title bar) | **before** `show()`, e.g. `new Stage(style)` |
| `initOwner(stage)` | The parent window. Closing the owner closes its children. | before `show()` |
| `initModality(...)` | `NONE`: the other windows still work. `APPLICATION_MODAL`: every other window is blocked until this one closes. | before `show()` |

`show()` returns **immediately**. `showAndWait()` **pauses** the method until the window closes, which is how `ModalityDemo` reads the dialog's answer on the very next line.

### Events (`EventDemo`)

An event handler is an object that implements **`EventHandler<T>`**, a functional interface with one method, `void handle(T event)`. That's why a lambda works, just like the lambdas in [`../lambdas`](../lambdas/README.md).

| Way | Code | When to use it |
|-----|------|----------------|
| Anonymous class | `btn.setOnAction(new EventHandler<ActionEvent>() { public void handle(ActionEvent e) { … } });` | To see what a lambda is short for (commented out in `EventDemo`) |
| Lambda | `btn.setOnAction(e -> message.setText("Hello World!"));` | One short action |
| Lambda that calls a method | `btn.setOnAction(e -> resetMessage());` | Anything longer than a line or two |

| Event | Registered with | Fires when… |
|-------|-----------------|-------------|
| `ActionEvent` | `button.setOnAction(...)` | a button is clicked |
| `KeyEvent` | `scene.setOnKeyPressed(...)` | a key is pressed while the window has focus |
| `MouseEvent` | `pane.setOnMouseMoved(...)` | the mouse moves over the pane |
| `WindowEvent` | `stage.setOnCloseRequest(...)` | the user tries to close the window |

The `e` parameter is the **event object**. It tells you what happened: `e.getCode()` gives the key, `e.getX()` the mouse position, and `e.getTarget()` the clicked node.

### Reading values from controls (`ControlsDemo`, `AdvancedControlsDemo`)

| Control | How to read its value |
|---------|-----------------------|
| `RadioButton` (in a `ToggleGroup`) | `group.getSelectedToggle()`, which is `null` if nothing is picked |
| `CheckBox` | `isSelected()` |
| `TextField`, `TextArea` | `getText()` |
| `ChoiceBox`, `ComboBox` | `getValue()` |
| `ListView`, `TreeView` | `getSelectionModel().getSelectedItems()` |
| `Slider` | `getValue()`, or react to every change with `valueProperty().addListener(...)` |
| `TableView<Song>` | `getItems()` holds the rows. Each `TableColumn`'s `setCellValueFactory` lambda picks the value to show from a `Song`. |

## Gotchas

- **Controls that handlers change must be fields.** `EventDemo`'s `message` label is a field so that every handler can reach it. A local variable from `start` would only be visible to lambdas written inside `start`, and only if it's never reassigned.
- **One node, one parent.** A node can only be in one place in the scene at a time. That's why `AdvancedControlsDemo` calls `createFamilyTree()` once for each `TreeView`, instead of sharing one tree.
- **`TRANSPARENT` needs two steps.** The pane's background *and* the scene's fill must both be transparent. `UNDECORATED` and `TRANSPARENT` windows have no ✕, so each one gets a "Close me" button.
- **"Module javafx.controls not found"** means the run configuration is missing `--module-path` or points to the wrong folder. Check step 3 of the setup.
- **Before JavaFX is set up,** VS Code marks every file in `src/gui` with errors. The other topics still run: if VS Code says the build failed, choose **Proceed**.

## Try it

1. In `HelloWorld`, uncomment the `setCenter` line. Then swap `btn1` and `btn2` between `Left` and `Right`.
2. In `ModalityDemo`, open three non-modal windows, then close the main window. What happens to them? Change `initModality` to `Modality.WINDOW_MODAL`. What's different from `APPLICATION_MODAL`?
3. In `EventDemo`, comment out `btn1`'s lambda and uncomment the anonymous class below it. Does the program behave any differently?
4. In `ControlsDemo`, remove `setToggleGroup` from one radio button. How many can you select now?
5. Add an `Album` column to `AdvancedControlsDemo`'s table. Add a field and getter to `Song`, then a new `TableColumn`.
6. In `StageStylesDemo`, call `stage.initStyle(StageStyle.UTILITY)` **after** `stage.show()`. What happens, and why?
