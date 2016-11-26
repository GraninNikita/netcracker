package com.netcracker.controllers;

import com.netcracker.entities.ContactsEntity;
import com.netcracker.entities.MeetingsEntity;
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

    public List<ContactsEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<MeetingsEntity> upComingMeetings = UpcomingMeetingsJob.getUpcomingMeetingsList();

        Query q = session.createQuery("from ContactsEntity ");
        List<ContactsEntity> list = q.list();
        for (ContactsEntity contacts : list) {
            for (MeetingsEntity m : contacts.getMeetings()) {
                System.out.println(m.getName());

            }
        }
        session.close();
        return list;
    }
}
