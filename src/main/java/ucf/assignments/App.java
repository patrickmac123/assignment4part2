/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Patrick Mac
 */

package ucf.assignments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException
    {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("addTodo.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 946, 469);
        stage.setTitle("Todo Application");
        stage.setScene(scene);
        stage.show();





    }

    public static void main(String[] args)
    {
        launch();
    }
}