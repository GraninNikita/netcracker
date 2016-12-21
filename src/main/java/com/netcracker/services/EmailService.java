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
import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.rest.lookups.v1.PhoneNumber;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * Created by Nick on 24.11.2016.
 */

public class EmailService implements NotificationService, Job {

    public static final String ACCOUNT_SID = "ACf455c7efa87be87e68bb05e15bc5fb52";
    public static final String AUTH_TOKEN = "6ea79dcf08d7c0957a823f21dab74332";

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


        message.setSubject(content.getName());

        DateTime dateStart = new DateTime(content.getDateStart());
        DateTime dateEnd = new DateTime(content.getDateEnd());

        message.setText("Здравствуйте, напоминаем, что событие \"" + content.getName() + "\"" + " состоится сегодня в " +
                dateStart.toString("HH:mm") + " и закончится в " + dateEnd.toString("HH:mm") + " в месте под названием \""
                + content.getPlace() + "\". Дополнительная информация: " + content.getSummary() + ".");
        message.setHeader("X-Mailer", "Netcracker notification");
        message.setSentDate(new Date()); // ЗДЕСЬ НУЖНО УКАЗАТЬ ВРЕМЯ КОГДА ПРИСЫЛАТЬ СООБЩЕНИЕ

        transport.sendMessage(message, message.getAllRecipients());
        System.out.println("Response: " + transport.getLastServerResponse());
    }

    public void notificateSms(ContactsEntity contact, MeetingsEntity content, String subject) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        DateTime dateStart = new DateTime(content.getDateStart());
        DateTime dateEnd = new DateTime(content.getDateEnd());
        com.twilio.rest.api.v2010.account.Message message;
        message = com.twilio.rest.api.v2010.account.Message.creator(new com.twilio.type.PhoneNumber(contact.getValue()),
                new com.twilio.type.PhoneNumber("+13475072188"),
                "Здравствуйте, напоминаем, что событие \"" + content.getName() + "\"" + " состоится сегодня в " +
                        dateStart.toString("HH:mm") + " и закончится в " + dateEnd.toString("HH:mm") + " в месте под названием \""
                        + content.getPlace() + "\". Дополнительная информация: " + content.getSummary() + ".")
                .create();

        Logger.getLogger(EmailService.class).error("Notificate by sms");
    }


    public void close() throws MessagingException {
        transport.close();
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger.getRootLogger().error("Execute email ");
        try {
            init();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        if (UpcomingMeetingsJob.getUpcomingMeetingsList().size() > 0) {
            MeetingsEntity firstMeeting = UpcomingMeetingsJob.getUpcomingMeetingsList().get(0);
            DateTime nowTime = new DateTime(); // current time
            DateTime meetingDateStart = new DateTime(firstMeeting.getDateStart());
            if (firstMeeting.getState() && (Seconds.secondsBetween(nowTime.plusHours(3), meetingDateStart).getSeconds() <= 30)) {
                MeetingsEntity meetingToNotificate = UpcomingMeetingsJob.getUpcomingMeetingsList().pop();
                List<ContactsEntity> contactsToNotificate = meetingToNotificate.getContacts();
                Hibernate.initialize(contactsToNotificate);

                for (ContactsEntity contact : contactsToNotificate) {
                    try {
                        if (contact.getType().contains("email")) {
                            logger.error("value " + contact.getValue());
                            logger.error("Meeting: " + meetingToNotificate.getName());
                            notificate(contact, meetingToNotificate, "Test subject");
                            logger.error("Message was sent to " + contact.getValue());
                        }
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }

                for (ContactsEntity contact : contactsToNotificate) {
                    try {
                        if (contact.getType().contains("sms")){
                            logger.error("value " + contact.getValue());
                            logger.error("Meeting: " + meetingToNotificate.getName());
                            notificateSms(contact, meetingToNotificate, "Test subject");
                            logger.error("Message was sent to " + contact.getValue());
                        }

                    } catch (TwilioException e) {
                        e.printStackTrace();
                    }
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
