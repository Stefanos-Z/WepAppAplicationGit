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
    
        int suveyId = Integer.parseInt(request.getParameter("surveyId"));
        String surveyName = surveysDao.findBySurveyId(suveyId).getSurveyName();
        List<Questions> questions = questionsDao.findBySurveyId(suveyId);
        
        String table = HtmlBuilderTable.getQuestionsTable(questions);
        String breadcrumbs = "<ul class=\"breadcrumb123\">"+
                        "<li><a href=\"/landing\">Home</a></li>"+ 
                        "<li><a href=\"/survey_results\">Survey Overview</a></li>"+
                        "<li><a href=\"#\">Survey: "+ surveyName +"</a></li>"+
                    "</ul>";
                        
            
            
            
            model.addAttribute("myBreadcrumbs", breadcrumbs);
        model.addAttribute("surveyTable", table);
        
        return "resultsSurveyList";
    }
    
    @RequestMapping(value = "/survey_results/survey_answers/freetext", method = RequestMethod.GET)
    public String listFreeTextAnswers(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException
    {
        //get question associated with question id.
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        Questions q = questionsDao.findByQuestionId(questionId);
        
        //Get answers associated with the free text.
        List<Answers> answer = answersDao.findByQuestions(q);
        List<RespondentAnswers> answers = respondentAnswerDao.findByAnswers(answer.get(0));
        
        //print the table
        String table = HtmlBuilderTable.getFreeTextTable(answers);
        model.addAttribute("surveyTable", table);
        
        return "resultsSurveyList";
    }
    
    @RequestMapping(value = "/survey_results/survey_answers/survey_stats", method = RequestMethod.GET)
    public String surveyStats(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException
    {
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        
        String s = "";
        Questions q = questionsDao.findByQuestionId(questionId);
        String surveyName = surveysDao.findBySurveyId(q.getSurveyId()).getSurveyName();
        int surveyId = q.getSurveyId();
        
        ArrayList<Integer> countArray = new ArrayList<Integer>();
        ArrayList<String> answersArray = new ArrayList<String>();
        
        s+="<div class=\"questions\" id=\"statsDiv\">";
        
        s+="<div id=\"titleDiv\"><h3>Q) "+q.getQuestion()+"</h3></div>";
            
        List<AnswerCount> ac = respondentAnswerDao.countAnswersByQuestion(questionId);
            
        for(int j = 0; j<ac.size(); j++)
        {
            //s+= "Answer: "+ac.get(j).getAnswer()+" Count: "+ac.get(j).getCount()+"<br></br>";
            System.out.println("Answer: "+ac.get(j).getAnswer()+" Count: "+ac.get(j).getCount());

            answersArray.add(ac.get(j).getAnswer());
            countArray.add(ac.get(j).getCount());

            s+="</div>";
        }
        String breadcrumbs = "<ul class=\"breadcrumb123\">"+
                        "<li><a href=\"/landing\">Home</a></li>"+ 
                        "<li><a href=\"/survey_results\">Survey Overview</a></li>"+
                        "<li><a href=\"/survey_results/survey_answers?surveyId="+surveyId+"\">Survey: "+ surveyName +"</a></li>"+
                        "<li><a href=\"#\">Question: "+ q.getQuestion() +"</a></li>"+
                    "</ul>";
                        
            
            
            
        model.addAttribute("myBreadcrumbs", breadcrumbs);
        
        model.addAttribute("stats", s);
 
        model.addAttribute("respondentDataArray", countArray);
        model.addAttribute("answersArray", answersArray);
        
        return "survey_stats";
    }
}
