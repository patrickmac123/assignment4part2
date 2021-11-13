/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Patrick Mac
 */

package ucf.assignments;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

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
    private TableColumn<todoList, String> idCol;

    @FXML
    private TableColumn<todoList, String> taskCol;

    @FXML
    private TableColumn<todoList, String> descriptionCol;

    @FXML
    private TableColumn<todoList, String> deadlineCol;

    @FXML
    private TableColumn<todoList, String> statusCol;

    public int ID = 0;


    ArrayList<todoList> list;

    @FXML
    private TableView<todoList> sorted;

    @FXML
    private TableColumn<todoList, String> sortedTask;

    @FXML
    private TableColumn<todoList, String> sortedDescription;

    @FXML
    private TableColumn<todoList, String> sortedDueDate;

    @FXML
    private TableColumn<todoList, String> sortedStatus;

    @FXML
    private CheckBox selectCol;

    @FXML
    protected void showObjects()
    {
        System.out.println(list.get(0).getTitle());
        System.out.println(list.get(0).getDescription());
        System.out.println(list.get(0).getDueDate());
        System.out.println(list.get(0).getStatus());
        System.out.println("");
        System.out.println(list.get(1).getTitle());
        System.out.println(list.get(1).getDescription());
        System.out.println(list.get(1).getDueDate());
        System.out.println(list.get(1).getStatus());
        System.out.println("");
        System.out.println(list.get(2).getTitle());
        System.out.println(list.get(2).getDescription());
        System.out.println(list.get(2).getDueDate());
        System.out.println(list.get(2).getStatus());


    }
    @FXML
    protected void newSavetodo()
    {
        todoList obj = new todoList(ID,inputTitle.getText(),inputDescription.getText(),(String)inputStatus.getValue(),inputDuedate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ID++;

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

    public ObservableList<String> statusChoices;

    public void initialize()
    {
        list = new ArrayList<>();
        inputStatus.getItems().add("Complete");
        inputStatus.getItems().add("Incomplete");

        statusChoices = FXCollections.observableArrayList();
        statusChoices.add("Complete");
        statusChoices.add("Incomplete");

        idCol.setCellValueFactory(new PropertyValueFactory<todoList,String>("ID"));

        taskCol.setCellValueFactory(new PropertyValueFactory<todoList,String>("title"));
        taskCol.setCellFactory(TextFieldTableCell.forTableColumn());

        descriptionCol.setCellValueFactory(new PropertyValueFactory<todoList,String>("description"));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());

        deadlineCol.setCellValueFactory(new PropertyValueFactory<todoList,String>("dueDate"));
        deadlineCol.setCellFactory(TextFieldTableCell.forTableColumn());

        statusCol.setCellValueFactory(new PropertyValueFactory<todoList,String>("status"));
        statusCol.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(),statusChoices));
        statusCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<todoList, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<todoList, String> event)
            {

                list.get(event.getRowValue().ID).setStatus(event.getNewValue());
            }
        });

        //selectCol.setCellValueFactory(new PropertyValueFactory<todoList,String>("select"));


        myTable.setEditable(true);
        myTable.refresh();
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
        System.out.println("test");
    }

    public void clearList(ActionEvent actionEvent)
    {
        list.clear();
        myTable.getItems().setAll(list);
    }

    public void removeItem(ActionEvent actionEvent)
    {
        int rowNum = myTable.getSelectionModel().getSelectedIndex();
        myTable.getItems().removeAll(myTable.getSelectionModel().getSelectedItem());
        int counter = rowNum;
        list.remove(counter);
        //myTable.getSelectionModel().getSelectedItem();
       // TableRow row = myTable.getItems().removeAll(myTable.getSelectionModel().getSelectedItem().cell.getTableRow();

    }


    public void showCompleted(ActionEvent actionEvent) throws IOException {


            ArrayList<todoList> sortedList;
            sortedList = new ArrayList<>();
            sortedList.clear();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/ucf/assignments/editTodo.fxml"));
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(fxmlLoader.load(), 630, 400);
            Stage stage = new Stage();
            stage.setTitle("Completed Tasks");
            stage.setScene(scene);
            stage.show();

        sortedTask.setCellValueFactory(new PropertyValueFactory<todoList,String>("title"));
        sortedDescription.setCellValueFactory(new PropertyValueFactory<todoList,String>("description"));
        sortedDueDate.setCellValueFactory(new PropertyValueFactory<todoList,String>("dueDate"));
        sortedStatus.setCellValueFactory(new PropertyValueFactory<todoList,String>("status"));

        for (todoList obj: list)
        {
            if (obj.status.getValue().equals("Completed"))
            {
                sortedList.add(obj);
            }
        }
        System.out.println(sortedList.get(0).getTitle());
        System.out.println(sortedList.get(0).getDescription());
        System.out.println(sortedList.get(0).getDueDate());
        System.out.println(sortedList.get(0).getStatus());
        sorted.getItems().setAll(sortedList);

        System.out.println(sortedList.get(0).getTitle());
        System.out.println(sortedList.get(0).getDescription());
        System.out.println(sortedList.get(0).getDueDate());
        System.out.println(sortedList.get(0).getStatus());

    }

    public void showIncompleted(ActionEvent actionEvent)
    {

    }
}