package com.sweet.demo;

/**
 * Created by Aritra Paul on 7/28/2018.
 */


        import org.hibernate.HibernateException;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;

public class Hibutil {
    private static SessionFactory sessionFactory;

    public static boolean setSessionFactory() {
        try {
            sessionFactory = new Configuration()
                    .configure()
                    .buildSessionFactory();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;

        }

        return true;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

