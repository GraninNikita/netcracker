package com.netcracker.controllers;

import com.netcracker.entities.ContactsEntity;
import com.netcracker.orm.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Nick on 24.11.2016.
 */

public class ContactsController {

    public List<ContactsEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from ContactsEntity ");
        List<ContactsEntity> list = q.list();
        session.close();
        return list;
    }

}
