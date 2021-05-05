package com.taorusb.consolecrunduseshibernate.repository.impl;

import com.taorusb.consolecrunduseshibernate.HibernateUtil;
import com.taorusb.consolecrunduseshibernate.model.Post;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostRepositoryImplTest {

    Post post = new Post(1L);
    PostRepositoryImpl postRepository = PostRepositoryImpl.getInstance();
    HibernateUtil connectionSupplier = mock(HibernateUtil.class);
    SessionFactory sessionFactory = mock(SessionFactory.class);
    Session session = mock(Session.class);
    Query query = mock(Query.class);
    Transaction transaction = mock(Transaction.class);

    @Before
    public void setUp() throws Exception {
        postRepository.setConnectionSupplier(connectionSupplier);
        when(connectionSupplier.getSessionFactory()).thenReturn(sessionFactory);
        when(sessionFactory.openSession()).thenReturn(session);
    }

    @Test
    public void getById() {
        when(session.get(same(Post.class), anyLong())).thenReturn(null);
        assertThrows(ObjectNotFoundException.class, () -> postRepository.getById(1L));

        when(session.get(same(Post.class), anyLong())).thenReturn(post);
        assertEquals(post, postRepository.getById(1L));
    }

    @Test
    public void deleteById() {
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(-1);
        assertThrows(ObjectNotFoundException.class, () -> postRepository.deleteById(1L));
    }

    @Test
    public void findAll() {
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.list()).thenReturn(List.of(new Post(1L), new Post(2L), new Post(3L)));
        assertEquals(3, postRepository.findAll().size());
    }

    @Test
    public void save() {
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.save(post)).thenReturn(1L);
        assertEquals(post, postRepository.save(post));
    }

    @Test
    public void update() {
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(same(Post.class), anyLong())).thenReturn(null);
        assertThrows(ObjectNotFoundException.class, () -> postRepository.update(post));

        when(session.get(same(Post.class), anyLong())).thenReturn(post);
        assertEquals(post, postRepository.update(post));
    }
}