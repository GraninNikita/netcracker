package com.netcracker.controllers;

import com.netcracker.entities.MeetingsEntity;
import com.netcracker.entities.UsersEntity;
import com.netcracker.orm.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import org.keycloak.KeycloakPrincipal;

import org.joda.time.DateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        UserController userController = new UserController();
        MeetingsController meetingsController = new MeetingsController();
//        List usersList = userController.getAll();
//        List meetingsList = meetingsController.getAll();
//        model.addAttribute("usersList", usersList);
//        model.addAttribute("meetingsList", meetingsList);
        Logger logger = Logger.getRootLogger();
        logger.warn("its message in file");
        model.addAttribute("user", ((KeycloakPrincipal) request.getUserPrincipal())
                .getKeycloakSecurityContext().getToken().getName());
        return "dashboard";
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

        int startYear = Integer.parseInt(startTime.substring(0,4));
        int startMonth = Integer.parseInt(startTime.substring(5,7));
        int startDay = Integer.parseInt(startTime.substring(8,10));
        int startHour = Integer.parseInt(startTime.substring(11,13));
        int startMinute = Integer.parseInt(startTime.substring(14,16));

        //Date startDate = new Date(startYear,startMonth,startDay,startHour,startMinute);
        DateTime startDate = new DateTime(startYear,startMonth,startDay,startHour,startMinute);

        int endYear = Integer.parseInt(endTime.substring(0,4));
        int endMonth = Integer.parseInt(endTime.substring(5,7));
        int endDay = Integer.parseInt(endTime.substring(8,10));
        int endHour = Integer.parseInt(endTime.substring(11,13));
        int endMinute = Integer.parseInt(endTime.substring(14,16));

        //Date endDate = new Date(endYear,endMonth,endDay,endHour,endMinute);
        DateTime endDate = new DateTime(endYear,endMonth,endDay,endHour,endMinute);

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
        meeting.setNotificationTime(Integer.parseInt(notificationTime));

        session.save(meeting);
        session.getTransaction().commit();
        return "dashboard";
    }

/*    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String info,
            @RequestParam int userId
    ) {
        Logger logger = Logger.getLogger(MappingController.class);
        logger.info("Start adding");
        logger.info("first name: " + firstName);
        logger.info("Info: " + info);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.beginTransaction();
        UsersEntity users = new UsersEntity();
        users.setUserId(userId);
        users.setFirstName(firstName);
        users.setLastName(lastName);
        users.setInfo(info);
        users.setParentUserId(new Long(0));

        session.save(users);
        session.getTransaction().commit();
        return "dashboard";
    }*/

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String handleLogout(HttpServletRequest req) throws ServletException {
        req.logout();
        return "welcome";
    }

}
