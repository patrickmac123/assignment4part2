/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Patrick Mac
 */

package ucf.assignments;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class Controller
{

    @FXML
    private Label mainTitle;

    @FXML
    private TextField inputTitle;

    @FXML
    private Text errorMsg;

    @FXML
    private TextField inputDescription;

    @FXML
    private ChoiceBox inputStatus;

    @FXML
    private DatePicker inputDuedate;

    @FXML
    private TableView<todoList> myTable;

    @FXML
    private TableColumn<todoList, String> taskCol;

    @FXML
    private TableColumn<todoList, String> descriptionCol;

    @FXML
    private TableColumn<todoList, String> deadlineCol;

    @FXML
    private TableColumn<todoList, String> statusCol;




    ArrayList<todoList> list;

    @FXML
    protected void showObjects()
    {
        System.out.println(list.get(0).getTitle());
        System.out.println(list.get(0).getDescription());
        System.out.println(list.get(0).getDueDate());
        System.out.println(list.get(0).getStatus());
    }
    @FXML
    protected void newSavetodo()
    {
        todoList obj = new todoList(inputTitle.getText(),inputDescription.getText(),(String)inputStatus.getValue(),inputDuedate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        if (inputDescription.getText().length() >= 1 && inputDescription.getText().length() <= 256)
        {
            //obj.settitle(inputTitle.getText());
            //obj.description = inputDescription.getText();
            //obj.status = (String) inputStatus.getValue();
            //obj.dueDate = inputDuedate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            list.add(obj);
            inputTitle.clear();
            inputDescription.clear();
            errorMsg.setText("");
            inputStatus.valueProperty().set(null);
            inputDuedate.setValue(null);

            myTable.getItems().setAll(list);

            System.out.println(obj.title);
            System.out.println(obj.description);
            System.out.println(obj.status);
            System.out.println(obj.dueDate);

        }
        else
        {
            errorMsg.setText("The description must be between 1 and 256 characters");
        }



    }




    @FXML
    protected void openViewlist(ActionEvent event) throws IOException
    {

    }

    @FXML
    protected void editTodo()
    {

    }

    @FXML
    protected void deleteTodo()
    {

    }

    @FXML
    protected void loadList()
    {

    }

    @FXML
    protected void saveList()
    {

    }

    public void initialize()
    {
        list = new ArrayList<>();
        inputStatus.getItems().add("Complete");
        inputStatus.getItems().add("Incomplete");



        taskCol.setCellValueFactory(new PropertyValueFactory<todoList,String>("title"));
        taskCol.setCellFactory(TextFieldTableCell.forTableColumn());

        descriptionCol.setCellValueFactory(new PropertyValueFactory<todoList,String>("description"));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());

        deadlineCol.setCellValueFactory(new PropertyValueFactory<todoList,String>("dueDate"));
        deadlineCol.setCellFactory(TextFieldTableCell.forTableColumn());

        statusCol.setCellValueFactory(new PropertyValueFactory<todoList,String>("status"));
        statusCol.setCellFactory(TextFieldTableCell.forTableColumn());

        myTable.setEditable(true);



    }


    public void editTask(TableColumn.CellEditEvent<todoList, String> todoListStringCellEditEvent)
    {
        todoList task = myTable.getSelectionModel().getSelectedItem();
        task.setTitle(todoListStringCellEditEvent.getNewValue());
    }

    public void editDescription(TableColumn.CellEditEvent<todoList, String> todoListStringCellEditEvent)
    {
        todoList description = myTable.getSelectionModel().getSelectedItem();
        description.setDescription(todoListStringCellEditEvent.getNewValue());
    }

    public void editDeadline(TableColumn.CellEditEvent<todoList, String> todoListStringCellEditEvent)
    {
        todoList dueDate = myTable.getSelectionModel().getSelectedItem();
        dueDate.setDueDate(todoListStringCellEditEvent.getNewValue());
    }

    public void editStatus(TableColumn.CellEditEvent<todoList, String> todoListStringCellEditEvent)
    {
        todoList status = myTable.getSelectionModel().getSelectedItem();
        status.setStatus(todoListStringCellEditEvent.getNewValue());
    }
}