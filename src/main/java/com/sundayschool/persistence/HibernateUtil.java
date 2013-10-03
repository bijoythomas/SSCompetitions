package com.sundayschool.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.util.Properties;

public class HibernateUtil
{
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static synchronized SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.load(HibernateUtil.class.getClassLoader().getResourceAsStream("db.properties"));
            String username = properties.getProperty("db.user");
            String password = properties.getProperty("db.passwd");
            String schema = properties.getProperty("db.schema");
            String dbHost = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
            String dbPort = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
            String dbUrl = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + schema;
            configuration.setProperty("hibernate.connection.url", dbUrl);
            configuration.setProperty("hibernate.connection.username", username);
            configuration.setProperty("hibernate.connection.password", password);
            return configuration.configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
