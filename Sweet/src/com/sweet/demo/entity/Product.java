package com.sweet.demo.entity;

/**
 * Created by Aritra Paul on 7/28/2018.
 */
import javafx.beans.property.*;

import javax.persistence.*;

@Entity

@Table
public class Product {
    @Id @GeneratedValue
    @Column(nullable=false,length=50)
    private int productId ;
    @Column(nullable=false,length=50)
    private String productName;

    @Column(nullable=false,length=25)
    private String productType;
    @Column(nullable=false,length=50)
    private int buyPrice ;
    @Column
    private int sellPrice ;
    @Column
    private double profit;
    @Column
    private int sell ;
    @Column(length=500)
    private String productDetails;

    @Transient
    private int totalsold;
    @Transient
    private double avgProfit;
    public Product(){

    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public int getSell() {
        return sell;
    }

    public void setSell(int sell) {
        this.sell = sell;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }
    @Transient
    public int getTotalsold() {
        return totalsold;
    }

    public void setTotalsold(int totalsold) {
        this.totalsold = totalsold;
    }

    public double getAvgProfit() {
        return avgProfit;
    }

    public void setAvgProfit(double avgProfit) {
        this.avgProfit = avgProfit;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                ", profit=" + profit +
                ", sell=" + sell +
                ", productDetails='" + productDetails + '\'' +
                ", totalsold=" + totalsold +
                ", avgProfit=" + avgProfit +
                '}';
    }
}
