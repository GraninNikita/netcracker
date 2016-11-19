package com.netcracker.controllers;

import com.netcracker.entities.MeetingsEntity;
import com.netcracker.orm.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Nick on 16.11.2016.
 */
public class MeetingController {

    public List<MeetingsEntity> getAll(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from MeetingsEntity");
        List<MeetingsEntity> list = q.list();
        session.close();
        return list;
    }
}
