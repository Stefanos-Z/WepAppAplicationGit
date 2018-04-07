/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.controllers;

import com.inspiredGaming.surveyWebApp.models.Answers;
import com.inspiredGaming.surveyWebApp.models.Questions;
import com.inspiredGaming.surveyWebApp.models.Respondents;
import com.inspiredGaming.surveyWebApp.models.Sessions;
import com.inspiredGaming.surveyWebApp.models.Surveys;
import com.inspiredGaming.surveyWebApp.models.Users;
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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Owain
 */
@Controller
public class EditSurveyController {
    
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
    
    
    @RequestMapping(value = "/surveyEditor", method = RequestMethod.GET)
    public String EditSurvey(HttpServletResponse response, HttpServletRequest request, Model model)
    {
        //check user has appropriate permissions
        if(!checkValidation(request,"SURVEYOR")){
            return "sLogin";
        }
        
        int surveyId  = Integer.parseInt(request.getParameter("surveyId"));
        
        List<Respondents> respondentList = respondentDao.findBySurveyId(surveyId);
        
        //make sure survey is not associated with any respondents
        if(respondentList.size()>0)
        {
            return "errorpage";
        }
        
        Surveys survey = surveysDao.findBySurveyId(surveyId);
        List<Questions> questionsArray = questionsDao.findBySurveyId(surveyId);
        String toReturn = "";
        toReturn += "<!DOCTYPE html>";
        toReturn += "<html xmlns:th=\"http://www.thymeleaf.org\">";
        toReturn += "<head th:replace=\"template :: header\">";
        toReturn += "<title>Survey Editor</title>";
        toReturn += "<script src=\"../js/javascript.js\"></script>";
        toReturn += "</head>";
    
        
        toReturn += "<div th:replace=\"template :: navbar\"/>";
        toReturn += "<div class =\"sbuilderheader\">Survey Name:";
        toReturn += "<input id =\"surveyName\" type=\"text\" name=\"sName\" class=\"textbox\" value=\""+survey.getSurveyName()+"\"></input>";
        toReturn += "<button onclick=\"createQuestion()\" type=\"button\" name=\"createQ\">Add Question</button>";
        toReturn += "<form id=\"submitForm\" action=\"surveyEditor\" onsubmit=\"return constructString()\" method=\"post\">";
        toReturn += "<input type=\"text\" id=\"xmlForm\" name=\"mytextform\" value=\"\" style=\"display:none\"></input>";
        toReturn += "<input type=\"text\" id=\"surveyIdVar\" name=\"surveyId\" value=\""+surveyId+"\" style=\"display:none\"></input>";
        toReturn += "<button id=\"saveButton\" type=\"submit\"> SaveSurvey</button>";
        toReturn += "</form><br></br></div>";
        toReturn += "<input type=\"hidden\" id=\"questionCount\" value=\""+questionsArray.size()+"\">";
    
    
        for(int i = 0; i < questionsArray.size(); i++)
        {
            String questionText = questionsArray.get(i).getQuestion();
            toReturn += "<div id=\"div" + i + "\" class=\"questions\">";
            toReturn += "<br><form><input id=\"question" + i + "\" type=\"text\" name=\"questionText\" class=\"questiontext\" value=\""+questionText+"\">";
            toReturn += "<select id=\"dropDown"+i+"\" onchange=\"removeAllAnswers("+i+")\" name=\"qType\">";
            //set drop down menu to that stored....TODO
            int questionType = questionsArray.get(i).getQuestionTypeId();
            if(questionType == 4)
            {
                toReturn += "<option value=\"OpenText\" selected>Open Text</option>";
                toReturn += "<option value=\"RadioButtons\">Radio Button</option>";
                toReturn += "<option value=\"ScoreRange\">Score Range</option>";
            }
            else if(questionType == 2)
            {
                toReturn += "<option value=\"OpenText\">Open Text</option>";
                toReturn += "<option value=\"RadioButtons\" selected>Radio Button</option>";
                toReturn += "<option value=\"ScoreRange\">Score Range</option>";
            }
            else if(questionType == 3)
            {
                toReturn += "<option value=\"OpenText\">Open Text</option>";
                toReturn += "<option value=\"RadioButtons\">Radio Button</option>";
                toReturn += "<option value=\"ScoreRange\" selected>Score Range</option>";
            }
            
            
            
            toReturn += "</select>";
            toReturn += "<button onclick=\"deleteQuestion("+i+")\" type=\"button\" name=\"deleteQ\">Delete Question</button>";
            toReturn += "</form>";
            
            if(questionType == 2)
            {
                toReturn += "<button id=\"createAnswer"+i+"\" onclick=\"createAnswer("+i+")\" type=\"button\" name=\"createQ\">Create Answer</button>";
            
                //loop through answers       
                List<Answers> answers = answersDao.findByQuestions(questionsArray.get(i));

                for(int j = 0; j < answers.size(); j++)
                {
                    String answerText = answers.get(j).getAnswer();
                    toReturn += "<div id=\"ansDiv"+i+"\">Answer: ";
                    toReturn += "<input id=ans"+i+" class=\"textbox\" name=\"answer"+i+"\" type=\"text\" value=\""+answerText+"\" >";
                    toReturn +=  "<span onclick=\"deleteAnswer("+i+")\" id=\"deleteIcon\">   ×</span>";
                    toReturn += "</div>";
                } 
            }
            toReturn += "</div>";                  
        }
        toReturn += "<div id=\"div"+questionsArray.size()+"\" class=\"questions\" style=\"display:none\">";
        model.addAttribute("surveyEditorPage", toReturn);
        return "ourSurveyEditor";
    }

