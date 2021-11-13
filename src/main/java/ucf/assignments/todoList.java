package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class todoList
{


    public int ID;
    public StringProperty title;
    public StringProperty description;
    public StringProperty status;
    public StringProperty dueDate;



    todoList(int ID2, String title2, String description2, String status2, String dueDate2)
    {
        this.ID = ID2;
        this.title = new SimpleStringProperty(title2);
        this.description = new SimpleStringProperty(description2);
        this.status = new SimpleStringProperty(status2);
        this.dueDate = new SimpleStringProperty(dueDate2);

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public String getTitle()
    {
        return title.get();
    }
    public void setTitle(String title2)
    {
        title.set(title2);
    }
    public String getDescription()
    {
        return description.get();
    }
    public void setDescription(String description2)
    {
        description.set(description2);
    }
    public String getStatus()
    {
        return status.get();
    }
    public void setStatus(String status2)
    {
        status.set(status2);
    }
    public String getDueDate()
    {
        return dueDate.get();
    }
    public void setDueDate(String dueDate2)
    {
        dueDate.set(dueDate2);
    }



}

