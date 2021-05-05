package com.taorusb.consolecrunduseshibernate;

import com.taorusb.consolecrunduseshibernate.model.Post;
import com.taorusb.consolecrunduseshibernate.model.Region;
import com.taorusb.consolecrunduseshibernate.model.Writer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static HibernateUtil instance;

    private HibernateUtil() {
    }

    public static HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .addAnnotatedClass(Writer.class)
                    .addAnnotatedClass(Post.class)
                    .addAnnotatedClass(Region.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }
}