    @RequestMapping(value = "/surveyEditor", method = RequestMethod.POST)
    public String surveyBuilder(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException
    {
        if(!checkValidation(request,"SURVEYOR"))
            return "sLogin";
        
        
        //find user
        Cookie c[] = request.getCookies();
        Cookie myCookie = c[0];
        Sessions session = sessionsDao.findBySessionId(myCookie.getValue());
        
        //clear current survey values from the database
        int surveyId = Integer.parseInt(request.getParameter("surveyId"));
        Surveys s = surveysDao.findBySurveyId(surveyId);
        surveysDao.delete(s);
        
        try {
            //gets the value from the textbox
            //System.out.println(request.getParameter("mytextform"));
            DocumentBuilder builder;
            XPath path;
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            builder = dbFactory.newDocumentBuilder();

            XPathFactory xpfactory = XPathFactory.newInstance();
            path = xpfactory.newXPath();
            
            //System.out.println(filename);
            InputSource is = new InputSource(new StringReader(request.getParameter("mytextform")));
            Document doc = builder.parse(is);

            //get name of survey
            String surveyName = path.evaluate("/survey/surveyName" ,doc);

            //save survey to the database
            Surveys survey  = new Surveys(surveyName,usersDao.findByUserId(session.getUserId()));
            surveysDao.save(survey);
            

            for(int i = 0; i<doc.getElementsByTagName("question").getLength();i++)
            {
                //retrieve current question from doc model
                Element qE = (Element)doc.getElementsByTagName("question").item(i);

                //extract appropriae variables
                String questionText = qE.getElementsByTagName("questionText").item(0).getTextContent();
                String questionType = qE.getElementsByTagName("questionType").item(0).getTextContent();

                int questionTypeId =2; //radio button (with values) by default

                //System.out.println("question type is"+questionType);
                
                if(questionType.equals("OpenText"))
                {
                    questionTypeId = 4;
                }

                //add question to the db
                Questions question = new Questions(questionText,questionTypeId,survey.getSurveyId());
                questionsDao.save(question);
                
                //add answers for the question
                if(questionTypeId==4)
                {
                    Answers answer = new Answers("",question,0);
                    answersDao.save(answer);
                }
                else
                {
                    for(int j = 0; j<qE.getElementsByTagName("answerText").getLength();j++)
                    {
                        Element aE = (Element) qE.getElementsByTagName("answerText").item(j);

                        String answerText = aE.getTextContent();
                        //String answerWeight = aE.getElementsByTagName("answerWeight").item(0).getTextContent();

                        Answers answer = new Answers(answerText,question,0);
                        answersDao.save(answer);
                    }
                }
        }
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("/survey_results");
        return "landingPage";
    }
    
    private boolean checkValidation(HttpServletRequest request, String role)
    {

        Cookie[] c = request.getCookies();
        if(c == null)
        {
            System.out.println("no cookies found");
            return false;
        }
        
        
        //System.out.println(c.length);
        Cookie myCookie = c[0];

        Sessions session = sessionsDao.findBySessionId(myCookie.getValue());
        
        //Find user role
        Users u = usersDao.findByUserId(session.getUserId());
        
        boolean isValid = false;
        
        //true if valid
        if(u.getRole().equals(role) && session.getExpiryTime().after(new Date()))
        {
            isValid = true;
        }
        
        //extends the validity of the page
        if(isValid)
        {
            Date newExpiryDate = new Date();//create a new date refference
            newExpiryDate.setHours(newExpiryDate.getHours()+1);//adds an hour to the expiry date
            session.setExpiryTime(newExpiryDate);//updates the new expiry date
            sessionsDao.save(session);
            myCookie.setMaxAge(60*60);
        }
        
        return isValid;
    }
        
}
