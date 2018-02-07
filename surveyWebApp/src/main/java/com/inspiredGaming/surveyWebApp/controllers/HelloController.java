/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.controllers;

import com.inspiredGaming.surveyWebApp.models.HelloLog;
import com.inspiredGaming.surveyWebApp.models.HelloMessage;
import com.inspiredGaming.surveyWebApp.models.RespondentAnswers;
import com.inspiredGaming.surveyWebApp.models.Respondents;
import com.inspiredGaming.surveyWebApp.models.dao.HelloLogDao;
import com.inspiredGaming.surveyWebApp.models.dao.RespondentAnswersDao;
import com.inspiredGaming.surveyWebApp.models.dao.RespondentsDao;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Levi
 */
@Controller
public class HelloController {
    
    @Autowired
    private HelloLogDao helloLogDao;
    
    @Autowired
    private RespondentsDao respondentDao;
    
    @Autowired
    private RespondentAnswersDao respondentAnswerDao;
    
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloForm()
    {
        return "helloform";
    }
    
    @RequestMapping(value = "/respondent", method = RequestMethod.GET)
    public String respondentForm()
    {
        return "survey";
    }
    
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    //@ResponseBody //just for passing a string instead of a template
    public String hello(HttpServletRequest request, Model model)
    {
        String name = request.getParameter("name");
        
        //handle null values
        if(name==null)
        {
            name = "world";
        }
        
        HelloLog log = new HelloLog(name);
        helloLogDao.save(log);
        
        model.addAttribute("message", HelloMessage.getMessage(name));
        model.addAttribute("title","Helooo Spring");
        
        return "hello";//"<h1>"+HelloMessage.getMessage(name)+"</h1>";
    }
    
    @RequestMapping(value = "/respondent", method = RequestMethod.POST)
    //@ResponseBody //just for passing a string instead of a template
    public String respondent(HttpServletRequest request, Model model)
    {
       
        //array to hold answers
        ArrayList<String> answers = new ArrayList<String>();
        
        //Placeholder- place values into HTML file during generation.
        int numberOfQuestions = 7;      
        int survey_id = 1;              
        
                
        //get all values
        for(int i = 1;i<numberOfQuestions+1;i++)
        {
            answers.add(request.getParameter("q"+i));
        }
        
        System.out.println("answersList is equal to "+request.getParameter("answersList"));
        
        //print all values
        System.out.println(answers.toString());
        
        System.out.println(request.getParameter("answersDict"));
        
        //handle emply values
        if(answers.size()==0)
        {
            answers.add("world");
        }
        
        Respondents log = new Respondents();
        respondentDao.save(log);
        
        //add all answers to database:
        for(int i = 0 ; i<answers.size();i++)
        {
            RespondentAnswers answerLog = new RespondentAnswers(Integer.parseInt(answers.get(i)),log.getRespondentId(),"");
            respondentAnswerDao.save(answerLog);
        }        
        
        model.addAttribute("message", HelloMessage.getMessage(answers.get(0)+answers.get(1)));
        model.addAttribute("title","Helooo Spring");
        
        return "hello";//"<h1>"+HelloMessage.getMessage(name)+"</h1>";
    }
        
}
