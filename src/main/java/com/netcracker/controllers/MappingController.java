package com.netcracker.controllers;

import com.netcracker.entities.UsersEntity;
import com.netcracker.orm.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.keycloak.KeycloakPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Nick on 02.11.2016.
 */
@Controller
public class MappingController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String test(@RequestParam("name") String param, Model model) {
        model.addAttribute("nam e", param);
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
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String handleLogout(HttpServletRequest req) throws ServletException {
        req.logout();
        return "welcome";
    }

}
