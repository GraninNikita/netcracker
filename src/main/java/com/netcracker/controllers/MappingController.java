package com.netcracker.controllers;

import com.netcracker.entities.ContactsEntity;
import com.netcracker.entities.MeetingsEntity;
import com.netcracker.entities.UsersEntity;
import com.netcracker.orm.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.joda.time.DateTime;
import org.keycloak.KeycloakPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.util.*;

import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * Created by Nick on 02.11.2016.
 */
@Controller
public class MappingController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String test(@RequestParam("name") String param, Model model) {
        model.addAttribute("name", param);
        return "welcome";
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String start(ModelMap model, HttpServletRequest request) {
        String nameUser = ((KeycloakPrincipal) request.getUserPrincipal())
                .getKeycloakSecurityContext().getToken().getName();
        String loginUser = ((KeycloakPrincipal) request.getUserPrincipal())
                .getKeycloakSecurityContext().getToken().getEmail();
        UserController userController = new UserController();


        List usersList = userController.getAll();
        Set meetingsList;

        if (userController.getUsersByNameAndEmail(nameUser.split(" ")[0], nameUser.split(" ")[1], loginUser) == null) {
            UsersEntity usersEntity = new UsersEntity();
            usersEntity.setFirstName(nameUser.split(" ")[0]);
            usersEntity.setLastName(nameUser.split(" ")[1]);
            usersEntity.setLogin(loginUser);
            usersEntity.setInfo("");
            usersEntity.setParentUserId(null);
            userController.add(usersEntity);
            meetingsList = null;
        }else{
            long userId = userController.getUsersByNameAndEmail(nameUser.split(" ")[0], nameUser.split(" ")[1], loginUser).getUserId();
            meetingsList = MeetingsController.getByUserId(userId);
            meetingsList.addAll(MeetingsController.getByAdminId(userId));
        }

        model.addAttribute("user", nameUser);
        model.addAttribute("usersList", usersList);
        model.addAttribute("meetingsList", meetingsList);
        return "dashboard";
    }

    @RequestMapping(value = "/event/{eventName}_{eventId}", method = RequestMethod.GET)
    public String handleEvent(Model model, @PathVariable String eventName, @PathVariable long eventId) {
        List<ContactsEntity> contactsList = MeetingsController.getContactsByMeetingId(eventId);
        Logger logger = Logger.getLogger(MappingController.class);
        logger.error("contacts size : " + contactsList.size());
        logger.error("event id  : " + eventId);
        Map<String, String> usersAndContacts = new HashMap<String, String>();
        for (ContactsEntity contact : contactsList) {
            UsersEntity user = ContactsController.getUserByUserId(contact.getUserId());
            usersAndContacts.put(contact.getValue(), user.getFirstName() + user.getLastName());
        }
        UserController userController = new UserController();
        List<UsersEntity> users = userController.getAll();
        model.addAttribute("users", users);
        model.addAttribute("usersAndContacts", usersAndContacts);
        model.addAttribute("eventName", eventName);
        model.addAttribute("eventId", eventId);
        return "event";
    }

    @RequestMapping(value = "/profile/{name}", method = RequestMethod.GET)
    public String handleProfile(Model model, @PathVariable String name) {
        ContactsController contactsController = new ContactsController();
        List contactsList = contactsController.getContactsForUser(name);
        model.addAttribute("contactsList", contactsList);
        model.addAttribute("name", name);
        return "userprofile";
    }

    @RequestMapping(value = "/event/save", method = RequestMethod.POST)
    public String addingUserToEvent(Model model, @RequestParam String user, @RequestParam String eventId) {
        Logger logger = Logger.getLogger(MappingController.class);
        logger.error("User name : " + user);
        logger.error("Event id : " + eventId);
        MeetingsEntity meeting = MeetingsController.getMeetingById(Long.parseLong(eventId));
        ContactsController contactsController = new ContactsController();
        List<ContactsEntity> contactsList = contactsController.getContactsForUser(user);

        Session session1 = HibernateUtil.getSessionFactory().getCurrentSession();
        session1.beginTransaction();
        meeting.addContacts(contactsList);
        session1.merge(meeting);
        session1.getTransaction().commit();
        session1.close();
        return "event";
    }

    @RequestMapping(value = "/contact/save", method = RequestMethod.POST)
    public String handleProfile(Model model, @RequestParam String email, @RequestParam String name) {
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];
        Set<MeetingsEntity> meetingsByUser = MeetingsController.getByUserName(name);
        List<MeetingsEntity> listMeetingByUser = new ArrayList<>(meetingsByUser);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ContactsEntity contact = new ContactsEntity();
        Query q = session.createQuery("select userId from UsersEntity where firstName = '" + firstName + "' AND " + "lastName = '" + lastName + "'");
        List<Long> ids = q.list();
        contact.setUserId(ids.get(0));
        contact.setState(true);
        contact.setType("email");
        contact.setValue(email);
        session.save(contact);
        contact.setMeetings(listMeetingByUser);
        session.update(contact);
        session.getTransaction().commit();
        session.close();
        return "userprofile";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(
            @RequestParam String nameEvent,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam String summary,
            @RequestParam String place,
            @RequestParam String notificationTime,
            @RequestParam String user,
            HttpServletRequest request
    ) {
        String nameUser = ((KeycloakPrincipal) request.getUserPrincipal())
                .getKeycloakSecurityContext().getToken().getName();
        String loginUser = ((KeycloakPrincipal) request.getUserPrincipal())
                .getKeycloakSecurityContext().getToken().getEmail();
        UserController userController = new UserController();
        long userId = userController.getUsersByNameAndEmail(nameUser.split(" ")[0], nameUser.split(" ")[1], loginUser).getUserId();


        int startYear = Integer.parseInt(startTime.substring(0, 4)) - 1900;
        int startMonth = Integer.parseInt(startTime.substring(5, 7)) - 1;
        int startDay = Integer.parseInt(startTime.substring(8, 10));
        int startHour = Integer.parseInt(startTime.substring(11, 13));
        int startMinute = Integer.parseInt(startTime.substring(14, 16));

        Date startDate = new Date(startYear, startMonth, startDay, startHour, startMinute);

        int endYear = Integer.parseInt(endTime.substring(0, 4)) - 1900;
        int endMonth = Integer.parseInt(endTime.substring(5, 7)) - 1;
        int endDay = Integer.parseInt(endTime.substring(8, 10));
        int endHour = Integer.parseInt(endTime.substring(11, 13));
        int endMinute = Integer.parseInt(endTime.substring(14, 16));

        Date endDate = new Date(endYear, endMonth, endDay, endHour, endMinute);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        MeetingsEntity meeting = new MeetingsEntity();
        meeting.setName(nameEvent);
        meeting.setDateStart(startDate);
        meeting.setDateEnd(endDate);
        meeting.setSummary(summary);
        meeting.setPlace(place);
        //we don't know user ID
        meeting.setAdminId(userId);
        meeting.setState(true);
        meeting.setNotificationTime(Integer.parseInt(notificationTime));

        session.save(meeting);
        session.getTransaction().commit();
        session.close();
        addCOntactToNewMetting(meeting, user);
        return "dashboard";
    }

    public void addCOntactToNewMetting(MeetingsEntity meeting, String userName) {
        ContactsController contactsController = new ContactsController();
        List<ContactsEntity> contactsList = contactsController.getContactsForUser(userName);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        meeting.setContacts(contactsList);
        session.clear();
        session.update(meeting);
        session.getTransaction().commit();
        session.close();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String handleLogout(HttpServletRequest req) throws ServletException {
        req.logout();
        return "welcome";
    }

}
