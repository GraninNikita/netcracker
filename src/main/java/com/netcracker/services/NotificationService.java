package com.netcracker.services;

import com.netcracker.entities.ContactsEntity;
import com.netcracker.entities.MeetingsEntity;

import javax.mail.MessagingException;
import java.util.List;

/**
 * Created by Nick on 23.11.2016.
 */
public interface NotificationService {

    public void init() throws MessagingException;

    public List<ContactsEntity> getData();

    public void notificate(ContactsEntity contact, MeetingsEntity content, String subject) throws MessagingException;

    public void close() throws MessagingException;

}
