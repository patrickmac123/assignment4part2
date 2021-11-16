/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Patrick Mac
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller
{

    @FXML
    private Text errorMsg;

    @FXML
    private TextField inputDescription;

    @FXML
    private ChoiceBox inputStatus;

    @FXML
    private DatePicker inputDueDate;

    @FXML
    public TableView<todoList> myTable;
    
    @FXML
    private TableColumn<todoList, String> descriptionCol;

    @FXML
    private TableColumn<todoList, String> deadlineCol;

    @FXML
    private TableColumn<todoList, String> statusCol;

    public int ID = 0;

    public ArrayList<todoList> list = new ArrayList<>();

    public ObservableList<String> statusChoices;

    ArrayList<todoList> sortedList = new ArrayList<>();

    FileChooser fileChooser = new FileChooser();

    @FXML
    protected void newSavetodo() {
        if (inputDescription.getText().equals("") || inputStatus.getSelectionModel().isEmpty() || inputDueDate.getValue() == null)
        {
            errorMsg.setText("You cannot leave anything blank");
        }
        else
        {
            todoList obj = new todoList(ID, inputDescription.getText(), (String) inputStatus.getValue(), inputDueDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            ID++;

            boolean check = checkDescription(inputDescription.getText());
            System.out.println(obj.getDueDate());
            boolean date = checkDate(obj.getDueDate());



                if (date) {
                    if (check) {

                        addItem(obj);
                        inputDescription.clear();
                        errorMsg.setText("");
                        inputStatus.valueProperty().set(null);
                        inputDueDate.setValue(null);

                        myTable.getItems().setAll(list);

                        System.out.println(obj.description);
                        System.out.println(obj.status);
                        System.out.println(obj.dueDate);

                    } else {
                        errorMsg.setText("The description must be between 1 and 256 characters");
                    }
                } else {
                    errorMsg.setText("The date is an the incorrect format");
                }
            }
        }

    public boolean checkDate(String date)
    {
        if(date.matches("\\d{4}-\\d{2}-\\d{2}") &&
                Integer.parseInt(date.substring(5,7)) >= 1 && Integer.parseInt(date.substring(5,7)) <= 12 &&
                Integer.parseInt(date.substring(8,10)) >= 1 && Integer.parseInt(date.substring(8,10)) <= 31)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean checkDescription(String desc)
    {
        if(desc.length() >= 1 && desc.length() <= 256)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public void addItem(todoList obj)
    {
            list.add(obj);
    }

    @FXML
    public void importList(ActionEvent actionEvent)
    {
        final Stage primaryStage = new Stage();
        fileChooser.setTitle("Load Dialog");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text File", "*.txt"));
        File file = fileChooser.showOpenDialog(primaryStage);

        load(file);

        myTable.getItems().setAll(list);
        myTable.refresh();

    }

    public void load(File file)
    {
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
            ID++;
        }

    }

    @FXML
    public void exportList(ActionEvent actionEvent)
    {
        final Stage primaryStage = new Stage();
        fileChooser.setTitle("Save Dialog");
        fileChooser.setInitialFileName("List1");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text File", "*.txt"));
        File file = fileChooser.showSaveDialog(primaryStage);
        save(file);
    }

    public void save(File file)
    {

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

    public void initialize()
    {

        inputStatus.getItems().add("Complete");
        inputStatus.getItems().add("Incomplete");

        statusChoices = FXCollections.observableArrayList();
        statusChoices.add("Complete");
        statusChoices.add("Incomplete");

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
                todoList row = list.get(event.getRowValue().ID);
                String newValue = event.getNewValue();
                updateStatus(row,newValue);
            }
        });

        myTable.setEditable(true);
        myTable.refresh();

        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    }

    public void updateStatus(todoList row,String newValue)
    {
        row.setStatus(newValue);
    }

    @FXML
    public void editDescription(TableColumn.CellEditEvent<todoList, String> todoListStringCellEditEvent)
    {
        boolean check = checkDescription(todoListStringCellEditEvent.getNewValue());
        if(check)
        {
            todoList description = myTable.getSelectionModel().getSelectedItem();
            updateDescription(description,todoListStringCellEditEvent.getNewValue());
            errorMsg.setText("");
        }
        else
        {
            errorMsg.setText("The description must be between 1 and 256 characters");
            myTable.refresh();
        }

    }
    public void updateDescription(todoList description,String newDescription)
    {
        description.setDescription(newDescription);
    }

    @FXML
    public void editDeadline(TableColumn.CellEditEvent<todoList, String> todoListStringCellEditEvent)
    {
        boolean check = checkDate(todoListStringCellEditEvent.getNewValue());
        if(check)
        {
            todoList dueDate = myTable.getSelectionModel().getSelectedItem();
            updateDeadline(dueDate,todoListStringCellEditEvent.getNewValue());
            errorMsg.setText("");
        }
        else
        {
            errorMsg.setText("The date is an the incorrect format");
            myTable.refresh();
        }

    }
    public void updateDeadline(todoList dueDate,String newDueDate)
    {
        dueDate.setDueDate(newDueDate);
    }

    @FXML
    public void clearList(ActionEvent actionEvent)
    {
        clearAll();
        myTable.getItems().setAll(list);
    }
    public void clearAll()
    {
        list.clear();
        ID = 0;
    }

    @FXML
    public void removeItem(ActionEvent actionEvent)
    {
        int n = myTable.getSelectionModel().getSelectedIndex();
        if(n >= 0)
        {
            int rowNum = myTable.getSelectionModel().getSelectedIndex();
            myTable.getItems().removeAll(myTable.getSelectionModel().getSelectedItem());
            int counter = rowNum;
            remove(counter);
        }
    }
    public void remove(int counter)
    {
        list.remove(counter);
        for(int i=0;i < list.size();i++)
        {
            if(list.get(i).getID() == 0)
            {

            }
            else
            {
                list.get(i).setID(ID - 1);
            }
        }
    }

    @FXML
    public void showCompleted(ActionEvent actionEvent)
    {
        ArrayList<todoList> sortedList = sortComplete();
        myTable.getItems().setAll(sortedList);
        myTable.refresh();
    }

    public ArrayList<todoList> sortComplete()
    {

        sortedList.clear();

        for (int i = 0;i<list.size(); i++)
        {
            if(list.get(i).getStatus().equals("Complete"))
            {
                sortedList.add(list.get(i));
            }
        }
        return sortedList;
    }


    @FXML
    public void showIncompleted(ActionEvent actionEvent)
    {
        ArrayList<todoList> sortedList = sortIncomplete();
        myTable.getItems().setAll(sortedList);
        myTable.refresh();
    }
    public ArrayList<todoList> sortIncomplete()
    {

        sortedList.clear();

        for (int i = 0;i<list.size(); i++)
        {
            if(list.get(i).getStatus().equals("Incomplete"))
            {
                sortedList.add(list.get(i));
            }
        }
        return sortedList;
    }

    @FXML
    public void showAll(ActionEvent actionEvent)
    {
        showList(list);
        myTable.refresh();
    }
    public void showList(ArrayList<todoList> list)
    {
        myTable.getItems().setAll(list);
    }

}