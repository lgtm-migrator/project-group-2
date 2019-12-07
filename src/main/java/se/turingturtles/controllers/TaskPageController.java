package se.turingturtles.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import se.turingturtles.ProjectManagement;
import se.turingturtles.Validator;
import se.turingturtles.entities.Task;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class TaskPageController {

    @FXML
    private TableView taskTableView;

    @FXML
    private TableColumn taskName;

    @FXML
    private TableColumn taskStartWeek;

    @FXML
    private TableColumn taskDuration;

    @FXML
    private TableColumn taskTeamMembersAmount;

    @FXML
    private TableColumn taskStatus;

    @FXML
    private Button taskCreateTaskButton;

    @FXML
    private Text taskHeaderText;

    @FXML
    private DatePicker taskStartDate;

    @FXML
    private DatePicker taskEndDate;

    @FXML
    private AnchorPane createTaskAnchorPane;

    @FXML
    private TextField newTaskName;

    @FXML
    private Button newTaskCreateButton;
    @FXML
    private AnchorPane tableAnchorPane;

    private ProjectFactory projectFactory = new ProjectFactory();
    private ProjectManagement projectManagement = projectFactory.makeProjectManagement();

    @FXML public void initialize(){
        taskName.setCellValueFactory(new PropertyValueFactory<>("name"));
        taskStartWeek.setCellValueFactory(new PropertyValueFactory<>("startWeek"));
        taskDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        taskTeamMembersAmount.setCellValueFactory(new PropertyValueFactory<>("totalTeamMembers"));
        taskStatus.setCellValueFactory(new PropertyValueFactory<>("completion"));
        ObservableList<Task> tasks = FXCollections.observableArrayList(projectManagement.retrieveTasks());
        taskTableView.setItems(tasks);
        setDatePicker(taskStartDate);
        setDatePicker(taskEndDate);
        createTaskAnchorPane.setVisible(false);
        tableAnchorPane.setVisible(true);
    }

    private void updateTable(){
        ObservableList<Task> tasks = FXCollections.observableArrayList(projectManagement.retrieveTasks());
        taskTableView.setItems(tasks);
    }
    private void setDatePicker(DatePicker datePicker){
        datePicker.setStyle(String.valueOf(ProjectManagementImp.getProject().getProjectStartDate()));
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate projectStartDay = ProjectManagementImp.getProject().getProjectStartDate();
                LocalDate projectFinalDay = ProjectManagementImp.getProject().getProjectEndDate();
                setDisable(date.isAfter(projectFinalDay) || date.isBefore(projectStartDay));
            }
        });
        datePicker.setPromptText("Choose date:");
    }

    @FXML public void createNewTask(ActionEvent event){
        if (tableAnchorPane.isVisible()){

            tableAnchorPane.setVisible(false);
            createTaskAnchorPane.setVisible(true);
            taskCreateTaskButton.setText("View Tasks");
        }
        else {
            createTaskAnchorPane.setVisible(false);
            tableAnchorPane.setVisible(true);
            taskCreateTaskButton.setText("Create Task");
        }

    }
    @FXML public void applyNewTask(ActionEvent event){
        String name = newTaskName.getText();
        LocalDate taskStart = taskStartDate.getValue();
        LocalDate taskEnd = taskEndDate.getValue();
        Validator validator = projectFactory.makeValidator();
        if (validator.validateTextInput(name) && validator.validateDate(taskStart, taskEnd)) {
                projectManagement.createTask(name, taskStart, taskEnd);
                updateTable();
                resetCreateTaskFields();
                newTaskName.clear();

        }
        else if (!validator.validateTextInput(name)) {
            newTaskName.clear();
            newTaskName.setPromptText("Invalid Name!");
        }
        else if(!validator.validateDate(taskStart, taskEnd)){
            resetCreateTaskFields();
            taskStartDate.setPromptText("Set Valid Date!");
            taskEndDate.setPromptText("Set Valid Date!");

        }

    }

    private void resetCreateTaskFields(){
        taskStartDate.getEditor().clear();
        taskEndDate.getEditor().clear();
        newTaskName.setPromptText("Enter name:");
    }

}