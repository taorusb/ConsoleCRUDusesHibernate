package com.taorusb.consolecrunduseshibernate.repository.impl;

import com.taorusb.consolecrunduseshibernate.HibernateUtil;
import com.taorusb.consolecrunduseshibernate.model.Role;
import com.taorusb.consolecrunduseshibernate.model.Writer;
import com.taorusb.consolecrunduseshibernate.repository.*;
import org.hibernate.*;
import org.hibernate.query.Query;

import java.util.*;

public class WriterRepositoryImpl implements WriterRepository {

    private HibernateUtil connectionSupplier;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    private static WriterRepositoryImpl instance;

    private WriterRepositoryImpl() {
    }

    public void setConnectionSupplier(HibernateUtil connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    public static WriterRepositoryImpl getInstance() {

        if (instance == null) {
            instance = new WriterRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Writer getById(Long id) {

        Writer writer;
        sessionFactory = connectionSupplier.getSessionFactory();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query query = session.createQuery("from Writer w join fetch w.posts where w.id = :id");
            query.setParameter("id", id);
            Object result = query.uniqueResult();

            if (result == null) {
                throw new ObjectNotFoundException(id, "Entity not found");
            }
            writer = (Writer) result;
        } finally {
            session.close();
        }

        return writer;
    }

    @Override
    public void deleteById(Long id) {

        int result;
        sessionFactory = connectionSupplier.getSessionFactory();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query query = session.createQuery("delete from Writer where id = :id");
            query.setParameter("id", id);

            result = query.executeUpdate();
            if (result != 1) {
                throw new ObjectNotFoundException(id, "Entity not found");
            }

            transaction.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Writer> findAll() {

        List<Writer> writers;
        sessionFactory = connectionSupplier.getSessionFactory();
        try {
            session = sessionFactory.openSession();

            Query query = session.createQuery("from Writer order by id");

            writers = query.list();

            query = session.createQuery("select writer.id, count(content) from Post group by writer.id order by writer.id");
            ScrollableResults results = query.scroll(ScrollMode.SCROLL_INSENSITIVE);

            writers.forEach(writer -> {

                while (results.next()) {

                    long writerId = results.getLong(0);

                    if (writerId == writer.getId()) {
                        long postCount = results.getLong(1);
                        writer.setPostCount(postCount);
                        break;
                    } else if (results.previous()) {
                        break;
                    }
                }
            });
            results.close();
        } finally {
            session.close();
        }

        return writers;
    }

    @Override
    public Writer save(Writer entity) {

        sessionFactory = connectionSupplier.getSessionFactory();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            entity.setRole(Role.USER);
            session.save(entity);

            transaction.commit();
        } finally {
            session.close();
        }

        return entity;
    }

    @Override
    public Writer update(Writer entity) {

        Writer updatableEntity;
        sessionFactory = connectionSupplier.getSessionFactory();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            updatableEntity = session.get(Writer.class, entity.getId());
            if (updatableEntity == null) {
                throw new ObjectNotFoundException(entity.getId(), "Entity not found.");
            }

            updatableEntity.setFirstName(entity.getFirstName());
            updatableEntity.setLastName(entity.getLastName());
            updatableEntity.setRegion(entity.getRegion());

            transaction.commit();
        } finally {
            session.close();
        }

        return entity;
    }
}