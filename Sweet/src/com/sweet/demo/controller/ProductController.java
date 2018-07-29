package com.sweet.demo.controller;

/**
 * Created by Aritra Paul on 7/28/2018.
 */
import com.sweet.demo.entity.Product;
import com.sweet.demo.model.ModelProduct;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable{

    @FXML
    private TextField txtId;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnNew;

    @FXML
    private TextArea Message;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDetails;

    @FXML
    private ComboBox cmbType;

    @FXML
    private TextField txtBuyPrice;
    private final ObservableList typeList = FXCollections.observableArrayList("GRAPHICS CARD","MOTHERBOARD","RAM");//List containing product type

    Product product= new Product();
    ModelProduct model= new ModelProduct();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtBuyPrice.textProperty().addListener(new ChangeListener<String>() {// Making Numeric text field
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtBuyPrice.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        cmbType.setItems(typeList);

    }

    @FXML
    void newProduct() {
        txtName.setText("");
        txtBuyPrice.setText("");
        txtDetails.setText("");
        cmbType.getSelectionModel().clearSelection();
        txtId.setText("");
    }

    @FXML
    void saveAction() {
        Message.clear();
        if(txtId.getText()==null||txtId.getText().trim().isEmpty()) {
            if (validation()) {
                product.setProductName(txtName.getText());
                product.setProductType(String.valueOf(cmbType.getValue()));
                product.setBuyPrice(Integer.valueOf(txtBuyPrice.getText()));
                product.setProductDetails(txtDetails.getText());
                String message = model.saveProduct(product);
                Message.setText(message);

            }
        }
        else {
            if (validation()) {
                product.setProductId(Integer.valueOf(txtId.getText()));
                product.setProductName(txtName.getText());
                product.setProductType(String.valueOf(cmbType.getValue()));
                product.setBuyPrice(Integer.valueOf(txtBuyPrice.getText()));
                product.setProductDetails(txtDetails.getText());
                String message = model.updateProduct(product);
                Message.setText(message);

            }
        }
    }



    @FXML
    void searchAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/sweet/demo/controller/Search.fxml"));
        Stage stage = (Stage) btnSearch.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    private boolean validation() {
        if(txtName.getText().trim().equals("")){
            Message.setText("Provide  Name");
            return false;
        }
//        System.out.println(cmbType.getValue().toString());
            if(cmbType.getValue()==null)
            {
                Message.setText("Please Select Product Type");
                return false;
            }
        if(txtBuyPrice.getText().trim().equals("")|| txtBuyPrice.getText()== null)
        {
            Message.setText("Please Enter Buy Price");
            return false;
        }

        return true;
    }


    public void preUpdateAction(Product product) {
        txtName.setText(product.getProductName());
        txtBuyPrice.setText(String.valueOf(product.getBuyPrice()));
        txtDetails.setText(product.getProductDetails());
        cmbType.getSelectionModel().select(product.getProductType());
        txtId.setText(String.valueOf(product.getProductId()));
    }
}