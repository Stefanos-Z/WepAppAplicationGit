/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.controllers;

import com.inspiredGaming.surveyWebApp.models.Sessions;
import com.inspiredGaming.surveyWebApp.models.Users;
import com.inspiredGaming.surveyWebApp.models.dao.SessionsDao;
import com.inspiredGaming.surveyWebApp.models.dao.UsersDao;
import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author oneZt
 */
@Controller
public class LoginControler {
    
    @Autowired
    private UsersDao usersDao;
    
    
    @Autowired
    private SessionsDao sessionsDao;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request, HttpServletResponse response)
    {
        deleteCookiesOnPage(request, response);
        return "sLogin";
    }
    
    
    
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPage(HttpServletResponse responce, HttpServletRequest request, Model model)
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        responce.setContentType("text/html");
        deleteCookiesOnPage(request, responce);
//        Users newUser = new Users(username, password, "malakas@deryneia.com", "0998789733");
//        usersDao.save(newUser); //saves login details to the database
        Users user = usersDao.findByUsername(username);

        //EncryptPasswordWithPBKDF2WithHmacSHA1 afddas = new EncryptPasswordWithPBKDF2WithHmacSHA1();
        String databasePassword = "";
        
        if (user!=null)
        {
            databasePassword = user.getUserPassword();
            if(databasePassword.equals(password))
            {
                user.getUserId();
                Sessions s = new Sessions(user.getUserId());
                sessionsDao.save(s);
                Cookie myCookie = new Cookie(user.getUsername(), s.getSessionId());
                myCookie.setMaxAge(60*60);//sets the the lifespan of the cooki in seconds
                myCookie.setPath("/");
                
                responce.addCookie(myCookie);
                
                //direct user to appropriate site location
                if(user.getRole().equals("ADMINISTRATOR"))
                {
                    try {
                    responce.sendRedirect("/landingAdmin");
                    } catch (IOException ex) {
                        System.out.println("Error in redirect");
                    }
                    return "landingPageAdmin";
                }
                
                try {
                    responce.sendRedirect("/landing");
                } catch (IOException ex) {
                    System.out.println("Error in redirect");
                }
                return "landingPage";

            }
        }
        
        return "sLogin";
    }
    
    private void deleteCookiesOnPage(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] allCookies = request.getCookies();
        if (allCookies != null)
        {
            for(Cookie thisCookie: allCookies)
            {
                Cookie cookie = new Cookie(thisCookie.getName(), null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                
                response.addCookie(cookie);
            }
        }
    }
}
