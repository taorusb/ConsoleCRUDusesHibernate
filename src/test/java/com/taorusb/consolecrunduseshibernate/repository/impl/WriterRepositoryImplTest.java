package com.taorusb.consolecrunduseshibernate.repository.impl;

import com.taorusb.consolecrunduseshibernate.HibernateUtil;
import com.taorusb.consolecrunduseshibernate.model.Writer;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WriterRepositoryImplTest {

    Writer writer = new Writer(1L);
    WriterRepositoryImpl writerRepository = WriterRepositoryImpl.getInstance();
    HibernateUtil connectionSupplier = mock(HibernateUtil.class);
    SessionFactory sessionFactory = mock(SessionFactory.class);
    Session session = mock(Session.class);
    Query query = mock(Query.class);
    Transaction transaction = mock(Transaction.class);
    ScrollableResults results = mock(ScrollableResults.class);

    @Before
    public void setUp() throws Exception {
        writerRepository.setConnectionSupplier(connectionSupplier);
        when(connectionSupplier.getSessionFactory()).thenReturn(sessionFactory);
        when(sessionFactory.openSession()).thenReturn(session);
    }

    @Test
    public void getById() {
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);
        assertThrows(ObjectNotFoundException.class, () -> writerRepository.getById(1L));

        when(query.uniqueResult()).thenReturn(writer);
        assertEquals(writer, writerRepository.getById(1L));
    }

    @Test
    public void deleteById() {
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(-1);
        assertThrows(ObjectNotFoundException.class, () -> writerRepository.deleteById(1L));
    }

    @Test
    public void findAll() {
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.scroll(any())).thenReturn(results);
        when(results.next()).thenReturn(false);
        when(query.list()).thenReturn(List.of(new Writer(1L), new Writer(2L), new Writer(3L)));
        assertEquals(3, writerRepository.findAll().size());
    }

    @Test
    public void save() {
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.save(writer)).thenReturn(1L);
        assertEquals(writer, writerRepository.save(writer));
    }

    @Test
    public void update() {
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(same(Writer.class), anyLong())).thenReturn(null);
        assertThrows(ObjectNotFoundException.class, () -> writerRepository.update(writer));

        when(session.get(same(Writer.class), anyLong())).thenReturn(writer);
        assertEquals(writer, writerRepository.update(writer));
    }
}