package eu.ortlepp.tasklist.gui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

import eu.ortlepp.tasklist.SimpleTaskList;
import eu.ortlepp.tasklist.model.Task;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Controller for the new task and edit task dialog window. Handles all actions of the dialog window.
 *
 * @author Thorsten Ortlepp
 */
public class NewEditDialogController {


    /** Layout container for buttons. */
    @FXML
    private GridPane gpButtons;


    /** Button to continue / add another task. */
    @FXML
    private Button btnContinue;


    /** Button to add tasks to the list. */
    @FXML
    private Button btnDone;


    /** Button to save changes on the edited task. */
    @FXML
    private Button btnSave;


    /** Button to cancel an edit / adding action and dismiss changes / new tasks. */
    @FXML
    private Button btnHide;


    /** Combo box to select the priority. */
    @FXML
    private ComboBox<String> cbxPriority;


    /** Picker to select the creation date. */
    @FXML
    private DatePicker dpCreation;


    /** Picker to select the due date. */
    @FXML
    private DatePicker dpDue;


    /** Area to insert / edit the description text. */
    @FXML
    private TextArea txaDescription;


    /** List of all contexts of the task. */
    @FXML
    private ListView<String> lvContext;


    /** List of all projects of the task. */
    @FXML
    private ListView<String> lvProject;


    /** The stage of the dialog. */
    private Stage stage;


    /** Translated captions and tooltips for the GUI. */
    private ResourceBundle translations;


    /** List of all existing contexts. */
    private List<String> contexts;


    /** List of all existing projects. */
    private List<String> projects;


    /** The save state of the data. Values: true = save / done was clicked, false = cancel was clicked. */
    private boolean saved;

    /** List of all new tasks. */
    private List<Task> newTasks;



    /**
     * Initialize the dialog and its components; load the translations.
     */
    @FXML
    private void initialize() {
        contexts = new ArrayList<String>();
        projects = new ArrayList<String>();
        newTasks = new ArrayList<Task>();

        try {
            translations = ResourceBundle.getBundle(SimpleTaskList.TRANSLATION);
        } catch (MissingResourceException ex) {
            throw new RuntimeException("Translation is not available", ex);
        }

        cbxPriority.setItems(FXCollections.observableArrayList(translations.getString("choice.priority.no"), translations.getString("choice.priority.done"), "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"));
    }



    /**
     * Handle a click on the "cancel" button: hide / close the dialog window.
     */
    @FXML
    private void handleBtnHideClick() {
        stage.hide();
    }



    /**
     * Handle a click on the "save" button: set saved and hide / close the dialog window.
     */
    @FXML
    private void handleBtnSaveClick() {
        saved = true;
        stage.hide();
    }



    /**
     * Handle a click on the "continue" button: save the entered data and clear the input components.
     */
    @FXML
    private void handleBtnContinueClick() {
        newTasks.add(createTask());
        resetComponents();
    }



    /**
     * Handle a click on the "done" button: save the entered data, set saved and hide / close the dialog window.
     */
    @FXML
    private void handleBtnDoneClick() {
        newTasks.add(createTask());
        saved = true;
        stage.hide();
    }



    /**
     * Handle a click on the "select context" button: open a selection dialog and add the selected context to the list.
     */
    @FXML
    private void handleBtnSelectContext() {
        ChoiceDialog<String> choice = new ChoiceDialog<String>(contexts.get(0), contexts);
        choice.setTitle(translations.getString("dialog.context.select.title"));
        choice.setHeaderText(translations.getString("dialog.context.select.header"));
        choice.setContentText(translations.getString("dialog.context.select.content"));
        Optional<String> text = choice.showAndWait();
        if (text.isPresent()){
            lvContext.getItems().add(text.get());
        }
    }



    /**
     * Handle a click on the "add context" button: open an input dialog and add the inserted context to the list.
     */
    @FXML
    private void handleBtnAddContext() {
        TextInputDialog input = new TextInputDialog();
        input.setTitle(translations.getString("dialog.context.new.title"));
        input.setHeaderText(translations.getString("dialog.context.new.header"));
        input.setContentText(translations.getString("dialog.context.new.content"));
        Optional<String> text = input.showAndWait();
        if (text.isPresent()){
            lvContext.getItems().add(text.get());
        }
    }



    /**
     * Handle a click on the "remove context" button: remove the selected context from the list.
     */
    @FXML
    private void handleBtnRemoveContext() {
        if (lvContext.getSelectionModel().getSelectedIndex() != -1) {
            lvContext.getItems().remove(lvContext.getSelectionModel().getSelectedIndex());
        }
    }



    /**
     * Handle a click on the "select project" button: open a selection dialog and add the selected project to the list.
     */
    @FXML
    private void handleBtnSelectProject() {
        ChoiceDialog<String> choice = new ChoiceDialog<String>(projects.get(0), projects);
        choice.setTitle(translations.getString("dialog.project.select.title"));
        choice.setHeaderText(translations.getString("dialog.project.select.header"));
        choice.setContentText(translations.getString("dialog.project.select.content"));
        Optional<String> text = choice.showAndWait();
        if (text.isPresent()){
            lvProject.getItems().add(text.get());
        }
    }



