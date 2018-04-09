/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.controllers;

import com.inspiredGaming.surveyWebApp.Email;
import com.inspiredGaming.surveyWebApp.HtmlBuilderSurvey;
import com.inspiredGaming.surveyWebApp.HtmlBuilderTable;
import com.inspiredGaming.surveyWebApp.models.Answers;
import com.inspiredGaming.surveyWebApp.models.EmailGroups;
import com.inspiredGaming.surveyWebApp.models.HelloLog;
import com.inspiredGaming.surveyWebApp.models.HelloMessage;
import com.inspiredGaming.surveyWebApp.models.Questions;
import com.inspiredGaming.surveyWebApp.models.RespondentAnswers;
import com.inspiredGaming.surveyWebApp.models.Respondents;
import com.inspiredGaming.surveyWebApp.models.Sessions;
import com.inspiredGaming.surveyWebApp.models.StaffEmails;
import com.inspiredGaming.surveyWebApp.models.SurveyKeys;
import com.inspiredGaming.surveyWebApp.models.Surveys;
import com.inspiredGaming.surveyWebApp.models.Users;
import com.inspiredGaming.surveyWebApp.models.dao.AnswerCount;
import com.inspiredGaming.surveyWebApp.models.dao.AnswersDao;
import com.inspiredGaming.surveyWebApp.models.dao.EmailGroupsDao;
import com.inspiredGaming.surveyWebApp.models.dao.HelloLogDao;
import com.inspiredGaming.surveyWebApp.models.dao.QuestionsDao;
import com.inspiredGaming.surveyWebApp.models.dao.RespondentAnswersDao;
import com.inspiredGaming.surveyWebApp.models.dao.RespondentsDao;
import com.inspiredGaming.surveyWebApp.models.dao.SessionsDao;
import com.inspiredGaming.surveyWebApp.models.dao.StaffEmailsDao;
import com.inspiredGaming.surveyWebApp.models.dao.SurveyKeysDao;
import com.inspiredGaming.surveyWebApp.models.dao.SurveysDao;
import com.inspiredGaming.surveyWebApp.models.dao.UsersDao;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 *
 * @author Levi
 */
@Controller
public class SurveyViewerController {
    
    @Autowired
    private UsersDao usersDao;
    
    @Autowired
    private HelloLogDao helloLogDao;
    
    @Autowired
    private RespondentsDao respondentDao;
    
    @Autowired
    private RespondentAnswersDao respondentAnswerDao;
    
    @Autowired
    private QuestionsDao questionsDao;
    
    @Autowired
    private AnswersDao answersDao;
    
    @Autowired
    private SessionsDao sessionsDao;
    
    @Autowired
    private StaffEmailsDao staffEmailsDao;
    
    @Autowired
    private SurveyKeysDao surveyKeysDao;
    
    @Autowired
    private SurveysDao surveysDao;
    
    @Autowired
    private EmailGroupsDao emailGroupsDao;
    //Dependancy injection used by Spring
    //Needed because the interface instance is not manually coded   
    
    
    ServletContext servletContext;
    
    @RequestMapping(value = "/survey_results/survey_answers", method = RequestMethod.GET)
    public String DisplayResults(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException
    {
    
        
        return "resultsSurveyList";
    }
    
    @RequestMapping(value = "/survey_stats", method = RequestMethod.GET)
    public String EditSurvey(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException
    {
        int surveyid = Integer.parseInt(request.getParameter("surveyId"));
        
        List<Questions> questionList = questionsDao.findBySurveyId(surveyid);
        
        String s = "";
        
        ArrayList<Integer> countArray = new ArrayList<Integer>();
        ArrayList<String> answersArray = new ArrayList<String>();
        
        for(int i = 0; i<questionList.size();i++)
        {
            if(i == 0)
            {
                s+="<div class=\"questions\" id=\"question1\">";
            }
            else
            {
                s+="<div class=\"questions\">";
            }
            
            List<AnswerCount> ac = respondentAnswerDao.countAnswersByQuestion(questionList.get(i).getQuestionId());
            
            
            for(int j = 0; j<ac.size(); j++)
            {
                s+= "Answer: "+ac.get(j).getAnswer()+" Count: "+ac.get(j).getCount()+"<br></br>";
                System.out.println("Answer: "+ac.get(j).getAnswer()+" Count: "+ac.get(j).getCount());
                
                if(i==0){
                   
                    answersArray.add(ac.get(j).getAnswer());
                    countArray.add(ac.get(j).getCount());
                    
                }
                
            }
            s+="</div>";
        }
        
        model.addAttribute("stats", s);
//        int array[] = {1,2};
//        String array2[] = {"textA","textB"};
 
        model.addAttribute("respondentDataArray", countArray);
        model.addAttribute("answersArray", answersArray);
        
        return "survey_stats";
    }
}
