package com.sweet.demo.controller;

/**
 * Created by Aritra Paul on 7/28/2018.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtId;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField pass;

    String userId= "Aoyon123";
    String password= "Aoyon123";
    @FXML
    void userLogin() throws IOException {
        if(txtId.getText().trim().equals(userId)&& pass.getText().trim().equals(password)) {
            Parent root = FXMLLoader.load(getClass().getResource("/com/sweet/demo/controller/Dashboard.fxml"));
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
    }

}