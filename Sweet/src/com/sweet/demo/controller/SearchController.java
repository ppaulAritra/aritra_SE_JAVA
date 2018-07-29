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

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    private static Session session;

    @FXML
    private Button btnBack;
    @FXML
    private TableColumn colDetails;
    @FXML
    private TableColumn colName;
    @FXML
    private TextArea Message;
    @FXML
    private CheckBox chkSold;
    @FXML
    private TableColumn colType;
    @FXML
    private TableColumn colAction;
    @FXML
    private TableColumn colBuy;
    @FXML
    private TableColumn colSold;
    @FXML
    private TableColumn colProfit;
    @FXML
    private Button btnDash;
    @FXML
    private TableView<Product> tableProduct;
    private Label label;
    Product product= new Product();
    ModelProduct model= new ModelProduct();
    boolean select=false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        colType.setCellValueFactory(new PropertyValueFactory<Product, String>("productType"));
        colBuy.setCellValueFactory(new PropertyValueFactory<Product, Integer>("buyPrice"));
        colDetails.setCellValueFactory(new PropertyValueFactory<Product, String>("productDetails"));
        colProfit.setCellValueFactory(new PropertyValueFactory<Product, Double>("profit"));
        colSold.setCellValueFactory(new PropertyValueFactory<Product, Integer>("sellPrice"));

        colAction.setCellFactory(col -> {//Adding Button in each row
            Button sellButton = new Button("Sell");
            Button removeButton = new Button("Remove");
            Button updateButton = new Button("Update");

            HBox hbox = new HBox(5);


            hbox.getChildren().addAll(sellButton, updateButton, removeButton);
            // hbox2.getChildren().addAll( updateButton, removeButton);
            TableCell<Product, Product> cell = new TableCell<Product, Product>() {
                @Override
                //Updating with the number of row
                public void updateItem(Product product, boolean empty) {
                    super.updateItem(product, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        if (!select) {// adding sell button when product is not sold
                            if(!hbox.getChildren().contains(sellButton))
                                hbox.getChildren().add(0,sellButton);
                            setGraphic(hbox);
                        }
                        else if(select){// removing sell button when product is already sold
                            if(hbox.getChildren().contains(sellButton))
                                hbox.getChildren().remove(sellButton);
                            setGraphic(hbox);}
                    }
                }
            };


            updateButton.setOnAction(event -> {

                TableRow row = cell.getTableRow();
                Product product = (Product) row.getItem();

                try {
                    updateAction(product);
                } catch (Exception e) {

                    e.printStackTrace();
                }

            });

            removeButton.setOnAction(event -> {
                TableRow row = cell.getTableRow();
                Product product = (Product) row.getItem();
                model.deleteProduct(product);
                tableProduct.layout();
                tableProduct.setItems(getInfo(false));
            });

            sellButton.setOnAction(event -> {//for a pop up
                TableRow row = cell.getTableRow();
                Product rowProduct = (Product) row.getItem();

                Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);

                dialog.setTitle("Sell Price");
                VBox dialogVbox = new VBox(10);
                Label text = new Label("Enter Your Selling Price");
                HBox h1= new HBox(10);
                HBox h2= new HBox(5);
                TextField txtSell= new TextField();
                txtSell.setPromptText("Sell Price");
                txtSell.textProperty().addListener(new ChangeListener<String>() {// Making Numeric text field
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                                        String newValue) {
                        if (!newValue.matches("\\d*")) {
                            txtSell.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    }
                });
                Button btnOk= new Button("Ok");
                Button btnCancel=new Button("Cancel");
                h1.getChildren().add(txtSell);
                h2.getChildren().addAll(btnOk,btnCancel);
                btnCancel.setOnAction(e->{

                    dialog.close();
                });
                btnOk.setOnAction(e->{
                    product.setBuyPrice(rowProduct.getBuyPrice());
                    product.setSellPrice(Integer.valueOf(txtSell.getText()));
                    int sell=Integer.valueOf(txtSell.getText());
                    int buy=rowProduct.getBuyPrice();
                    double profit=(((double)sell-(double)buy)/(double)buy)*100;
                    product.setProductId(rowProduct.getProductId());
                    product.setProfit(profit);
                    System.out.println(sell+" "+buy+" "+profit);
                    String msg= model.insertProfit(product);
                    if(!txtSell.getText().trim().isEmpty())
                        tableProduct.setItems(getInfo(false));
                        dialog.close();
                });
                dialogVbox.setPadding(new Insets(10, 10, 20, 20));

                dialogVbox.getChildren().addAll(text,h1,h2);

                Scene dialogScene = new Scene(dialogVbox, 250, 150);
                dialog.setScene(dialogScene);
                dialog.show();

            });


            return cell;
        });

        chkSold.selectedProperty().addListener(new ChangeListener<Boolean>() {//Listener in checkbox for showing sold/notsold products
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == true){
                    tableProduct.setItems(getInfo(true));
                    colProfit.setVisible(true);
                    colSold.setVisible(true);

                    select=true;
                }
                else{
                    tableProduct.setItems(getInfo(false));
                    colProfit.setVisible(false);
                    colSold.setVisible(false);
                    select=false;
                }
            }
        });


        tableProduct.setItems(getInfo(false));

        colProfit.setVisible(false);
        colSold.setVisible(false);
    }

    private ObservableList<Product> getInfo(boolean state) {// return list from database
        ObservableList<Product> list = FXCollections.observableArrayList();


        if (state == true)
            list = model.getProductSold();
        else
            list = model.getProductNotSold();


        if (list.isEmpty()) {
            Message.setText("No Product to show");

        } else

        {
            Message.clear();
        }
        return list;
    }


    private void updateAction(Product product) throws IOException {
        Product selectedItems = tableProduct.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sweet/demo/controller/Product.fxml"));
        Parent root = loader.load();

        ProductController cinfo = loader.<ProductController>getController();

        cinfo.preUpdateAction(product);
        Stage stage = (Stage) btnBack.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void goDash() throws IOException {
        Message.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/com/sweet/demo/controller/Dashboard.fxml"));
        Stage stage = (Stage) btnDash.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void backAction() throws IOException {
        Message.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/com/sweet/demo/controller/Product.fxml"));
        Stage stage = (Stage) btnBack.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
