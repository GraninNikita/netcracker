package com.netcracker.services;

import javax.mail.MessagingException;

/**
 * Created by Nick on 23.11.2016.
 */
public interface NotificationService {

    public void init() throws MessagingException;

    public void prepareData() throws MessagingException;

    public void getContacts();

    public void notificate() throws MessagingException;

    public void close() throws MessagingException;

}
