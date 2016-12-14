package com.netcracker.controllers;

import com.netcracker.entities.ContactsEntity;
import com.netcracker.entities.MeetingsEntity;
import com.netcracker.orm.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        session.close();
        return list;
    }

    public static List<ContactsEntity> getContactsByMeetingId(long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from MeetingsEntity where meetingId = " + id);
        MeetingsEntity meeting = (MeetingsEntity) q.getSingleResult();
        List<ContactsEntity> list = meeting.getContacts();
        session.close();
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
    public static Set<MeetingsEntity> getByUserId(long id) {
        Logger logger = Logger.getLogger(MeetingsController.class);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from ContactsEntity where userId = "+id);
        List<ContactsEntity> userContacts = q.list();

        Set<MeetingsEntity> result = new HashSet<>();
        for (ContactsEntity contact: userContacts) {
            List<MeetingsEntity> meetings = contact.getMeetings();
            for (MeetingsEntity m: meetings) {
                result.add(m);
                logger.error("Название события: "+m.getName());
            }

        }

        session.close();
        return result;
    }
}
