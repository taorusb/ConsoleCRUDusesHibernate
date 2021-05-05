package com.taorusb.consolecrunduseshibernate.repository.impl;

import com.taorusb.consolecrunduseshibernate.HibernateUtil;
import com.taorusb.consolecrunduseshibernate.model.Region;
import com.taorusb.consolecrunduseshibernate.repository.RegionRepository;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RegionRepositoryImpl implements RegionRepository {

    private HibernateUtil connectionSupplier;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    private static RegionRepositoryImpl instance;

    private RegionRepositoryImpl() {
    }

    public void setConnectionSupplier(HibernateUtil connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    public static RegionRepositoryImpl getInstance() {

        if(instance == null) {
            instance = new RegionRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Region getById(Long id) {

        Region region;
        sessionFactory = connectionSupplier.getSessionFactory();

        try {
            session = sessionFactory.openSession();

            region = session.get(Region.class, id);

            if (region == null) {
                throw new ObjectNotFoundException(id, "Entity not found.");
            }
        } finally {
            session.close();
        }

        return region;
    }

    @Override
    public void deleteById(Long id) {

        int result;
        sessionFactory = connectionSupplier.getSessionFactory();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query query = session.createQuery("delete from Region where id = :id");
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
    public List<Region> findAll() {

        List<Region> regions;
        sessionFactory = connectionSupplier.getSessionFactory();
        try {
            session = sessionFactory.openSession();

            regions = session.createQuery("from Region order by id").list();
        } finally {
            session.close();
        }

        return regions;
    }

    @Override
    public Region save(Region entity) {

        sessionFactory = connectionSupplier.getSessionFactory();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.save(entity);

            transaction.commit();
        } finally {
            session.close();
        }

        return entity;
    }

    @Override
    public Region update(Region entity) {

        Region updatableEntity;
        sessionFactory = connectionSupplier.getSessionFactory();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            updatableEntity = session.get(Region.class, entity.getId());
            if (updatableEntity == null) {
                throw new ObjectNotFoundException(entity.getId(), "Entity not found.");
            }

            updatableEntity.setName(entity.getName());

            transaction.commit();
        } finally {
            session.close();
        }

        return entity;
    }
}