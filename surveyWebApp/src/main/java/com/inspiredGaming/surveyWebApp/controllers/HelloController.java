/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.controllers;

import com.inspiredGaming.surveyWebApp.Email;
import com.inspiredGaming.surveyWebApp.HtmlBuilder;
import com.inspiredGaming.surveyWebApp.XMLParser.QuestionsParser;
import com.inspiredGaming.surveyWebApp.models.Answers;
import com.inspiredGaming.surveyWebApp.models.HelloLog;
import com.inspiredGaming.surveyWebApp.models.HelloMessage;
import com.inspiredGaming.surveyWebApp.models.Questions;
import com.inspiredGaming.surveyWebApp.models.RespondentAnswers;
import com.inspiredGaming.surveyWebApp.models.Respondents;
import com.inspiredGaming.surveyWebApp.models.StaffEmails;
import com.inspiredGaming.surveyWebApp.models.SurveyKeys;
import com.inspiredGaming.surveyWebApp.models.Surveys;
import com.inspiredGaming.surveyWebApp.models.dao.AnswersDao;
import com.inspiredGaming.surveyWebApp.models.dao.HelloLogDao;
import com.inspiredGaming.surveyWebApp.models.dao.QuestionsDao;
import com.inspiredGaming.surveyWebApp.models.dao.RespondentAnswersDao;
import com.inspiredGaming.surveyWebApp.models.dao.RespondentsDao;
import com.inspiredGaming.surveyWebApp.models.dao.StaffEmailsDao;
import com.inspiredGaming.surveyWebApp.models.dao.SurveyKeysDao;
import com.inspiredGaming.surveyWebApp.models.dao.SurveysDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

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
    
    @Autowired
    private QuestionsDao questionsDao;
    
    @Autowired
    private AnswersDao answersDao;
    
    @Autowired
    private StaffEmailsDao staffEmailsDao;
    
    @Autowired
    private SurveyKeysDao surveyKeysDao;
    
    @Autowired
    private SurveysDao surveysDao;
    //Dependancy injection used by Spring
    //Needed because the interface instance is not manually coded   
    
    
    @RequestMapping(value = "/surveyBuilder", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String surveyBuilderForm()
    {
        
        return "ourSurveyBuilder";
    }
    
    
    @RequestMapping(value = "/surveyBuilder", method = RequestMethod.POST)
    public String surveyBuilder(HttpServletRequest request, Model model)
    {
        try {
            //gets the value from the textbox
            //System.out.println(request.getParameter("mytextform"));
            
            QuestionsParser qp = new QuestionsParser();
            String ddd = qp.parse("src\\main\\resources\\XML\\questions.xml");
            System.out.println(ddd);
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ourSurveyBuilder";
    }
    
    
    
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
    
    @RequestMapping(value = "/uploademails", method = RequestMethod.GET)
    public String uploademailsForm(HttpServletRequest request, Model model)
    {
        
        List<Surveys> surveys = surveysDao.findAll();
        
        String selectListHtml = "";
        
        //add all surveys to the form
        for(int i = 0;i<surveys.size();i++)
        {
           
            selectListHtml += "<option value = \""+surveys.get(i).getSurveyId()+"\">"+surveys.get(i).getSurveyName()+"</option>";
        }
        
        model.addAttribute("selectList", selectListHtml);
        
        return "uploademails";
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
   
    
    
    @RequestMapping(value = "/survey", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String survey(HttpServletRequest request, Model model)
    {
        //String surveyId = request.getParameter("survey");
        
        int surveyId = surveyKeysDao.findByKeyId(request.getParameter("key")).getSurveyId();
        
        //check key to see if survey has been completed
        boolean expired = surveyKeysDao.findByKeyId(request.getParameter("key")).getExpired();
        
        if(!expired)
        {
            //queries database for a list of all rows in Questions
            List<Questions> questionList = questionsDao.findBySurveyId(surveyId);

            //build html for survey
            HtmlBuilder htmlDoc = new HtmlBuilder();

            //generates html <h1> tags for each row
            for(int i = 0;i<questionList.size();i++)
            {
                    //get answers for all questions
                    List<Answers> answerList = answersDao.findByQuestionId(questionList.get(i).getQuestionId());

                    //add question & answers to html
                    htmlDoc.addQuestion(questionList.get(i), answerList);
            }       

            model.addAttribute("surveyName", "Survey :"+surveyId);
            model.addAttribute("form", htmlDoc.getSurveyHTML());

            return "hello";
        }
        else
        {
            return "surveycompleted";
        }
    }
    
    @RequestMapping(value = "/survey", method = RequestMethod.POST)
    public String surveySubmit(HttpServletRequest request, Model model)
    {
        //get survey id
        String surveyId = request.getParameter("survey");
        
        //get list of expected questions
        List<Questions> questionList = questionsDao.findBySurveyId(Integer.parseInt(surveyId));
        
        //get all parameter values
        Map<String, String[]> map = request.getParameterMap();
        
        //add respondent to table
        Respondents log = new Respondents();
        respondentDao.save(log);
        
        //add answers to database
        for(int i = 0; i<questionList.size();i++)
        {
            //check type of question
            int questionTypeId = questionList.get(i).getQuestionTypeId();
            
            //if question is text, need to search for answerId & insert value param into answerText field
            if(questionTypeId==4)
            {
                List<Answers> textAnswer = answersDao.findByQuestionId(questionList.get(i).getQuestionId());
                int answerId = textAnswer.get(0).getAnswerId();                
                
                RespondentAnswers answerLog = new RespondentAnswers(answerId,log.getRespondentId(),map.get(""+questionList.get(i).getQuestionId())[0]);
                respondentAnswerDao.save(answerLog);
            }
            else
            {
                RespondentAnswers answerLog = new RespondentAnswers(Integer.parseInt(map.get(""+questionList.get(i).getQuestionId())[0]),log.getRespondentId(),"");
                respondentAnswerDao.save(answerLog);
            }
            
        }
        
        //make survey expired
        SurveyKeys key = surveyKeysDao.findByKeyId(request.getParameter("key"));
        key.setExpired(true);
        surveyKeysDao.save(key);
        
        return "hello";
    }
    
    @RequestMapping(value = "/uploademails", method = RequestMethod.POST)
    public String uploadEmailsSubmit(HttpServletRequest request, Model model)
    {
        //get survey id
        String emails = request.getParameter("emails");
        
        String[] emailList = emails.split("\n");
        
        //save all new emails
        for(int i = 0; i<emailList.length; i++)
        {
            emailList[i] = emailList[i].replaceAll("[\r|\n|\\s]","");
            StaffEmails email = new StaffEmails(emailList[i]);
            
            //test - send survey to listed emails and record unique key
            try {
                System.out.println("gets here");
                SurveyKeys key = new SurveyKeys(Integer.parseInt(request.getParameter("surveys")));
                surveyKeysDao.save(key);
                Email emailObj = new Email(emailList[i],"http://localhost:8080/survey?key="+key.getKeyId());
                emailObj.send();
            } catch (MessagingException ex) {
                Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            staffEmailsDao.save(email);
        }
        
        return "uploademails";
    }
        
}
