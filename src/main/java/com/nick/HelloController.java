package com.nick;

/**
 * Created by Nick on 31.10.2016.
 */
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Controller
public class HelloController{

//    @Bean
    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public void printHello(ModelMap model) {
        model.addAttribute("message", "Hello Spring MVC Framework!");
    }
}