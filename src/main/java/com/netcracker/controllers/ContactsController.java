package com.netcracker.controllers;

import com.netcracker.entities.ContactsEntity;
import com.netcracker.entities.MeetingsEntity;
import com.netcracker.entities.UsersEntity;
import com.netcracker.orm.HibernateUtil;
import com.netcracker.services.UpcomingMeetingsJob;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 24.11.2016.
 */

public class ContactsController {

    public List<UsersEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("select usersEntities from ContactsEntity ");

        List<UsersEntity> usersEntity = new ArrayList<>() ;
        usersEntity = q.list();

        for (UsersEntity users: usersEntity) {
            System.out.println(users.getFirstName());

        }

        session.close();
        return usersEntity;
    }
}
