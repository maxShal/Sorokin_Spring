package org.example;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; // ✅ ЭТО ПРАВИЛЬНО


@Configuration
public class HibernateUtil
{
    //private static final SessionFactory session = buildSessionFactory();

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .setProperty("hibernate.current_session_context_class", "thread")
                .buildSessionFactory();
    }
/*
    public static SessionFactory getSessionFactory() {
        return session;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }*/

}
