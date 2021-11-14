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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class Controller
{

    @FXML
    private Label mainTitle;

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
        System.out.println(list.get(0).getDescription());
        System.out.println(list.get(0).getDueDate());
        System.out.println(list.get(0).getStatus());
        System.out.println("");
        System.out.println(list.get(1).getDescription());
        System.out.println(list.get(1).getDueDate());
        System.out.println(list.get(1).getStatus());
        System.out.println("");
        System.out.println(list.get(2).getDescription());
        System.out.println(list.get(2).getDueDate());
        System.out.println(list.get(2).getStatus());


    }
    @FXML
    protected void newSavetodo()
    {
        todoList obj = new todoList(ID,inputDescription.getText(),(String)inputStatus.getValue(),inputDuedate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ID++;

        if (inputDescription.getText().length() >= 1 && inputDescription.getText().length() <= 256)
        {
            //obj.settitle(inputTitle.getText());
            //obj.description = inputDescription.getText();
            //obj.status = (String) inputStatus.getValue();
            //obj.dueDate = inputDuedate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            list.add(obj);
            inputDescription.clear();
            errorMsg.setText("");
            inputStatus.valueProperty().set(null);
            inputDuedate.setValue(null);

            myTable.getItems().setAll(list);

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
    protected void importList()
    {
        final Stage primaryStage = new Stage();
        fileChooser.setTitle("Load Dialog");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text File", "*.txt"));
        File file = fileChooser.showOpenDialog(primaryStage);

        List<String> lines;
        try
        {
            lines = Files.readAllLines(Paths.get(String.valueOf(file)), StandardCharsets.US_ASCII);
        }
        catch (Exception ex) {
            System.out.println("File not found.");
            return;
        }

        todoList loadedList;
        String line, description, status;
        String dueDate;
        for (int i = 0; i < lines.size(); i++)
        {
            line = lines.get(i);

            description = line.substring(0, line.indexOf(";"));
            line = line.substring(line.indexOf(";") + 1);

            dueDate = line.substring(0, line.indexOf(";"));
            line = line.substring(line.indexOf(";") + 1);

            status = line;
            loadedList = new todoList(list.size(), description, status,dueDate);
            list.add(loadedList);
        }
        myTable.getItems().setAll(list);
        myTable.refresh();
    }

    @FXML
    public void exportList(ActionEvent actionEvent)
    {
        final Stage primaryStage = new Stage();
        fileChooser.setTitle("Save Dialog");
        fileChooser.setInitialFileName("List1");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text File", "*.txt"));
        File file = fileChooser.showSaveDialog(primaryStage);
        String textFile = "";
        for(int i = 0; i<list.size(); i++){
            textFile += list.get(i).description.get();
            textFile += ";";

            textFile += list.get(i).dueDate.get();
            textFile += ";";

            textFile += list.get(i).status.get();
            if (i != list.size() - 1)
                textFile += "\n";
        }
        if (file != null) {
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.println(textFile);
                writer.close();
            } catch (IOException ex) {

            }
        }

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

        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    }


    @FXML
    public void editDescription(TableColumn.CellEditEvent<todoList, String> todoListStringCellEditEvent)
    {
        todoList description = myTable.getSelectionModel().getSelectedItem();
        description.setDescription(todoListStringCellEditEvent.getNewValue());
    }
    @FXML
    public void editDeadline(TableColumn.CellEditEvent<todoList, String> todoListStringCellEditEvent)
    {
        todoList dueDate = myTable.getSelectionModel().getSelectedItem();
        dueDate.setDueDate(todoListStringCellEditEvent.getNewValue());
    }
    @FXML
    public void editStatus(TableColumn.CellEditEvent<todoList, String> todoListStringCellEditEvent)
    {
        todoList status = myTable.getSelectionModel().getSelectedItem();
        status.setStatus(todoListStringCellEditEvent.getNewValue());
        System.out.println("test");
    }
    @FXML
    public void clearList(ActionEvent actionEvent)
    {
        list.clear();
        myTable.getItems().setAll(list);
    }
    @FXML
    public void removeItem(ActionEvent actionEvent)
    {
        int rowNum = myTable.getSelectionModel().getSelectedIndex();
        myTable.getItems().removeAll(myTable.getSelectionModel().getSelectedItem());
        int counter = rowNum;
        list.remove(counter);
        //myTable.getSelectionModel().getSelectedItem();
       // TableRow row = myTable.getItems().removeAll(myTable.getSelectionModel().getSelectedItem().cell.getTableRow();

    }

    @FXML
    public void showCompleted(ActionEvent actionEvent) throws IOException {


            ArrayList<todoList> sortedList;
            sortedList = new ArrayList<>();
            sortedList.clear();

        for (int i = 0;i<list.size(); i++)
        {
            if(list.get(i).getStatus().equals("Complete"))
            {
                sortedList.add(list.get(i));
            }
        }

        myTable.getItems().setAll(sortedList);
        myTable.refresh();

    }
    @FXML
    public void showIncompleted(ActionEvent actionEvent)
    {
        ArrayList<todoList> sortedList;
        sortedList = new ArrayList<>();
        sortedList.clear();

        for (int i = 0;i<list.size(); i++)
        {
            if(list.get(i).getStatus().equals("Incomplete"))
            {
                sortedList.add(list.get(i));
            }
        }

        myTable.getItems().setAll(sortedList);
        myTable.refresh();
    }
    @FXML
    public void showAll(ActionEvent actionEvent)
    {
        myTable.getItems().setAll(list);
        myTable.refresh();
    }

    FileChooser fileChooser = new FileChooser();



    @FXML
    public void saveSystem(File file,String content)
    {
        try{
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(content);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}