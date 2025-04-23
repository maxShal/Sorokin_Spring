package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{
    private static final SessionFactory session = buildSessionFactory();

    private static SessionFactory buildSessionFactory()
    {
        try{
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        }catch (Throwable ex){
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return session;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}
