package com.netcracker.controllers;

import com.netcracker.entities.ContactsEntity;
import com.netcracker.entities.UsersEntity;
import com.netcracker.orm.HibernateUtil;
import org.apache.catalina.User;
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

    public List<ContactsEntity> getContactsForUser(String name){
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("select userId from UsersEntity where firstName = '" + firstName + "' AND " + "lastName = '" + lastName + "'");
        List<Long> ids = q.list();
        Query query = session.createQuery("from ContactsEntity where userId = " + ids.get(0));
        List<ContactsEntity> contactsList = query.list();
        session.close();
        return contactsList;
    }

    public static UsersEntity getUserByUserId(long userId){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from UsersEntity where userId = " + userId);
        List<UsersEntity> users = q.list();
        session.close();
        if (users == null) {
            return null;
        }
        return users.get(0);
    }
}
