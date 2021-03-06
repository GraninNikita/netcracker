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

    public static void changeStateById(boolean state, long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from MeetingsEntity where meetingId = " + id);
        MeetingsEntity meeting = (MeetingsEntity) q.getSingleResult();
        meeting.setState(state);
        session.save(meeting);
        session.getTransaction().commit();
        session.close();
    }

    public static Set<MeetingsEntity> getByUserName(String name) {
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("select userId from UsersEntity where firstName = '" + firstName + "' AND " + "lastName = '" + lastName + "'");
        List<Long> ids = q.list();
        session.close();
        return getByUserId(ids.get(0));
    }


    public static Set<MeetingsEntity> getByUserId(long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from ContactsEntity where userId = " + id);
        List<ContactsEntity> userContacts = q.list();

        Set<MeetingsEntity> result = new HashSet<>();
        for (ContactsEntity contact : userContacts) {
            List<MeetingsEntity> meetings = contact.getMeetings();
            for (MeetingsEntity m : meetings) {
                result.add(m);
            }

        }

        session.close();
        return result;
    }

    public static MeetingsEntity getMeetingByNameAndParam(String name, String place, String summary) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from MeetingsEntity where name = '"
                + name + "' AND place = '" + place + "' AND summary = '" + summary +"'");
        MeetingsEntity meeting = (MeetingsEntity) q.getSingleResult();
        session.close();
        return meeting;
    }

    public static MeetingsEntity getMeetingById(long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from MeetingsEntity where meetingId = " + id);
        MeetingsEntity meeting = (MeetingsEntity) q.getSingleResult();
        session.close();
        return meeting;
    }

    public static List<MeetingsEntity> getByAdminIdAndState(long id, boolean state) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from MeetingsEntity where adminId = " + id + " and state ="+ state);
        List<MeetingsEntity> result =  q.list();
        session.close();
        return result;
    }

    public static Set getByUserIdAndState(long userId, boolean state) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from ContactsEntity where userId = " + userId);
        List<ContactsEntity> userContacts = q.list();

        Set<MeetingsEntity> result = new HashSet<>();
        for (ContactsEntity contact : userContacts) {
            List<MeetingsEntity> meetings = contact.getMeetings();
            for (MeetingsEntity m : meetings) {
                if (m.getState() == state)
                result.add(m);
            }

        }

        session.close();
        return result;
    }
}
