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
    
    @Autowired
    private EmailGroupsDao emailGroupsDao;
    //Dependancy injection used by Spring
    //Needed because the interface instance is not manually coded   
    
    
    ServletContext servletContext;
    
    
    
    
    
    @RequestMapping(value = "/surveyBuilder", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String surveyBuilderForm(HttpServletRequest request)
    {
        if(checkValidation(request,"SURVEYOR"))
            return "ourSurveyBuilder";
        return "sLogin";
    }
    
    @RequestMapping(value = "/user_admin", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String userAdmin(HttpServletRequest request, Model model)
    {
        if(!checkValidation(request,"ADMINISTRATOR"))
            return "sLogin";
        
        HtmlBuilderTable h = new HtmlBuilderTable();
        
        List<Users> userlist = usersDao.findAll();
        
        model.addAttribute("userTable",h.getUsersTable(userlist));
        
        return "useradmin";
        
    }
    
    
    
    @RequestMapping(value = "/landing", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String landingForm(HttpServletRequest request)
    {
        if(checkValidation(request,"SURVEYOR"))
            return "landingPage";
        return "sLogin";
    }
    
    @RequestMapping(value = "/landingAdmin", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String landingFormAdmin(HttpServletRequest request)
    {
        if(checkValidation(request,"ADMINISTRATOR"))
            return "landingPageAdmin";
        return "sLogin";
    }
    
    @RequestMapping(value = "/surveyBuilder", method = RequestMethod.POST)
    public String surveyBuilder(HttpServletRequest request, Model model)
    {
        if(!checkValidation(request,"SURVEYOR"))
            return "sLogin";
        
        //find user
        Cookie c[] = request.getCookies();
        Cookie myCookie = c[0];
        Sessions session = sessionsDao.findBySessionId(myCookie.getValue());
        
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
    public String surveySubmit(HttpServletRequest request, Model model) throws MessagingException
    {
        surveyKeysDao.findByKeyId(request.getParameter("key")).getSurveyId();
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
            
            try{
                
            
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
            }catch(NullPointerException error)
            {
                System.out.println("Question not filled");
            }
            
        }
        
        //Email the creator of the survey.
        Surveys s = surveysDao.findBySurveyId(surveyId);
        Users user = usersDao.findByUserId(s.getUsers().getUserId());
        Email email = new Email(user.getEmail(),"http://localhost:8080/survey_results/responses/user?id="+log.getRespondentId());
        email.sendCompletionEmail(s.getSurveyName());
        
        //make survey expired
        SurveyKeys key = surveyKeysDao.findByKeyId(request.getParameter("key"));
        key.setExpired(true);
        surveyKeysDao.save(key);
        
        return "hello";
    }
    
    @RequestMapping(value = "/getemails", method = RequestMethod.POST)
    @ResponseBody
    public String getEmails(HttpServletRequest request, Model model)
    { 
        String emailString = "";
        
        EmailGroups group = emailGroupsDao.findByGroupName(request.getParameter("groupName"));
        
        List<StaffEmails> e = staffEmailsDao.findByGroupID(group.getGroupID());
        
        for(StaffEmails www: e)
        {
            emailString += www.getEmail()+"\n";
        }
        
        return emailString;
    }
    
    @RequestMapping(value = "/groupmanagement", method = RequestMethod.GET)
    public String manageGroupsForm(HttpServletRequest request, Model model)
    {       
        if (!checkValidation(request,"SURVEYOR"))
            return "sLogin";     
        List<EmailGroups> groups = emailGroupsDao.findAll();
        
        String selectListHtml = "";        
        
        //add all groups to the form
        for(int i = 0;i<groups.size();i++)
        {
           
           selectListHtml += "<option value = \""+groups.get(i).getGroupID()+"\">"+groups.get(i).getGroupName()+"</option>";
           if(i==0)
           {
                List<StaffEmails> emailsInFirstGroup = staffEmailsDao.findByGroupID(groups.get(i).getGroupID());
                String emailStrings ="";
                for(StaffEmails e: emailsInFirstGroup)
                    emailStrings += e.getEmail() +"\n";
                model.addAttribute("emailTextArea", emailStrings);
           }
        }
        
        //do not change!!!!
        selectListHtml += "<option value = \""+"New Group"+"\">"+"New Group"+"</option>";
        //this line!!!!
        
        
        
        model.addAttribute("groupSelectList", selectListHtml);
        return "uploademails";
    }
    
    @RequestMapping(value = "/groupmanagement", method = RequestMethod.POST, params="upload")
    public String manageGroupsSubmit(HttpServletRequest request,HttpServletResponse response, Model model)
    {        
        if (!checkValidation(request,"SURVEYOR"))
            return "sLogin";
        //get emails
        String emails = request.getParameter("emails");
        String[] emailList = emails.split("\n");
        
        //create arraylist to catch invalid emails
        ArrayList<String> invalidEmails = new ArrayList<String>();
        
        String selected = request.getParameter("groups");
        
        EmailGroups emailGroup;
        
        //clear emails if updating a group
        if(!selected.equals("New Group"))
        {
            Integer groupID = Integer.parseInt(selected);
            
            emailGroup = emailGroupsDao.findByGroupID(groupID);
            
            List<StaffEmails> allEmails = staffEmailsDao.findAll();
            
            //remove group so it can be replaced with new version.
            for(StaffEmails thisEmail: allEmails)
            {
                if(thisEmail.getGroupID() == groupID)
                    staffEmailsDao.delete(thisEmail);
            }
        }
        
        //else create new group
        else
        {
            String groupName = request.getParameter("groupName");
            emailGroup = new EmailGroups(groupName);
            emailGroupsDao.save(emailGroup);
        }
        
        //upload all new emails
        for(int i = 0; i<emailList.length; i++)
        {
            //strip whitespace characters
            emailList[i] = emailList[i].replaceAll("[\r|\n|\\s]","");

            if(Pattern.matches("[A-z\\d][A-z\\d_\\-.]+[@][A-z|\\d]+[.A-z\\d]+[A-z]+", emailList[i]))
            {
                StaffEmails email = new StaffEmails(emailList[i], emailGroup.getGroupID());            
                staffEmailsDao.save(email);
            }
            else
            {
                invalidEmails.add(emailList[i]);
            }

        }
        
        int numberUploaded = emailList.length - invalidEmails.size();
        String serverMessage = "<h1>"+numberUploaded+"/"+emailList.length+" emails uploaded sucessfully</h1><br/>";
        
        //flag any invalid email addresses
        if(invalidEmails.size()>0)
        {
            serverMessage += "<p id = \"errorEmails\"><span class = \"glyphicon glyphicon-exclamation-sign\"></span>"+
                            "The following email addresses are invalid and were excluded from the upload:<br/><br/>";
            
            for(String email: invalidEmails)
            {
                serverMessage+= email + "<br/>";
            }
            serverMessage +="<br/></p>";
        }
        
        model.addAttribute("serverMessage", serverMessage);
        /*
        try {
            response.sendRedirect("/uploademails");
        } catch (IOException ex) {
            System.out.println("Error in redirect");
        }*/
        
        
        return "confirmationPage";
    }
    
    
    @RequestMapping(value = "/groupmanagement", method = RequestMethod.POST, params="delete")
    public String deleteGroupsSubmit(HttpServletRequest request,HttpServletResponse response, Model model)
    {
        int selectedGroupId = Integer.parseInt(request.getParameter("groups"));
        
        List<StaffEmails> emailsToBeDeleted = staffEmailsDao.findByGroupID(selectedGroupId);
        int emailCount = emailsToBeDeleted.size();
        for(StaffEmails e: emailsToBeDeleted)
        {
            staffEmailsDao.delete(e);
        }
        String groupName = emailGroupsDao.findByGroupID(selectedGroupId).getGroupName();
        emailGroupsDao.delete(selectedGroupId);
        
        String serverMessage = "<h1>"+ "The group "+groupName +" with a total of " +emailCount+" has been succesfully deleted!" +"</h1><br/>";

        model.addAttribute("serverMessage", serverMessage);
        return "confirmationPage";
    }
    @RequestMapping(value = "/survey_results", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String surveyResults(HttpServletRequest request, Model model)
    {
        if(checkValidation(request,"SURVEYOR")){
            List<Surveys> surveyList = surveysDao.findAll();

            HtmlBuilderTable tableBuilder = new HtmlBuilderTable();

            String tableXML = tableBuilder.buildSurveyTable(surveyList,respondentDao);
            String breadcrumbs = "<ul class=\"breadcrumb123\">"+
                        "<li><a href=\"/landing\">Home</a></li>"+ 
                        "<li><a href=\"#\">Survey Overview</a></li>"+
                    "</ul>";
                        
            
            
            
            model.addAttribute("myBreadcrumbs", breadcrumbs);
            model.addAttribute("surveyTable", tableXML);

            return "resultsSurveyList";
        }
        return "sLogin";
    }
    
    @RequestMapping(value = "/survey_results/responses", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String surveyResponses(HttpServletRequest request, Model model)
    {
        if(checkValidation(request,"SURVEYOR")){
            int surveyId = Integer.parseInt(request.getParameter("survey"));
            String surveyName = surveysDao.findBySurveyId(surveyId).getSurveyName();
            List<Respondents> respondentList = respondentDao.findBySurveyId(surveyId);

            HtmlBuilderTable tableBuilder = new HtmlBuilderTable();

            String tableXML = tableBuilder.buildResponseTable(respondentList);
            String breadcrumbs = "<ul class=\"breadcrumb123\">"+
                        "<li><a href=\"/landing\">Home</a></li>"+ 
                        "<li><a href=\"/survey_results\">Survey Overview</a></li>"+
                        "<li><a href=\"/survey_results/responses?survey="+surveyId+"\">SurveyNo"+ surveyName +"</a></li>"+
                    "</ul>";
                        
            
            
            
            model.addAttribute("myBreadcrumbs", breadcrumbs);
            model.addAttribute("surveyTable", tableXML);
             
            return "resultsSurveyList";
        }
        return "sLogin";
    }
    
    @RequestMapping(value = "/survey_results/responses/user", method = RequestMethod.GET)
    //@ResponseBody //just for passing a string instead of a template
    public String surveyIndividualResponses(HttpServletRequest request, Model model)
    {
        if(checkValidation(request,"SURVEYOR")){
            int respondentId = Integer.parseInt(request.getParameter("id"));
            
            List<RespondentAnswers> answers = respondentAnswerDao.findByRespondentId(respondentId);
            Answers aaa = answers.get(0).getAnswers();
            Questions ddd = aaa.getQuestions();

            System.out.println(ddd.getSurveyId());
            String surveyName = surveysDao.findBySurveyId(ddd.getSurveyId()).getSurveyName();
            String breadcrumbs = "<ul class=\"breadcrumb123\">"+
                        "<li><a href=\"/landing\">Home</a></li>"+ 
                        "<li><a href=\"/survey_results\">Survey Overview</a></li>"+
                        "<li><a href=\"/survey_results/responses?survey="+ddd.getSurveyId()+"\">Survey: "+ surveyName +"</a></li>"+
                        "<li><a href=\"#\">RespondentNo: "+ "I want to put selected respondent num" +"</a></li>"+
                    "</ul>";
                        
            
            
            
            model.addAttribute("myBreadcrumbs", breadcrumbs);

            HtmlBuilderTable tableBuilder = new HtmlBuilderTable();

            String tableXML = tableBuilder.buildIndividialResponse(answers);

//            String breadcrumbs = "<ul class=\"breadcrumb123\">"+
//                        "<li><a href=\"/landing\">Home</a></li>"+ 
//                        "<li><a href=\"/survey_results\">Survey Overview</a></li>"+
//                        "<li><a href=\"/resultsSurveyList\">SurveyNo"+ 4 +"</a></li>"+
//                    "</ul>";
//                        
//            
//            
//            
//            model.addAttribute("myBreadcrumbs", breadcrumbs);
//            
            
            model.addAttribute("surveyTable", tableXML);

            return "resultsSurveyList";
        }
        return "sLogin";
    }
    
    @RequestMapping(value = "/deleteSurvey", method = RequestMethod.POST)
    public String testSubmit(HttpServletRequest request) throws IOException
    {
        if(!checkValidation(request,"SURVEYOR"))
            return "String Error";
        //get parameter from request body
        int surveyId = Integer.parseInt(request.getParameter("surveyid"));
        //System.out.println(request.getParameter("surveyid"));
        
        //find corresponding survey
        Surveys s = surveysDao.findBySurveyId(surveyId);
        //System.out.println(s.getSurveyName());
        
        //delete survey from database
        surveysDao.delete(s);
        
        //System.out.println("test successful!");
                
        //return surveyResults(request,model);
        return "hello";
    }
    
    @RequestMapping(value = "/sendemails", method = RequestMethod.GET)
    public String sendemailsForm(HttpServletRequest request, Model model)
    {
        if(!checkValidation(request,"SURVEYOR"))
            return "sLogin";
        List<Surveys> surveys = surveysDao.findAll();
        
        String selectListHtml = "";
        
        //add all surveys to the form
        for(int i = 0;i<surveys.size();i++)
        {
           
            selectListHtml += "<option value = \""+surveys.get(i).getSurveyId()+"\">"+surveys.get(i).getSurveyName()+"</option>";
        }
        
        
        List<EmailGroups> groups = emailGroupsDao.findAll();
        String groupListHtml = "";
        
        //add all groups to the form
        for(int i = 0;i<groups.size();i++)
        {
           
            groupListHtml += "<option value = \""+groups.get(i).getGroupID()+"\">"+groups.get(i).getGroupName()+"</option>";
        }
        
        model.addAttribute("selectList", selectListHtml);
        model.addAttribute("groupList", groupListHtml);
        
        
        return "sendemails";
    }
    
    @RequestMapping(value = "/sendemails", method = RequestMethod.POST)
    public String sendEmailsSubmit(HttpServletRequest request, Model model)
    {
        if(!checkValidation(request,"SURVEYOR"))
            return "sLogin";
        //get survey id
        
        int groupId = Integer.parseInt(request.getParameter("group"));
        
        List<StaffEmails> emailList = staffEmailsDao.findByGroupID(groupId);
        
        //save all new emails
        for(int i = 0; i<emailList.size(); i++)
        {
            //test - send survey to listed emails and record unique key
            try {
                //System.out.println("gets here");
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
        
    /**
     * Returns the results of a selected questionnaire
     * @param request is the Server Request ???
     * @param model is the selected questionnaire ????
     * @return the login or the result page
     */
    @RequestMapping(value = "/resultPage", method = RequestMethod.GET)
    public String displayResultPage(HttpServletRequest request, Model model)
    {
        /*
        if(!checkValidation(request,"SURVEYOR")){
            return "sLogin";
        }
        */
        return "resultPage";
    }
    
    /**
     * A method to validate the credentials of a request.
     * @param request
     * @return 
     */
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
    
    /**
     * Validates a user submission.
     * @param username
     * @param password
     * @param email
     * @return 
     */
    private boolean validateUser(String username, String password, String email)
    {
        boolean valid = true;
        valid = !username.isEmpty();
        valid = !password.isEmpty();
        valid = Pattern.matches("[A-z\\d][A-z\\d_\\-.]+[@][A-z|\\d]+[.A-z\\d]+[A-z]+", email);
                
        return valid;
    }
    
    @RequestMapping(value = "/add_user", method = RequestMethod.POST)
    @ResponseBody //just for passing a string instead of a template
    public String addUser(HttpServletRequest request, Model model)
    {
        if(!checkValidation(request,"ADMINISTRATOR"))
        {
            return "ERROR- INVALID USER ROLE";
        }
        
        //get user data
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String role = request.getParameter("role");
        
        //validate mandatory fields
        if(!validateUser(username,password,email))
        {
            return "ERROR- INVALID SUBMISSION";
        }
        
        //create a new user
        Users u = new Users(username,password,email,phoneNumber,role);
        
        usersDao.save(u);
        
        HtmlBuilderTable tb = new HtmlBuilderTable();
        
        List<Users> userlist = usersDao.findAll();
        
        return tb.getUsersTable(userlist);
    }
    
    @RequestMapping(value = "/edit_user", method = RequestMethod.POST)
    @ResponseBody //just for passing a string instead of a template
    public String editUser(HttpServletRequest request, Model model)
    {
        if(!checkValidation(request,"ADMINISTRATOR"))
        {
            return "ERROR- INVALID USER ROLE";
        }
        
        //public Users(String username, String userPassword, String email, String phoneNumber, String role) {
        String id = request.getParameter("userid");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String role = request.getParameter("role");
        
        //validate mandatory fields
        if(!validateUser(username,password,email))
        {
            return "ERROR- INVALID SUBMISSION";
        }
        
        //create a new user
        Users u = usersDao.findByUserId(Integer.parseInt(id));
        
        //update values
        u.setUsername(username);
        
        if(!password.equals("******"))  //only update if password has been changed
        {
           u.setUserPassword(password); 
        }
        
        u.setEmail(email);
        u.setPhoneNumber(phoneNumber);
        u.setRole(role);
        
        usersDao.save(u);
        
        //return HTML for updated table
        HtmlBuilderTable tb = new HtmlBuilderTable();
        List<Users> userlist = usersDao.findAll();
        
        return tb.getUsersTable(userlist);
    }
    
    @RequestMapping(value = "/delete_user", method = RequestMethod.POST)
    @ResponseBody //just for passing a string instead of a template
    public String deleteUser(HttpServletRequest request, Model model)
    {
        if(!checkValidation(request,"ADMINISTRATOR"))
        {
            return "ERROR- INVALID USER ROLE";
        }
        
        //public Users(String username, String userPassword, String email, String phoneNumber, String role) {
        String id = request.getParameter("userid");
                
        //create a new user
        Users u = usersDao.findByUserId(Integer.parseInt(id));
        
        usersDao.findByUserId(Integer.parseInt(id));
        
        //delete the specified user
        usersDao.delete(u);
        
        //return HTML for updated table
        HtmlBuilderTable tb = new HtmlBuilderTable();
        List<Users> userlist = usersDao.findAll();
        
        return tb.getUsersTable(userlist);
    }
    
    
    @RequestMapping(value = "/check_username_uniqueness", method = RequestMethod.POST)
    @ResponseBody //just for passing a string instead of a template
    public String checkUsernameUniqueness(HttpServletRequest request, Model model)
    {
 
        String username = request.getParameter("username");
        int userId = Integer.parseInt(request.getParameter("userId"));
        System.out.println("user id: "+userId+", username: "+username);
         
        Users user = usersDao.findByUsername(username);
         
        if(user == null)
        {
            return "unique";
         }
        else if (user.getUserId()==userId)
        {
            return "unique";
        }
        else
        {
            return "duplicate";
        }
         
        
    }
    
    
}
    
