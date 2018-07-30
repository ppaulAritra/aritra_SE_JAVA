package com.sweet.demo.controller;

/**
 * Created by Aritra Paul on 7/28/2018.
 */
import com.sweet.demo.Dto.ProductDto;

import com.sweet.demo.entity.Product;
import com.sweet.demo.model.ModelProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable{

    @FXML
    private Button btnLogout;

    @FXML
    private TextArea Message;

    @FXML
    private Button btnAdd;



    @FXML
    private Button btnProfit;

    @FXML
    private TableColumn colProduct;

    @FXML
    private TableColumn colCount;

    @FXML
    private Button btnSold;

    @FXML
    private TableColumn colProfit;

    @FXML
    private TableColumn colProductName;

    @FXML
    private Button btnSearchProduct;
    @FXML
    private TableView <Product>tableProfit;
    @FXML
    private TableView <Product>tableProductSold;

    ModelProduct model = new ModelProduct();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colProductName.setCellValueFactory(new PropertyValueFactory<Product,String>("productName"));
        colProfit.setCellValueFactory(new PropertyValueFactory<Product,Double>("avgProfit"));
        colProduct.setCellValueFactory(new PropertyValueFactory<Product,String>("productName"));
        colCount.setCellValueFactory(new PropertyValueFactory<Product,Integer>("totalsold"));


    }

    private ObservableList<Product> getMostSold() {
        ObservableList<Product> list = FXCollections.observableArrayList();
        list = model.getSold();

        if (list.isEmpty()) {
            Message.setText("No Product to show");

        } else
        {
            Message.clear();
        }

        return list;
    }
    private ObservableList <Product> getProfitable() {
        ObservableList <Product> list = FXCollections.observableArrayList();
        list = model.getProfitable();
        if (list.isEmpty()) {
            Message.setText("No Product to show");

        } else
        {
            Message.clear();
        }

        return list;
    }

    @FXML
    void searchProduct() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/sweet/demo/controller/Search.fxml"));
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void addProduct() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/com/sweet/demo/controller/Product.fxml"));
        Stage stage= (Stage) btnAdd.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void logOut()throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/sweet/demo/controller/Login.fxml"));
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void searchMostProfit() {
        tableProfit.setItems(getProfitable());

    }

    @FXML
    void searchSold() {
        tableProductSold.setItems(getMostSold());
    }


}

