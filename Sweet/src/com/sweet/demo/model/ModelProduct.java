package com.sweet.demo.model;

import com.sweet.demo.Dto.ProductDto;
import com.sweet.demo.Hibutil;
import com.sweet.demo.dao.DaoProduct;
import com.sweet.demo.entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by Aritra Paul on 7/28/2018.
 */
public class ModelProduct implements DaoProduct {
    private static Session session;
    @Override
    public ObservableList<Product> getProductNotSold() {
        ObservableList<Product> list = FXCollections.observableArrayList();
        Transaction tx=null;

        session = Hibutil.getSessionFactory().getCurrentSession();
        try {
            tx= session.beginTransaction();
            List<Product> productList = session.createQuery("from Product where sell ='0'").setCacheable(true).list();
            tx.commit();
            productList.stream().forEach(list::add);
            //System.out.println(list);
            return list;
        }catch(HibernateException e) {
            if(tx!=null)tx.rollback();
            return null;
        }
    }

    @Override
    public ObservableList<Product> getProductSold() {
        ObservableList<Product> list = FXCollections.observableArrayList();
        Transaction tx=null;

        session = Hibutil.getSessionFactory().getCurrentSession();
        try {
            tx= session.beginTransaction();
            List<Product> productList = session.createQuery("from Product where sell='1'").setCacheable(true).list();
           tx.commit();
            productList.stream().forEach(list::add);

            return list;
        }catch(HibernateException e) {
            if(tx!=null)tx.rollback();
            return null;
        }
    }

    @Override
    public String saveProduct(Product product) {
        session = Hibutil.getSessionFactory().getCurrentSession();
        Transaction tx= null;
        try {
            tx= session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
            return "Successfully Inserted";
        }
        catch(HibernateException e) {
            if(tx!=null)tx.rollback();
            e.printStackTrace();
            return "Exception Occured";


        }
    }

    @Override
    public String updateProduct(Product product) {
        session = Hibutil.getSessionFactory().getCurrentSession();
        Transaction tx=null;
        try {
            tx=session.beginTransaction();
            Product  p = session.get(Product.class, product.getProductId());
            p.setProductName(product.getProductName());
            p.setBuyPrice(product.getBuyPrice());
            p.setProductType(product.getProductType());
            p.setProductDetails(product.getProductDetails());

            session.update(p);
            session.getTransaction().commit();
            return "Successfully Updated";
        }

        catch(HibernateException e) {
            if(tx!=null)tx.rollback();
            e.printStackTrace();
            return "Exception Occured";

        }
    }

    @Override
    public String deleteProduct(Product product) {
        session = Hibutil.getSessionFactory().getCurrentSession();
        Transaction tx=null;
        try {
            tx=session.beginTransaction();
           Product  p = session.get(Product.class, product.getProductId());

            session.delete(p);
            session.getTransaction().commit();
            return "Successfully Deleted";
        }

        catch(HibernateException e) {
            if(tx!=null)tx.rollback();
            e.printStackTrace();
            return "Exception Occured";
        }
    }

    @Override
    public String insertProfit(Product product) {
        session = Hibutil.getSessionFactory().getCurrentSession();
        Transaction tx=null;
        try {
            tx=session.beginTransaction();
            Product  p = session.get(Product.class, product.getProductId());
            p.setSellPrice(product.getSellPrice());
            p.setProfit(product.getProfit());
            p.setSell(1);
            session.update(p);
            session.getTransaction().commit();
            return "Successfully Sold";
        }

        catch(HibernateException e) {
            if(tx!=null)tx.rollback();
            e.printStackTrace();
            return "Exception Occured";

        }
    }

    @Override
    public ObservableList <Product >getProfitable() {
        ObservableList<Product> list = FXCollections.observableArrayList();
        Transaction tx=null;

        session = Hibutil.getSessionFactory().getCurrentSession();
        try {
            tx= session.beginTransaction();
            List <Object[]>productList = session.createQuery("select productName,avg(profit) as avgProfit from Product group by productName "
                    +"order by avg(profit) desc").setFirstResult(0).setMaxResults(5).list();
            tx.commit();
            for(Object []row:productList)
            {Product p= new Product();
            p.setProductName(row[0].toString());
            p.setAvgProfit(Double.valueOf(row[1].toString()));
            list.add(p);

            }


            return list;
        }catch(HibernateException e) {
            if(tx!=null)tx.rollback();
            return null;
        }
    }

    @Override
    public ObservableList <Product >getSold() {
        ObservableList<Product >list = FXCollections.observableArrayList();
        Transaction tx=null;
        ;
        session = Hibutil.getSessionFactory().getCurrentSession();
        try {
            tx= session.beginTransaction();

           List <Object[] >productList = session.createQuery(" select productName, count(productName)  from Product where sell='1' group by productName Order by count(productName) desc").setCacheable(true).list();
            tx.commit();
            for(Object []row:productList)
            {Product p= new Product();
                p.setProductName(row[0].toString());
                p.setTotalsold(Integer.valueOf(row[1].toString()));
               list.add(p);

            }

            return list;
        }catch(HibernateException e) {
            if(tx!=null)tx.rollback();
            return null;
        }
    }
}
