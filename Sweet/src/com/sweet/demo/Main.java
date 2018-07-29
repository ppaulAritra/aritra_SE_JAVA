package com.sweet.demo;

/**
 * Created by Aritra Paul on 7/28/2018.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;




public class Main extends Application {



    @Override
    public void start(Stage window) throws Exception {


       // Parent root = FXMLLoader.load(getClass().getResource("com/sweet/demo/controller/login.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/sweet/demo/controller/Login.fxml"));

        Parent root =loader.load();

        Scene scene = new Scene(root);
        window.setTitle("Version 1.0");

        window.setScene(scene);
        window.show();




    }
    public static void main(String [] args) {
        if(Hibutil.setSessionFactory())// obtaining hibernate connection
            launch(args);
        else
        {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An error has occured!");
                alert.setHeaderText("Database Connection Error!");
                alert.setContentText("Please contact the developer");
                alert.showAndWait();
                Platform.exit();
            });
        }
    }
}

