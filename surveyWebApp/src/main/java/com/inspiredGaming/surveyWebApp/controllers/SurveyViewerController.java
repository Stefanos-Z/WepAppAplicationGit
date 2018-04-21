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
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
    
        //get the surveyid from the parameters
        int suveyId = Integer.parseInt(request.getParameter("surveyId"));
        String surveyName = surveysDao.findBySurveyId(suveyId).getSurveyName();
        List<Questions> questions = questionsDao.findBySurveyId(suveyId);
        
        //print questions in html table format
        String table = HtmlBuilderTable.getQuestionsTable(questions);
        
        
        //find all questions associated with survey.
        List<Questions> q = questionsDao.findBySurveyId(suveyId);
        
        //create lists to hold other lists
        ArrayList<ArrayList<String>> allAnswers = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<Integer>> allTotals = new ArrayList<ArrayList<Integer>>();
        
        
        for(int i = 0;i<q.size();i++)
        {
            ArrayList<Integer> countArray = new ArrayList<Integer>();
            ArrayList<String> answersArray = new ArrayList<String>();
        
            //get all answers associated with question
            List<AnswerCount> ac = respondentAnswerDao.countAnswersByQuestion(q.get(i).getQuestionId());
            
            for(int j = 0; j<ac.size(); j++)
            {
                //s+= "Answer: "+ac.get(j).getAnswer()+" Count: "+ac.get(j).getCount()+"<br></br>";
                //System.out.println("Answer: "+ac.get(j).getAnswer()+" Count: "+ac.get(j).getCount());

                answersArray.add(ac.get(j).getAnswer());
                countArray.add(ac.get(j).getCount());

            }
            
            allTotals.add(countArray);
            allAnswers.add(answersArray);
        }
 
        //add variables for pie chart to html
        model.addAttribute("respondentDataArray", allTotals);
        model.addAttribute("answersArray", allAnswers);
        
        
        String breadcrumbs = "<ul class=\"breadcrumb123\">"+
                        "<li><a href=\"/landing\">Home</a></li>"+ 
                        "<li><a href=\"/survey_results\">Survey Overview</a></li>"+
                        "<li><a href=\"#\">Survey: "+ surveyName +"</a></li>"+
                    "</ul>";
                        
            
            
            
        model.addAttribute("myBreadcrumbs", breadcrumbs);
        model.addAttribute("surveyTable", table);
        
        return "graphicalResultsPage";
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
    
    @RequestMapping(value = "/survey_results/survey_answers/monthly_stats", method = RequestMethod.GET)
    public String surveyStats(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException
    {
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        
        String s = "";
        Questions q = questionsDao.findByQuestionId(questionId);
        String surveyName = surveysDao.findBySurveyId(q.getSurveyId()).getSurveyName();
        int surveyId = q.getSurveyId();
        
        s+="<div class=\"questions\" id=\"statsDiv\">";
        
        s+="<div id=\"titleDiv\"><h3>Q) "+q.getQuestion()+"</h3></div>";
            
        //TO DO- INSERT GROUPED MONTH DATA
        //create lists to hold other lists
        ArrayList<ArrayList<String>> annualAnswersData = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<Integer>> annualTotalsData = new ArrayList<ArrayList<Integer>>();
        
        ArrayList<String> allMonths = new ArrayList<String>();
        
        //get all answers associated with question (over 12 month period)
        for(int i = 0; i<6; i++)
        {
            List<AnswerCount> acM;
            
            //handle according to question type, with score range presenting an average
            if(q.getQuestionTypeId()==3)
            {
                acM = respondentAnswerDao.avgAnswersByQuestionAndMonth(questionId,i);
            }
            else
            {
                acM = respondentAnswerDao.countAnswersByQuestionAndMonth(questionId,i);
            }
            
            ArrayList<Integer> countMonthArray = new ArrayList<Integer>();
            ArrayList<String> answersMonthArray = new ArrayList<String>();

            //add each count for month to array
            for(int k = 0; k<acM.size();k++)
            {
                answersMonthArray.add(acM.get(k).getAnswer());
                countMonthArray.add(acM.get(k).getCount());
            }

            annualAnswersData.add(answersMonthArray);
            annualTotalsData.add(countMonthArray);
            
            //get reference to month
            LocalDateTime ldt = LocalDateTime.now();
            int m = ldt.minusMonths(i).getMonthValue();
            int y = ldt.minusMonths(i).getYear();
            allMonths.add(getMonthText(m)+" "+y);
        }
        
        Collections.reverse(allMonths);
        Collections.reverse(annualAnswersData);
        Collections.reverse(annualTotalsData);
        
        s+="</div>";
        String breadcrumbs = "<ul class=\"breadcrumb123\">"+
                        "<li><a href=\"/landing\">Home</a></li>"+ 
                        "<li><a href=\"/survey_results\">Survey Overview</a></li>"+
                        "<li><a href=\"/survey_results/survey_answers?surveyId="+surveyId+"\">Survey: "+ surveyName +"</a></li>"+
                        "<li><a href=\"#\">6 Month Overview</a></li>"+
                    "</ul>";
        
        model.addAttribute("myBreadcrumbs", breadcrumbs);
        
        model.addAttribute("stats", s);
 
        model.addAttribute("respondentDataArray", annualTotalsData);
        model.addAttribute("answersArray", annualAnswersData.get(0));
        model.addAttribute("arrayOfMonthText", allMonths);
        
        return "survey_stats";
    }
    
    
    /**
     * Returns month as text string
     * @param month
     * @return 
     */
    private String getMonthText(int month)
    {
        String months[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        return months[month-1];
    }
    
}
