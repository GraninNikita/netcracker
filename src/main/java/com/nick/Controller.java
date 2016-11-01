package com.nick;

import javax.ejb.Stateless;

/**
 * Created by Nick on 27.10.2016.
 */
@Stateless
public class Controller {

    public String met (){
        return "Hello, world!"+ System.currentTimeMillis();
    }

}

