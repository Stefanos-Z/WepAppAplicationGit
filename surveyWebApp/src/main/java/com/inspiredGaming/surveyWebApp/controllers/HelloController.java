/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.controllers;

import com.inspiredGaming.surveyWebApp.HtmlBuilder;
import com.inspiredGaming.surveyWebApp.models.Answers;
import com.inspiredGaming.surveyWebApp.models.HelloLog;
import com.inspiredGaming.surveyWebApp.models.HelloMessage;
import com.inspiredGaming.surveyWebApp.models.Questions;
import com.inspiredGaming.surveyWebApp.models.RespondentAnswers;
import com.inspiredGaming.surveyWebApp.models.Respondents;
import com.inspiredGaming.surveyWebApp.models.dao.AnswersDao;
import com.inspiredGaming.surveyWebApp.models.dao.HelloLogDao;
import com.inspiredGaming.surveyWebApp.models.dao.QuestionsDao;
import com.inspiredGaming.surveyWebApp.models.dao.RespondentAnswersDao;
import com.inspiredGaming.surveyWebApp.models.dao.RespondentsDao;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
    
    @Autowired
    private QuestionsDao questionsDao;
    
    @Autowired
    private AnswersDao answerDao;
    
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
        
        //test form
        String testHtml = "<input type=\"radio\" name=\"q8\" value=\"177\">Extremely unhappy</input>"
        +"<input type=\"radio\" name=\"q8\" value=\"178\">Fairly unhappy</input>"
        +"<input type=\"radio\" name=\"q8\" value=\"179\">Indifferent</input>"
        +"<input type=\"radio\" name=\"q8\" value=\"180\">Fairly happy</input>"
        +"<input type=\"radio\" name=\"q8\" value=\"181\">Extremely happy</input>"
        +"<input type=\"radio\" name=\"q9\" value=\"182\">Extremely unhappy</input>"
        +"<input type=\"radio\" name=\"q9\" value=\"183\">Fairly unhappy</input>"
        +"<input type=\"radio\" name=\"q9\" value=\"184\">Indifferent</input>"
        +"<input type=\"radio\" name=\"q9\" value=\"185\">Fairly happy</input>"
        +"<input type=\"radio\" name=\"q9\" value=\"186\">Extremely happy</input>"
        +"<input type=\"radio\" name=\"q10\" value=\"187\">Extremely unhappy</input>"
        +"<input type=\"radio\" name=\"q10\" value=\"188\">Fairly unhappy</input>"
        +"<input type=\"radio\" name=\"q10\" value=\"189\">Indifferent</input>"
        +"<input type=\"radio\" name=\"q10\" value=\"190\">Fairly happy</input>"
        +"<input type=\"radio\" name=\"q10\" value=\"191\">Extremely happy</input>"
        +"<input type=\"radio\" name=\"q11\" value=\"192\">Extremely unhappy</input>"
        +"<input type=\"radio\" name=\"q11\" value=\"193\">Fairly unhappy</input>"
        +"<input type=\"radio\" name=\"q11\" value=\"194\">Indifferent</input>"
        +"<input type=\"radio\" name=\"q11\" value=\"195\">Fairly happy</input>"
        +"<input type=\"radio\" name=\"q11\" value=\"196\">Extremely happy</input>"
        +"<input type=\"radio\" name=\"q12\" value=\"197\">Extremely unhappy</input>"
        +"<input type=\"radio\" name=\"q12\" value=\"198\">Fairly unhappy</input>"
        +"<input type=\"radio\" name=\"q12\" value=\"199\">Indifferent</input>"
        +"<input type=\"radio\" name=\"q12\" value=\"200\">Fairly happy</input>"
        +"<input type=\"radio\" name=\"q12\" value=\"201\">Extremely happy</input>"
        +"<input type=\"radio\" name=\"q13\" value=\"202\">Extremely unhappy</input>"
        +"<input type=\"radio\" name=\"q13\" value=\"203\">Fairly unhappy</input>"
        +"<input type=\"radio\" name=\"q13\" value=\"204\">Indifferent</input>"
        +"<input type=\"radio\" name=\"q13\" value=\"205\">Fairly happy</input>"
        +"<input type=\"radio\" name=\"q13\" value=\"206\">Extremely happy</input>"
        +"<input type=\"radio\" name=\"q14\" value=\"207\">Extremely unhappy</input>"
        +"<input type=\"radio\" name=\"q14\" value=\"208\">Fairly unhappy</input>"
        +"<input type=\"radio\" name=\"q14\" value=\"209\">Indifferent</input>"
        +"<input type=\"radio\" name=\"q14\" value=\"210\">Fairly happy</input>"
        +"<input type=\"radio\" name=\"q14\" value=\"211\">Extremely happy</input>"
        +"<input type=\"radio\" name=\"q15\" value=\"212\">Extremely unhappy</input>"
        +"<input type=\"radio\" name=\"q15\" value=\"213\">Fairly unhappy</input>"
        +"<input type=\"radio\" name=\"q15\" value=\"214\">Indifferent</input>"
        +"<input type=\"radio\" name=\"q15\" value=\"215\">Fairly happy</input>"
        +"<input type=\"radio\" name=\"q15\" value=\"216\">Extremely happy</input>"
        +"<input type=\"radio\" name=\"q16\" value=\"217\">Enter Text</input>";
        
        model.addAttribute("message", HelloMessage.getMessage(answers.get(0)+answers.get(1)));
        model.addAttribute("form", testHtml);
        model.addAttribute("title","Helooo Spring");
        
        
        
        
        return "hello";
    }
    
    @RequestMapping(value = "/survey", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String survey(HttpServletRequest request, Model model)
    {
        String surveyId = request.getParameter("survey");//?survey=2
        
        //queries database for a list of all rows in Questions
        List<Questions> questionList = questionsDao.findBySurveyId(Integer.parseInt(surveyId));
        
        String testHtml = "";
        
        HtmlBuilder htmlDoc = new HtmlBuilder();
        
        //generates html <h1> tags for each row
        for(int i = 0;i<questionList.size();i++)
        {
                List<Answers> answerList = answerDao.findByQuestionId(questionList.get(i).getQuestionId());
                
                //add question to html
                htmlDoc.addQuestion(questionList.get(i), answerList);
        }
        
        /*
        //generates html <h1> tags for each row
        for(int i = 0;i<questionList.size();i++)
        {
                testHtml = testHtml+"<h3>"+questionList.get(i).getQuestion()+"<h3><br></br>";
                List<Answers> answerList = answerDao.findByQuestionId(questionList.get(i).getQuestionId());
                for(int j = 0;j<answerList.size();j++)
                {
                    testHtml = testHtml+"<input type=\"radio\" name=\"q6\" value=\"167\">"+answerList.get(j).getAnswer()+"</input>";
                }
                testHtml+= "<br></br>";
        }*/
        
        System.out.println(htmlDoc.getSurveyHTML());
        
        model.addAttribute("surveyName", "Survey :"+surveyId);
        model.addAttribute("form", htmlDoc.getSurveyHTML());
        
        return "hello";
    }
        
}
