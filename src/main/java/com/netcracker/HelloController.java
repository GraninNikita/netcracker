package com.netcracker;

/**
 * Created by Nick on 31.10.2016.
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController{

//    @Bean
    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public void printHello(ModelMap model) {
        model.addAttribute("message", "Hello Spring MVC Framework!");
    }

    public void handleLogout(HttpServletRequest req) throws ServletException {
        if (req.getParameter("logout") != null) {
            req.logout();
        }
    }

}