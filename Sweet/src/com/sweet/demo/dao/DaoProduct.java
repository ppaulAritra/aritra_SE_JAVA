package com.sweet.demo.dao;

import com.sweet.demo.dto.ProductDto;
import com.sweet.demo.entity.Product;
import javafx.collections.ObservableList;

/**
 * Created by Aritra Paul on 7/28/2018.
 */
public interface DaoProduct {
    public ObservableList<Product> getProductNotSold();
    public ObservableList<Product> getProductSold();


    public String saveProduct(Product product);
    public String updateProduct(Product product);
    public String deleteProduct(Product product);
    public String insertProfit(Product product);

    public ObservableList getProfitable();
    public ObservableList getSold();
}
