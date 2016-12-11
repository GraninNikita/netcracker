package com.netcracker.controllers;

import com.netcracker.entities.MeetingsEntity;
import com.netcracker.entities.UsersEntity;
import com.netcracker.orm.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.keycloak.KeycloakPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
        MeetingsController meetingsController = new MeetingsController();
        List usersList = userController.getAll();
        List meetingsList = meetingsController.getAll();
        model.addAttribute("usersList", usersList);
        model.addAttribute("meetingsList", meetingsList);

        // adding user to our db
        if (userController.getUsersByNameAndEmail(nameUser.split(" ")[0], nameUser.split(" ")[1], loginUser).size() == 0) {
            UsersEntity usersEntity = new UsersEntity();
            usersEntity.setFirstName(nameUser.split(" ")[0]);
            usersEntity.setLastName(nameUser.split(" ")[1]);
            usersEntity.setLogin(loginUser);
            usersEntity.setInfo("");
            usersEntity.setParentUserId(null);
            userController.add(usersEntity);
        }

        model.addAttribute("user", nameUser);
        return "dashboard";
    }

    @RequestMapping(value = "/profile/{name}", method = RequestMethod.GET)
    public String handleProfile(Model model, @PathVariable String name) {
        ContactsController contactsController = new ContactsController();
        List contactsList = contactsController.getAll();
        //List contactsList = contactsController.getContactsForUser(name); +++
        //List contactsList = contactsController.getContactsForUser("Nikita " + "Granin");
        model.addAttribute("contactsList", contactsList);
        model.addAttribute("name", name);
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
            @RequestParam String notificationTime
    ) {
        Logger logger = Logger.getLogger(MappingController.class);
        logger.info("Start adding");
        logger.info("event name: " + nameEvent);
        logger.info("start time: " + startTime);
        logger.info("end time: " + endTime);
        logger.info("summary: " + summary);
        logger.info("place: " + place);
        logger.info("place: " + notificationTime);

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
        meeting.setAdminId(1L);
        meeting.setState(true);

        session.save(meeting);
        session.getTransaction().commit();
        return "dashboard";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String handleLogout(HttpServletRequest req) throws ServletException {
        req.logout();
        return "welcome";
    }

}
