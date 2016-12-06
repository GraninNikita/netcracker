package com.netcracker.controllers;

import com.netcracker.entities.ContactsEntity;
import com.netcracker.entities.MeetingsEntity;
import com.netcracker.orm.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Nick on 16.11.2016.
 */
public class MeetingsController {

    EntityManager em;
    /*
    * Getting list of meetings order by date start asc
    * */
    public static List<MeetingsEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from MeetingsEntity order by dateStart asc ");
        List<MeetingsEntity> list = q.list();
//        session.close();
        return list;
    }

    public static List<ContactsEntity> getContactsByMeetingId(long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from MeetingsEntity where meetingId = " + id);
        MeetingsEntity meeting = (MeetingsEntity) q.getSingleResult();
        List<ContactsEntity> list = meeting.getContacts();
//        session.close();
        return list;
    }
    public static void changeStateById(boolean state, long id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from MeetingsEntity where meetingId = " + id);
        MeetingsEntity meeting = (MeetingsEntity) q.getSingleResult();
        meeting.setState(state);
        session.save(meeting);
        session.getTransaction().commit();
        session.close();
    }
}