    /**
     * Handle a click on the "add project" button: open an input dialog and add the inserted project to the list.
     */
    @FXML
    private void handleBtnAddProject() {
        TextInputDialog input = new TextInputDialog();
        input.setTitle(translations.getString("dialog.project.new.title"));
        input.setHeaderText(translations.getString("dialog.project.new.header"));
        input.setContentText(translations.getString("dialog.project.new.content"));
        Optional<String> text = input.showAndWait();
        if (text.isPresent()){
            lvProject.getItems().add(text.get());
        }
    }



    /**
     * Handle a click on the "remove project" button: remove the selected project from the list.
     */
    @FXML
    private void handleBtnRemoveProject() {
        if (lvProject.getSelectionModel().getSelectedIndex() != -1) {
            lvProject.getItems().remove(lvProject.getSelectionModel().getSelectedIndex());
        }
    }



    /**
     * Set dialog to the "new task" mode.
     *
     * @param contexts All existing contexts
     * @param projects All existing projects
     */
    public void setNewDialog(List<String> contexts, List<String> projects) {
        gpButtons.getChildren().removeAll(btnContinue, btnDone, btnSave);
        gpButtons.add(btnDone, 0, 0);
        gpButtons.add(btnContinue, 1, 0);

        saved = false;
        newTasks.clear();

        initChoiceList(contexts, this.contexts);
        initChoiceList(projects, this.projects);
        resetComponents();
    }



    /**
     * Set dialog to the "edit task" mode. Show the data of the currently selected task in the dialog window.
     *
     * @param task The data of the currently selected task
     * @param contexts All existing contexts
     * @param projects All existing projects
     */
    public void setEditDialog(Task task, List<String> contexts, List<String> projects) {
        gpButtons.getChildren().removeAll(btnContinue, btnDone, btnSave);
        gpButtons.add(btnSave, 0, 0);

        saved = false;

        initChoiceList(contexts, this.contexts);
        initChoiceList(projects, this.projects);

        /* Select priority */
        int prioIndex = 0;
        if (task.getPriority() != null && !task.getPriority().isEmpty()) {
            if (task.getPriority().equals("x")) {
                prioIndex = 1;
            } else {
                prioIndex = task.getPriority().charAt(0) - 'A' + 2;
            }
        }

        /* Set task data in dialog window */
        cbxPriority.getSelectionModel().clearAndSelect(prioIndex);
        initDatePicker(dpCreation, task.getCreation());
        initDatePicker(dpDue, task.getDue());
        txaDescription.setText(task.getDescription());
        lvContext.setItems(FXCollections.observableArrayList(task.getContext()));
        lvProject.setItems(FXCollections.observableArrayList(task.getProject()));
    }



    /**
     * Initialize a date picker with a date. If the date has the minimum value no date is set.
     *
     * @param picker The date picker component to initialize
     * @param value The date to be set in the picker
     */
    private void initDatePicker(DatePicker picker, LocalDate value) {
        if (value.equals(LocalDate.MIN)) {
            picker.setValue(null);
        } else {
            picker.setValue(value);
        }
    }



    /**
     * Initialize a choice list with items. The first two items of the source list (all / no items) are omitted.
     *
     * @param source The source list with all items
     * @param target The list to be initialized
     */
    private void initChoiceList(List<String> source, List<String> target) {
        target.clear();
        for (int i = 2; i < source.size(); i++) {
            target.add(source.get(i));
        }
    }



    /**
     * Setter for the stage of the dialog window.
     *
     * @param stage The stage of the dialog window.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }



    /**
     * Getter for the save state.
     *
     * @return The save state; true = save, false = canceled
     */
    public boolean isSaved() {
        return saved;
    }



    /**
     * Getter for the edited task data.
     *
     * @return The edited data of the task
     */
    public Task getEditedTask() {
        return createTask();
    }



    /**
     * Getter for all new / added tasks.
     *
     * @return The new / added tasks in a list
     */
    public List<Task> getNewTasks() {
        return newTasks;
    }



    /**
     * Create a task object with data from the dialog window.
     *
     * @return The created task object
     */
    private Task createTask() {
        /* Task object with default values */
        Task task = new Task();

        /* Set status */
        if (cbxPriority.getSelectionModel().getSelectedIndex() == 1) {
            task.setPriority("x");
            task.setDone(true);
        } else if (cbxPriority.getSelectionModel().getSelectedIndex() > 1) {
            task.setPriority(cbxPriority.getSelectionModel().getSelectedItem());
        }

        /* Set dates if a date was chosen */
        if (dpCreation.getValue() != null) {
            task.setCreation(dpCreation.getValue());
        }
        if (dpDue.getValue() != null) {
            task.setDue(dpDue.getValue());
        }

        /* Set lists */
        for (String item : lvContext.getItems()) {
            task.addToContext(item);
        }
        for (String item : lvProject.getItems()) {
            task.addToProject(item);
        }

        /* Set text */
        task.setDescription(txaDescription.getText());

        return task;
    }



    /**
     * (Re)Initialize GUI components with empty / default values.
     */
    private void resetComponents() {
        cbxPriority.getSelectionModel().clearAndSelect(0);
        dpCreation.setValue(LocalDate.now());
        dpDue.setValue(null);
        txaDescription.setText("");
        lvContext.getItems().clear();
        lvProject.getItems().clear();
    }

}