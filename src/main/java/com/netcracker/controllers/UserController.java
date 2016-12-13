package com.netcracker.controllers;

import com.netcracker.entities.UsersEntity;
import com.netcracker.orm.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nick on 15.11.2016.
 */
public class UserController {

    public UsersEntity getUsersByNameAndEmail(String firstName, String lastName, String login) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from UsersEntity where firstName = '" + firstName + "' AND " + "lastName = '" + lastName + "' AND " + " login = '" + login+"'");
        UsersEntity user = (UsersEntity) q.list().get(0);
        session.close();
        return user;
    }

    public boolean add(UsersEntity user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        UsersEntity newUser = new UsersEntity();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setLogin(user.getLogin());
        newUser.setInfo(user.getInfo());
        newUser.setParentUserId(null);
        session.save(newUser);
        session.getTransaction().commit();
        return false;
    }

    public List<UsersEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from UsersEntity ");
        List<UsersEntity> list = q.list();
        session.close();
        return list;
    }

}
