package com.netcracker.controllers;

import com.netcracker.entities.ContactsEntity;
import com.netcracker.entities.MeetingsEntity;
import com.netcracker.entities.UsersEntity;
import com.netcracker.services.EmailService;

import javax.mail.MessagingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nick on 23.11.2016.
 */
public class TestController {

    public static void main(String[] args) throws MessagingException {
//        EmailService emailService = new EmailService();
//        emailService.init();
        ContactsController contactsController = new ContactsController();
        List<UsersEntity> l = contactsController.getAll();

    }
}
