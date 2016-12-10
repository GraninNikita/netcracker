package com.netcracker.services;

import java.util.*;
import java.io.*;
import java.net.InetAddress;
import javax.mail.*;

import javax.mail.internet.*;

import com.netcracker.controllers.MeetingsController;
import com.netcracker.entities.ContactsEntity;
import com.netcracker.entities.MeetingsEntity;
import com.sun.mail.smtp.*;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * Created by Nick on 24.11.2016.
 */

public class EmailService implements NotificationService, Job {

    private final String recipient = "notification.netcracker@gmail.com"; // кому отправить
    private final String recipientPassword = "mSMGYfFY"; // кому отправить

    protected Message message;
    protected SMTPTransport transport;

    final static Logger logger = Logger.getLogger(EmailService.class);

    private List<ContactsEntity> contactsEntityList = new ArrayList<>();

    public void init() throws MessagingException {
        Properties props = System.getProperties();
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.auth", "true");

        Session session = Session.getInstance(props, null);
        message = new MimeMessage(session);
        transport = (SMTPTransport) session.getTransport("smtps");
        transport.connect("smtp.gmail.com", recipient, recipientPassword);

    }

    @Override
    public List<ContactsEntity> getData() {
        List<MeetingsEntity> upcomingMeetings = UpcomingMeetingsJob.getUpcomingMeetingsList();
        List<ContactsEntity> contactsToNotificate = new ArrayList<>();
        for (MeetingsEntity meeting : upcomingMeetings) {
            contactsToNotificate.addAll(MeetingsController.getContactsByMeetingId(meeting.getMeetingId()));
        }
        return contactsToNotificate;
    }


    @Override
    public void notificate(ContactsEntity contact, MeetingsEntity content, String subject) throws MessagingException {

        message.setFrom(new InternetAddress("notification.netcracker@gmail.com"));

        // здесь происходит добавление получателя из Contact.value
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(contact.getValue(), false));


        message.setSubject(subject);
        message.setText("Сообщение: " + content.getSummary());
        message.setHeader("X-Mailer", "Netcracker notification");
        message.setSentDate(new Date()); // ЗДЕСЬ НУЖНО УКАЗАТЬ ВРЕМЯ КОГДА ПРИСЫЛАТЬ СООБЩЕНИЕ

        transport.sendMessage(message, message.getAllRecipients());
        System.out.println("Response: " + transport.getLastServerResponse());
    }

    public void close() throws MessagingException {
        transport.close();
    }



    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        try {
            init();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        if (UpcomingMeetingsJob.getUpcomingMeetingsList().size() > 0) {
            MeetingsEntity firstMeeting = UpcomingMeetingsJob.getUpcomingMeetingsList().get(0);
            DateTime nowTime = new DateTime(); // current time
            DateTime meetingDateStart = new DateTime(firstMeeting.getDateStart());
            logger.info("Разница: " + (meetingDateStart.getMinuteOfDay() - nowTime.getMinuteOfDay()));
            if (firstMeeting.getState() && (Minutes.minutesBetween(meetingDateStart, nowTime).getMinutes() <= 1)) {
                MeetingsEntity meetingToNotificate = UpcomingMeetingsJob.getUpcomingMeetingsList().pop();
                List<ContactsEntity> contactsToNotificate = meetingToNotificate.getContacts();
                Hibernate.initialize(contactsToNotificate);

                for (ContactsEntity contact : contactsToNotificate) {
                    try {
                        logger.info("try to notificate in email Service");
                        notificate(contact, meetingToNotificate, "Test subject");
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    logger.info("Message was sent to " + contact.getValue());
                }
                logger.error("CHANGED STATE OF MEETING");
                MeetingsController.changeStateById(false, firstMeeting.getMeetingId());
            }

        }
        try {
            close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public List<ContactsEntity> getContactsEntityList() {
        return contactsEntityList;
    }

    public void setContactsEntityList(List<ContactsEntity> contactsEntityList) {
        this.contactsEntityList = contactsEntityList;
    }
}
