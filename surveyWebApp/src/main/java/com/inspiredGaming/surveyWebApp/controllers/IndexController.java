/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Owain
 */
@RestController
public class IndexController  implements ErrorController
{
    private static final String PATH = "/error";
    
    @RequestMapping(value = PATH)
    public ModelAndView error(){
        System.out.println("Handling Error 1");

        //send the user to landing page, which will check for logged in session.
        return new ModelAndView("redirect:/landing");
    }
    @Override
    public String getErrorPath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
