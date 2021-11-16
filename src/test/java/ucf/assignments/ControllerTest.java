package ucf.assignments;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    //The list shall have the capacity to store at least 100 unique items
    @Test
    void TestCase1()
    {
        Controller controller = new Controller();
        for(int i = 0;i<=100;i++)
        {
            todoList obj = new todoList(0,"test","Complete","2021-05-21");
            controller.list.add(obj);
        }
        assertEquals(controller.list.size(),101);
    }

    //A description shall be between 1 and 256 characters in length
    @Test
    void TestCase2()
    {
        Controller controller = new Controller();

        //characters in between 1-256
        todoList obj = new todoList(0,"test","Complete","2021-05-21");

        //no characters
        todoList obj2 = new todoList(0,"","Complete","2021-05-21");

        //257 characers
        todoList obj3 = new todoList(0,"aaaaaaaaaaaaaaaaaa " +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaa","Complete","2021-05-21");

        assertEquals(controller.checkDescription(obj.getDescription()),true);

        assertEquals(controller.checkDescription(obj2.getDescription()),false);

        assertEquals(controller.checkDescription(obj3.getDescription()),false);

    }

    //A due date shall be a valid date within the Gregorian Calendar
    //A due date shall be displayed to users in the format: YYYY-MM-DD
    @Test
    void TestCase3()
    {
        Controller controller = new Controller();
        todoList obj = new todoList(0,"test","Complete","2021-05-21");

        todoList obj2 = new todoList(0,"test","Complete","wrong date");

        //Checks if the date entered is in the correct format
        assertEquals(controller.checkDate(obj.getDueDate()),true);
        assertEquals(controller.checkDate(obj2.getDueDate()),false);
    }

    //A user shall be able to add a new item to the list
    @Test
    void TestCase4()
    {
        Controller controller = new Controller();
        todoList obj = new todoList(0,"test","Complete","2021-05-21");
        controller.addItem(obj);
        assertEquals(controller.list.get(0).getID(),0);
        assertEquals(controller.list.get(0).getDescription(),"test");
        assertEquals(controller.list.get(0).getStatus(),"Complete");
        assertEquals(controller.list.get(0).getDueDate(),"2021-05-21");
    }

    //A user shall be able to remove an item from the list
    @Test
    void TestCase5()
    {
        Controller controller = new Controller();
        todoList obj = new todoList(0,"first item","Incomplete","2024-06-22");
        todoList obj2 = new todoList(1,"second item","Complete","2021-05-21");
        controller.addItem(obj);
        controller.addItem(obj2);
        controller.remove(0);
        assertEquals(controller.list.get(0).getID(),1);
        assertEquals(controller.list.get(0).getDescription(),"second item");
        assertEquals(controller.list.get(0).getStatus(),"Complete");
        assertEquals(controller.list.get(0).getDueDate(),"2021-05-21");
    }

    //A user shall be able to clear the list of all items
    @Test
    void TestCase6()
    {
        Controller controller = new Controller();
        todoList obj = new todoList(0,"first item","Incomplete","2024-01-22");
        todoList obj2 = new todoList(1,"second item","Complete","2021-05-21");
        todoList obj3 = new todoList(2,"third item","Incomplete","2022-06-23");
        controller.addItem(obj);
        controller.addItem(obj2);
        controller.addItem(obj3);
        controller.clearAll();
        assertEquals(controller.list.isEmpty(),true);
    }

    //A user shall be able to edit the description of an item within the list
    @Test
    void TestCase7()
    {
        Controller controller = new Controller();
        todoList obj = new todoList(0,"first item","Incomplete","2024-01-22");
        controller.addItem(obj);

        controller.updateDescription(obj,"new todo");

        assertEquals(controller.list.get(0).getDescription(),"new todo");

    }

    //A user shall be able to edit the due date of an item within the list
    @Test
    void TestCase8()
    {
        Controller controller = new Controller();
        todoList obj = new todoList(0,"first item","Incomplete","2024-01-22");
        controller.addItem(obj);

        controller.updateDeadline(obj,"2029-01-26");

        assertEquals(controller.list.get(0).getDueDate(),"2029-01-26");
    }

    //A user shall be able to mark an item in the list as either complete or incomplete
    @Test
    void TestCase9()
    {
        Controller controller = new Controller();
        todoList obj = new todoList(0,"first item","Incomplete","2024-01-22");
        controller.addItem(obj);
        String status = "Complete";
        controller.updateStatus(obj,status);
        assertEquals(controller.list.get(0).getStatus(),"Complete");

    }

    //A user shall be able to display all of the existing items in the list
    @Test
    void TestCase10()
    {
        Controller controller = new Controller();
        todoList obj = new todoList(0,"first item","Incomplete","2024-01-22");
        todoList obj2 = new todoList(1,"second item","Incomplete","2024-01-22");
        todoList obj3 = new todoList(2,"third item","Incomplete","2024-01-22");
        todoList obj4 = new todoList(3,"fourth item","Complete","2024-01-22");
        todoList obj5 = new todoList(4,"fifth item","Complete","2024-01-22");
        todoList obj6 = new todoList(5,"sixth item","Complete","2024-01-22");

        controller.addItem(obj);
        controller.addItem(obj2);
        controller.addItem(obj3);
        controller.addItem(obj4);
        controller.addItem(obj5);
        controller.addItem(obj6);

        controller.showList(controller.list);
    }

    //A user shall be able to display only the incomplete items in the list
    @Test
    void TestCase11()
    {
        Controller controller = new Controller();
        todoList obj = new todoList(0,"first item","Incomplete","2024-01-22");
        todoList obj2 = new todoList(1,"second item","Incomplete","2024-01-22");
        todoList obj3 = new todoList(2,"third item","Incomplete","2024-01-22");
        todoList obj4 = new todoList(3,"fourth item","Complete","2024-01-22");
        todoList obj5 = new todoList(4,"fifth item","Complete","2024-01-22");
        todoList obj6 = new todoList(5,"sixth item","Complete","2024-01-22");

        controller.addItem(obj);
        controller.addItem(obj2);
        controller.addItem(obj3);
        controller.addItem(obj4);
        controller.addItem(obj5);
        controller.addItem(obj6);

        controller.sortIncomplete();

        assertEquals(controller.sortedList.get(0).getStatus(),"Incomplete");
        assertEquals(controller.sortedList.get(1).getStatus(),"Incomplete");
        assertEquals(controller.sortedList.get(2).getStatus(),"Incomplete");


    }

    //A user shall be able to display only the completed items in the list
    @Test
    void TestCase12()
    {
        Controller controller = new Controller();
        todoList obj = new todoList(0,"first item","Incomplete","2024-01-22");
        todoList obj2 = new todoList(1,"second item","Incomplete","2024-01-22");
        todoList obj3 = new todoList(2,"third item","Incomplete","2024-01-22");
        todoList obj4 = new todoList(3,"fourth item","Complete","2024-01-22");
        todoList obj5 = new todoList(4,"fifth item","Complete","2024-01-22");
        todoList obj6 = new todoList(5,"sixth item","Complete","2024-01-22");

        controller.addItem(obj);
        controller.addItem(obj2);
        controller.addItem(obj3);
        controller.addItem(obj4);
        controller.addItem(obj5);
        controller.addItem(obj6);

        controller.sortComplete();

        assertEquals(controller.sortedList.get(0).getStatus(),"Complete");
        assertEquals(controller.sortedList.get(1).getStatus(),"Complete");
        assertEquals(controller.sortedList.get(2).getStatus(),"Complete");
    }

    //A user shall be able to save the list (and all of its items) to external storage
    @Test
    void TestCase13()
    {
        Controller controller = new Controller();
        todoList obj = new todoList(0,"first item","Incomplete","2024-01-22");
        todoList obj2 = new todoList(1,"second item","Incomplete","2024-01-22");
        todoList obj3 = new todoList(2,"third item","Incomplete","2024-01-22");
        todoList obj4 = new todoList(3,"fourth item","Complete","2024-01-22");
        todoList obj5 = new todoList(4,"fifth item","Complete","2024-01-22");
        todoList obj6 = new todoList(5,"sixth item","Complete","2024-01-22");

        controller.addItem(obj);
        controller.addItem(obj2);
        controller.addItem(obj3);
        controller.addItem(obj4);
        controller.addItem(obj5);
        controller.addItem(obj6);

        File file = new File("JunitListExportTest.txt");

        controller.save(file);
        assertTrue(file.exists());

    }

    //A user shall be able to load a list (and all of its items) from external storage
    @Test
    void TestCase14()
    {
        Controller controller = new Controller();
        todoList obj = new todoList(0,"first item","Incomplete","2024-01-22");
        todoList obj2 = new todoList(1,"second item","Complete","2024-01-22");
        todoList obj3 = new todoList(2,"third item","Incomplete","2024-01-22");

        controller.addItem(obj);
        controller.addItem(obj2);
        controller.addItem(obj3);

        File file = new File("JunitListImportTest.txt");
        controller.save(file);

        controller.load(file);

        assertEquals(controller.list.get(0).getID(),0);
        assertEquals(controller.list.get(0).getDescription(),"first item");
        assertEquals(controller.list.get(0).getStatus(),"Incomplete");
        assertEquals(controller.list.get(0).getDueDate(),"2024-01-22");

        assertEquals(controller.list.get(1).getID(),1);
        assertEquals(controller.list.get(1).getDescription(),"second item");
        assertEquals(controller.list.get(1).getStatus(),"Complete");
        assertEquals(controller.list.get(1).getDueDate(),"2024-01-22");

        assertEquals(controller.list.get(2).getID(),2);
        assertEquals(controller.list.get(2).getDescription(),"third item");
        assertEquals(controller.list.get(2).getStatus(),"Incomplete");
        assertEquals(controller.list.get(2).getDueDate(),"2024-01-22");

    }
}