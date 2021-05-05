package com.taorusb.consolecrunduseshibernate.repository.impl;

import com.taorusb.consolecrunduseshibernate.HibernateUtil;
import com.taorusb.consolecrunduseshibernate.model.Post;
import com.taorusb.consolecrunduseshibernate.repository.PostRepository;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.*;

public class PostRepositoryImpl implements PostRepository {

    private HibernateUtil connectionSupplier;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    private final static SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd/MM/yyyy");
    private static PostRepositoryImpl instance;

    private PostRepositoryImpl() {
    }

    public void setConnectionSupplier(HibernateUtil connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    public static PostRepositoryImpl getInstance() {

        if(instance == null) {
            instance = new PostRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Post getById(Long id) {

        Post post;
        sessionFactory = connectionSupplier.getSessionFactory();
        try {
            session = sessionFactory.openSession();

            post = session.get(Post.class, id);

            if (post == null) {
                throw new ObjectNotFoundException(id, "Entity not found.");
            }
        } finally {
            session.close();
        }

        return post;
    }

    @Override
    public void deleteById(Long id) {

        int result;
        sessionFactory = connectionSupplier.getSessionFactory();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query query = session.createQuery("delete from Post where id = :id");
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
    public List<Post> findAll() {

        List<Post> posts;
        sessionFactory = connectionSupplier.getSessionFactory();
        try {
            session = sessionFactory.openSession();

            posts = session.createQuery("from Post order by id").list();
        } finally {
            session.close();
        }

        return posts;
    }

    @Override
    public Post save(Post entity) {

        sessionFactory = connectionSupplier.getSessionFactory();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            entity.setCreated(dateFormat.format(new Date()));
            session.save(entity);

            transaction.commit();
        } finally {
            session.close();
        }

        return entity;
    }

    @Override
    public Post update(Post entity) {

        String upDate = dateFormat.format(new Date());
        Post updatableEntity;
        sessionFactory = connectionSupplier.getSessionFactory();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            updatableEntity = session.get(Post.class, entity.getId());

            if (updatableEntity == null) {
                throw new ObjectNotFoundException(entity.getId(), "Entity not found.");
            }

            updatableEntity.setUpdated(upDate);
            updatableEntity.setContent(entity.getContent());

            transaction.commit();
        } finally {
            session.close();
        }

        return updatableEntity;
    }
}