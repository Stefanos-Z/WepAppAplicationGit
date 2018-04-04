/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.controllers;

import com.inspiredGaming.surveyWebApp.Email;
import com.inspiredGaming.surveyWebApp.HtmlBuilderSurvey;
import com.inspiredGaming.surveyWebApp.HtmlBuilderTable;
import com.inspiredGaming.surveyWebApp.XMLParser.QuestionsParser;
import com.inspiredGaming.surveyWebApp.models.Answers;
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
import com.inspiredGaming.surveyWebApp.models.dao.AnswersDao;
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
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.ModelMap;





/**
 *
 * @author Levi
 */
@Controller
public class HelloController {
    
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
    //Dependancy injection used by Spring
    //Needed because the interface instance is not manually coded   
    
    
    ServletContext servletContext;
    
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(HttpServletResponse responce)
    {

        return "sLogin";
    }
    
    
    
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPage(HttpServletResponse responce, HttpServletRequest request, Model model)
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
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

                return "landingPage";
                //return landingForm();
            }
        }
        
        return "sLogin";
    }
    
    
    @RequestMapping(value = "/surveyBuilder", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String surveyBuilderForm(HttpServletRequest request)
    {
        if(checkValidation(request))
            return "ourSurveyBuilder";
        return "sLogin";
    }
    
    
    @RequestMapping(value = "/landing", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String landingForm(HttpServletRequest request)
    {
        if(checkValidation(request))
            return "landingPage";
        return "sLogin";
    }
    
    @RequestMapping(value = "/surveyBuilder", method = RequestMethod.POST)
    public String surveyBuilder(HttpServletRequest request, Model model)
    {
        if(!checkValidation(request))
            return "sLogin";
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
            Surveys survey  = new Surveys(surveyName,usersDao.findByUserId(1));
            surveysDao.save(survey);

            for(int i = 0; i<doc.getElementsByTagName("question").getLength();i++)
            {
                //retrieve current question from doc model
                Element qE = (Element)doc.getElementsByTagName("question").item(i);

                //extract appropriae variables
                String questionText = qE.getElementsByTagName("questionText").item(0).getTextContent();
                String questionType = qE.getElementsByTagName("questionType").item(0).getTextContent();

                int questionTypeId =2; //radio button (with values) by default

                System.out.println("question type is"+questionType);
                
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
        
        Surveys s = surveysDao.findBySurveyId(surveyId);
                
        //check key to see if survey has been completed
        boolean expired = surveyKeysDao.findByKeyId(request.getParameter("key")).getExpired();
        
        if(!expired)
        {
            //queries database for a list of all rows in Questions
            List<Questions> questionList = questionsDao.findBySurveyId(surveyId);

            //build html for survey
            HtmlBuilderSurvey htmlDoc = new HtmlBuilderSurvey();

            //generates html <h1> tags for each row
            for(int i = 0;i<questionList.size();i++)
            {
                    //get answers for all questions
                    List<Answers> answerList = answersDao.findByQuestions(questionList.get(i));

                    //add question & answers to html
                    htmlDoc.addQuestion(questionList.get(i), answerList,i+1);
            }       

            //model.addAttribute("surveyName", "Survey :"+s.getSurveyName()); HEADER- REMOVED TEMPORARILY
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
        int surveyId = surveyKeysDao.findByKeyId(request.getParameter("key")).getSurveyId();
                
        //get list of expected questions
        List<Questions> questionList = questionsDao.findBySurveyId(surveyId);
        
        //get all parameter values
        Map<String, String[]> map = request.getParameterMap();
        
        //add respondent to table
        Respondents log = new Respondents(surveyId);
        respondentDao.save(log);
        
        //add answers to database
        for(int i = 0; i<questionList.size();i++)
        {
            //check type of question
            int questionTypeId = questionList.get(i).getQuestionTypeId();
            
            //if question is text, need to search for answerId & insert value param into answerText field
            if(questionTypeId==4)
            {
                List<Answers> textAnswer = answersDao.findByQuestions(questionList.get(i));
                //Answer answerId = textAnswer.get(0).getAnswerId();                
                
                RespondentAnswers answerLog = new RespondentAnswers(textAnswer.get(0),log.getRespondentId(),map.get(""+questionList.get(i).getQuestionId())[0]);
                respondentAnswerDao.save(answerLog);
            }
            else
            {
                //insert radio buttons answerlog.
                Answers a = answersDao.findByAnswerId(Integer.parseInt(map.get(""+questionList.get(i).getQuestionId())[0]));
                RespondentAnswers answerLog = new RespondentAnswers(a,log.getRespondentId(),"");
                respondentAnswerDao.save(answerLog);
            }
            
        }
        
        //make survey expired
        SurveyKeys key = surveyKeysDao.findByKeyId(request.getParameter("key"));
        key.setExpired(true);
        surveyKeysDao.save(key);
        
        return "hello";
    }
    
    @RequestMapping(value = "/uploademails", method = RequestMethod.GET)
    public String uploademailsForm(HttpServletRequest request, Model model)
    {       
        if (checkValidation(request))
            return "uploademails";
        return "sLogin";
    }
    
    @RequestMapping(value = "/uploademails", method = RequestMethod.POST)
    public String uploadEmailsSubmit(HttpServletRequest request, Model model)
    {        
        if (!checkValidation(request))
            return "sLogin";
        //get survey id
        String emails = request.getParameter("emails");
        
        String[] emailList = emails.split("\n");
        
        //clear database of old emails.
        staffEmailsDao.deleteAll();
        
        //save all new emails
        for(int i = 0; i<emailList.length; i++)
        {
            emailList[i] = emailList[i].replaceAll("[\r|\n|\\s]","");
            StaffEmails email = new StaffEmails(emailList[i]);            
            staffEmailsDao.save(email);
        }
        
        return "uploademails";
    }
    
    @RequestMapping(value = "/survey_results", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String surveyResults(HttpServletRequest request, Model model)
    {
        if(checkValidation(request)){
            List<Surveys> surveyList = surveysDao.findAll();

            HtmlBuilderTable tableBuilder = new HtmlBuilderTable();

            String tableXML = tableBuilder.buildSurveyTable(surveyList,respondentDao);

             model.addAttribute("surveyTable", tableXML);

            return "resultsSurveyList";
        }
        return "sLogin";
    }
    
    @RequestMapping(value = "/survey_results/responses", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String surveyResponses(HttpServletRequest request, Model model)
    {
        if(checkValidation(request)){
            int surveyId = Integer.parseInt(request.getParameter("survey"));

            List<Respondents> respondentList = respondentDao.findBySurveyId(surveyId);

            HtmlBuilderTable tableBuilder = new HtmlBuilderTable();

            String tableXML = tableBuilder.buildResponseTable(respondentList);

             model.addAttribute("surveyTable", tableXML);

            return "resultsSurveyList";
        }
        return "sLogin";
    }
    
    @RequestMapping(value = "/survey_results/responses/user", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String surveyIndividualResponses(HttpServletRequest request, Model model)
    {
        if(checkValidation(request)){
            int respondentId = Integer.parseInt(request.getParameter("id"));

            List<RespondentAnswers> answers = respondentAnswerDao.findByRespondentId(respondentId);

            HtmlBuilderTable tableBuilder = new HtmlBuilderTable();

            String tableXML = tableBuilder.buildIndividialResponse(answers);

            model.addAttribute("surveyTable", tableXML);

            return "resultsSurveyList";
        }
        return "sLogin";
    }
    
    @RequestMapping(value = "/deleteSurvey", method = RequestMethod.POST)
    public String testSubmit(HttpServletRequest request) throws IOException
    {
        if(!checkValidation(request))
            return "String Error";
        //get parameter from request body
        int surveyId = Integer.parseInt(request.getParameter("surveyid"));
        System.out.println(request.getParameter("surveyid"));
        
        //find corresponding survey
        Surveys s = surveysDao.findBySurveyId(surveyId);
        System.out.println(s.getSurveyName());
        
        //delete survey from database
        surveysDao.delete(s);
        
        System.out.println("test successful!");
                
        //return surveyResults(request,model);
        return "hello";
    }
    
    @RequestMapping(value = "/sendemails", method = RequestMethod.GET)
    public String sendemailsForm(HttpServletRequest request, Model model)
    {
        if(!checkValidation(request))
            return "sLogin";
        List<Surveys> surveys = surveysDao.findAll();
        
        String selectListHtml = "";
        
        //add all surveys to the form
        for(int i = 0;i<surveys.size();i++)
        {
           
            selectListHtml += "<option value = \""+surveys.get(i).getSurveyId()+"\">"+surveys.get(i).getSurveyName()+"</option>";
        }
        
        model.addAttribute("selectList", selectListHtml);
        
        return "sendemails";
    }
    
    @RequestMapping(value = "/sendemails", method = RequestMethod.POST)
    public String sendEmailsSubmit(HttpServletRequest request, Model model)
    {
        if(!checkValidation(request))
            return "sLogin";
        //get survey id
        List<StaffEmails> emailList = staffEmailsDao.findAll();
                
        //save all new emails
        for(int i = 0; i<emailList.size(); i++)
        {
            //test - send survey to listed emails and record unique key
            try {
                System.out.println("gets here");
                SurveyKeys key = new SurveyKeys(Integer.parseInt(request.getParameter("surveys")));
                surveyKeysDao.save(key);
                Email emailObj = new Email(emailList.get(i).getEmail(),"http://localhost:8080/survey?key="+key.getKeyId());
                emailObj.send();
            } catch (MessagingException ex) {
                Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return "sendemails";
    }
        
    
    
    private boolean checkValidation(HttpServletRequest request)
    {

        Cookie[] c = request.getCookies();
        if(c == null)
        {
            System.out.println("no cookies found");
            return false;
        }
        
        
        System.out.println(c.length);
        Cookie myCookie = c[0];
        System.out.println(myCookie.getName());
        System.out.println(myCookie.getValue());
        System.out.println(myCookie.getMaxAge());
        
        
        Sessions session = sessionsDao.findBySessionId(myCookie.getValue());
        System.out.println(session.getExpiryTime());
        
        boolean isValid = session.getExpiryTime().after(new Date());
        
        //extends the validity of the page
        if(isValid)
        {
            
        }
        
        return isValid;
    }
}
    
