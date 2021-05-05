package com.taorusb.consolecrunduseshibernate.repository.impl;

import com.taorusb.consolecrunduseshibernate.HibernateUtil;
import com.taorusb.consolecrunduseshibernate.model.Region;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RegionRepositoryImplTest {

    Region region = new Region(1L);
    RegionRepositoryImpl regionRepository = RegionRepositoryImpl.getInstance();
    HibernateUtil connectionSupplier = mock(HibernateUtil.class);
    SessionFactory sessionFactory = mock(SessionFactory.class);
    Session session = mock(Session.class);
    Query query = mock(Query.class);
    Transaction transaction = mock(Transaction.class);

    @Before
    public void setUp() throws Exception {
        regionRepository.setConnectionSupplier(connectionSupplier);
        when(connectionSupplier.getSessionFactory()).thenReturn(sessionFactory);
        when(sessionFactory.openSession()).thenReturn(session);
    }

    @Test
    public void getById() {
        when(session.get(same(Region.class), anyLong())).thenReturn(null);
        assertThrows(ObjectNotFoundException.class, () -> regionRepository.getById(1L));

        when(session.get(same(Region.class), anyLong())).thenReturn(region);
        assertEquals(region, regionRepository.getById(1L));
    }

    @Test
    public void deleteById() {
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(-1);
        assertThrows(ObjectNotFoundException.class, () -> regionRepository.deleteById(1L));
    }

    @Test
    public void findAll() {
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.list()).thenReturn(List.of(new Region(1L), new Region(2L), new Region(3L)));
        assertEquals(3, regionRepository.findAll().size());
    }

    @Test
    public void save() {
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.save(region)).thenReturn(1L);
        assertEquals(region, regionRepository.save(region));
    }

    @Test
    public void update() {
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(same(Region.class), anyLong())).thenReturn(null);
        assertThrows(ObjectNotFoundException.class, () -> regionRepository.update(region));

        when(session.get(same(Region.class), anyLong())).thenReturn(region);
        assertEquals(region, regionRepository.update(region));
    }
}