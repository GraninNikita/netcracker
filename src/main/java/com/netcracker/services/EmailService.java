package com.netcracker.services;

import java.util.Date;
import java.util.Properties;
import java.io.*;
import java.net.InetAddress;
import javax.mail.*;

import javax.mail.internet.*;

import com.sun.mail.smtp.*;


/**
 * Created by Nick on 24.11.2016.
 */

public class EmailService implements NotificationService {

    private final String recipient = "main.granin@gmail.com"; // кому отправить
    private final String recipientPassword = "mSMGYfFY"; // кому отправить

    private String subjectStr = ""; // текст сообщения

    private Message message;
    private SMTPTransport transport;


    public void init() throws MessagingException {
        Properties props = System.getProperties();
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.auth", "true");

        Session session = Session.getInstance(props, null);
        message = new MimeMessage(session);
        transport = (SMTPTransport) session.getTransport("smtps");
        transport.connect("smtp.gmail.com", recipient, recipientPassword);

    }

    public void getContacts() {

    }

    public void prepareData() throws MessagingException {
        message.setFrom(new InternetAddress("notification.netcracker@gmail.com"));

        // addRecipients - в цикле для всех контактов
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipient, false));

        message.setSubject(subjectStr);
        message.setText("Med vennlig hilsennTov Are Jacobsen");
        message.setHeader("X-Mailer", "Tov Are's program");
        message.setSentDate(new Date()); // ЗДЕСЬ НУЖНО УКАЗАТЬ ВРЕМЯ КОГДА ПРИСЫЛАТЬ СООБЩЕНИЕ
    }


    public void notificate() throws MessagingException {

        transport.sendMessage(message, message.getAllRecipients());
        System.out.println("Response: " + transport.getLastServerResponse());
    }

    public void close() throws MessagingException {
        transport.close();
    }
}
