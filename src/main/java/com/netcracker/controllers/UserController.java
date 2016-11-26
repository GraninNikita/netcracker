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

    public List<UsersEntity> getAll(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from UsersEntity ");
        List<UsersEntity> list = q.list();
        session.close();
        return list;
    }

}
