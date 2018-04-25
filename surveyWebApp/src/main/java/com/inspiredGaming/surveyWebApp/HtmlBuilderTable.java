/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp;

import com.inspiredGaming.surveyWebApp.models.Answers;
import com.inspiredGaming.surveyWebApp.models.Questions;
import com.inspiredGaming.surveyWebApp.models.RespondentAnswers;
import com.inspiredGaming.surveyWebApp.models.Respondents;
import com.inspiredGaming.surveyWebApp.models.Surveys;
import com.inspiredGaming.surveyWebApp.models.Users;
import com.inspiredGaming.surveyWebApp.models.dao.RespondentsDao;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

/**
 *
 * @author Levi
 */
public class HtmlBuilderTable {
        
    private Document doc;
    private Element table;
    
    /**
     * Constructor for class. Instantiates a doc object.
     */
    public HtmlBuilderTable()
    {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
            
            //build form xml
            table = doc.createElement("table");
            
                        
            doc.appendChild(table);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(HtmlBuilderTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Builds HTML for a table containing a list of all surveys in system.
     * @param surveys
     * @return 
     */
    public String buildSurveyTable(List<Surveys> surveys, RespondentsDao respondentDao)
    {
        //add header row
        Element headerRow = doc.createElement("tr");

        //add surveyname
        Element surveyNameHeader = doc.createElement("th");
        surveyNameHeader.setAttribute("class", "firstColumn");
        surveyNameHeader.appendChild(doc.createTextNode("Survey Name"));
        headerRow.appendChild(surveyNameHeader);

        //add userid
        Element userIdHeader = doc.createElement("th");
        userIdHeader.setAttribute("class", "columns");
        userIdHeader.appendChild(doc.createTextNode("Created By"));
        headerRow.appendChild(userIdHeader);
        
        //add creation_date
        Element creationDateHeader = doc.createElement("th");
        creationDateHeader.setAttribute("class", "columns");
        creationDateHeader.appendChild(doc.createTextNode("Creation Date"));
        headerRow.appendChild(creationDateHeader);


        //add responses
        Element responsesHeader = doc.createElement("th");
        responsesHeader.setAttribute("class", "columns");
        responsesHeader.appendChild(doc.createTextNode("Responses"));
        headerRow.appendChild(responsesHeader);
        
        //add stats header
        Element statsHeader = doc.createElement("th");
        statsHeader.setAttribute("class", "columns");
        statsHeader.appendChild(doc.createTextNode("Statistics"));
        headerRow.appendChild(statsHeader);
        
        //add edit header
        Element editHeader = doc.createElement("th");
        editHeader.setAttribute("class", "columns");
        editHeader.appendChild(doc.createTextNode("Edit"));
        headerRow.appendChild(editHeader);
        
        //add edit header
        Element deleteHeader = doc.createElement("th");
        deleteHeader.setAttribute("class", "columns");
        deleteHeader.appendChild(doc.createTextNode("Delete"));
        headerRow.appendChild(deleteHeader);

        table.appendChild(headerRow);
        
        //create rows in the table
        for (int i =0; i<surveys.size();i++)
        {
            Element row = doc.createElement("tr");
            row.setAttribute("id", "srv"+surveys.get(i).getSurveyId());
            
            //add surveyname
            Element surveyName = doc.createElement("td");
            surveyName.setAttribute("class", "firstColumn");
            surveyName.appendChild(doc.createTextNode(surveys.get(i).getSurveyName()));
            row.appendChild(surveyName);
            
            //add userid
            Element userId = doc.createElement("td");
            userId.appendChild(doc.createTextNode(""+surveys.get(i).getUsers().getUsername()));
            row.appendChild(userId);
            
            //add creation date
            Element creationDate = doc.createElement("td");
            creationDate.appendChild(doc.createTextNode((""+surveys.get(i).getCreationDate()).replace("00:00:00.0", "")));
            row.appendChild(creationDate);
            
            //add userid
            Element responses = doc.createElement("td");
            //count number of responses
            int numberOfResponses = respondentDao.findBySurveyId(surveys.get(i).getSurveyId()).size();
            Element link = doc.createElement("a");
            link.appendChild(doc.createTextNode(""+numberOfResponses));
            surveyName.appendChild(link);
            link.setAttribute("href", "http://localhost:8080/survey_results/responses?survey="+surveys.get(i).getSurveyId());
            responses.appendChild(link);
            row.appendChild(responses);
            
            //add creation date
            Element statsBreakdown = doc.createElement("td");
            
            Element statsIcon = doc.createElement("span");
            statsIcon.setAttribute("class", "glyphicon glyphicon-stats");
            Element statsLink = doc.createElement("a");
            statsLink.setAttribute("href", "/survey_results/survey_answers?surveyId="+surveys.get(i).getSurveyId());
            statsLink.setAttribute("class", "statsLink");
            statsLink.appendChild(statsIcon);
            statsBreakdown.appendChild(statsLink);
            row.appendChild(statsBreakdown);
            
            //add edit cell
            Element editCell = doc.createElement("td");
            //add an edit option ONLY if survey has no responses.
            if(numberOfResponses==0)
            {
                Element editIcon = doc.createElement("span");
                editIcon.setAttribute("class","glyphicon glyphicon-edit");
                Element editLink = doc.createElement("a");
                editLink.setAttribute("href", "/surveyEditor?surveyId="+surveys.get(i).getSurveyId());
                editLink.setAttribute("class", "editLink");
                editLink.appendChild(editIcon);
                editCell.appendChild(editLink);
            }
                        
            row.appendChild(editCell);
            
            //add delete cell
            Element deleteCell = doc.createElement("td");
            Element deleteIcon = doc.createElement("span");
            deleteIcon.setAttribute("class", "glyphicon glyphicon-remove-sign");
            Element deleteButton = doc.createElement("button");
            deleteButton.setAttribute("onClick", "updateModal('"+surveys.get(i).getSurveyName()+"','"+surveys.get(i).getSurveyId()+"')");
            deleteButton.setAttribute("data-toggle", "modal");
            deleteButton.setAttribute("data-target", "#myModal");
            deleteButton.appendChild(deleteIcon);
            
            deleteCell.appendChild(deleteButton);
            
            row.appendChild(deleteCell);
            
            table.appendChild(row);
        }
        
        return getTableHTML();
        
    }

    /**
     * Builds HTML for a table containing a list of all surveys in system.
     * @param surveys
     * @return 
     */
    public String buildResponseTable(List<Respondents> respondents)
    {
        //add header row
        Element headerRow = doc.createElement("tr");

        //add surveyname
        Element surveyNameHeader = doc.createElement("th");
        surveyNameHeader.setAttribute("class", "columns");
        surveyNameHeader.appendChild(doc.createTextNode("Survey Respondent"));
        headerRow.appendChild(surveyNameHeader);

        //add creation date
        Element creationDateHeader = doc.createElement("th");
        creationDateHeader.setAttribute("class", "columns");
        creationDateHeader.appendChild(doc.createTextNode("Response Date"));
        headerRow.appendChild(creationDateHeader);
        
        //add view
        Element viewHeader = doc.createElement("th");
        viewHeader.setAttribute("class", "columns");
        viewHeader.appendChild(doc.createTextNode("View Responses"));
        headerRow.appendChild(viewHeader);

        table.appendChild(headerRow);
        
        //create rows in the table
        for (int i =0; i<respondents.size();i++)
        {
            Element row = doc.createElement("tr");
            
            //add respondent
            Element respondentId = doc.createElement("td");
            Element link = doc.createElement("a");
            link.setAttribute("href", "http://localhost:8080/survey_results/responses/user?id="+respondents.get(i).getRespondentId()+"&num="+(i+1));
            link.appendChild(doc.createTextNode(""+(i+1)));
            respondentId.appendChild(link);
            row.appendChild(respondentId);
            
            //add creation date
            Element creationDate = doc.createElement("td");
            creationDate.appendChild(doc.createTextNode(""+respondents.get(i).getSurveyDate()));
            row.appendChild(creationDate);
            
            //add view cell
            Element viewCell = doc.createElement("td");
            Element editIcon = doc.createElement("span");
            editIcon.setAttribute("class","glyphicon glyphicon-comment");
            Element viewLink = doc.createElement("a");
            viewLink.setAttribute("href", "http://localhost:8080/survey_results/responses/user?id="+respondents.get(i).getRespondentId()+"&num="+(i+1));    
            viewLink.setAttribute("class", "statsLink");
            viewLink.appendChild(editIcon);
            viewCell.appendChild(viewLink);
                        
            row.appendChild(viewCell);
            
            table.appendChild(row);
        }
        
        return getTableHTML();
        
    }
    
    /**
     * Builds HTML table displaying the results for one survey.
     * @param answers
     * @return 
     */
    public String buildIndividialResponse(List<RespondentAnswers> answers)
    {
        //add header row
        Element headerRow = doc.createElement("tr");

        //add surveyname
        Element surveyNameHeader = doc.createElement("th");
        surveyNameHeader.setAttribute("class", "firstColumn");
        surveyNameHeader.appendChild(doc.createTextNode("Question"));
        headerRow.appendChild(surveyNameHeader);

        //add creation date
        Element creationDateHeader = doc.createElement("th");
        creationDateHeader.appendChild(doc.createTextNode("Response"));
        headerRow.appendChild(creationDateHeader);

        table.appendChild(headerRow);
        
        //create rows in the table
        for (int i =0; i<answers.size();i++)
        {
            Element row = doc.createElement("tr");
            
            //add question
            Element respondentId = doc.createElement("td");
            respondentId.setAttribute("class", "firstColumn");
            respondentId.appendChild(doc.createTextNode(answers.get(i).getAnswers().getQuestions().getQuestion()));
            row.appendChild(respondentId);
            
            //add answer
            String ansString = "";
            
            //differentiate between free text answers and radio buttons.
            //Radio button answers are stored in "answers" table
            //Free text answers are stored in "respondentanswers" table
            if(answers.get(i).getAnswers().getQuestions().getQuestionTypeId()==4)
            {
                ansString = answers.get(i).getAnswerText();
            }
            else
            {
                 ansString = answers.get(i).getAnswers().getAnswer();
            }
            
            Element answer = doc.createElement("td");
            answer.setAttribute("class", "firstColumn");
            answer.appendChild(doc.createTextNode(ansString));
            row.appendChild(answer);
            
            table.appendChild(row);
        }
        
        return getTableHTML();
        
    }
    
    /**
     * Formats a list of users as a HTML table
     * @param users the list of users
     * @throws SQLException 
     */
    public String getUsersTable(List<Users> user)
    {
        String table = "<table class=\"userTable\">";
        
        //create top row
        table+=("<tr>");
        
        //create headers        
        table+= ("<th>User Id</th>");
        table+= ("<th>Username</th>");
        table+= ("<th>Password</th>");
        table+= ("<th>Email</th>");
        table+= ("<th>Phone Number</th>");
        table+= ("<th>Role</th>");
        table+= ("<th class=\"actions\">Actions</th>");
        
        table+=("</tr>");
        
        //loop to print the table
        for(int i = 0; i<user.size();i++)
        {
            table+=("<tr id=\"row"+user.get(i).getUserId()+"\">");
            
            //dynamically adjusts according to number of columns in metadata
            
            table+=("<td>"+user.get(i).getUserId()+"</td>");
            table+=("<td>"+user.get(i).getUsername()+"</td>");
            table+=("<td>******</td>");
            table+=("<td>"+user.get(i).getEmail()+"</td>");
            table+=("<td>"+user.get(i).getPhoneNumber()+"</td>");
            table+=("<td>"+user.get(i).getRole()+"</td>");
            
            table+=("<td class=\"actions\"><div class=\"editIcon\" ><span class=\"glyphicon glyphicon glyphicon-edit\" data-toggle=\"modal\" data-target=\"#editModal\" title=\"Edit\">");
            table+=("</span></div><div class=\"deleteIcon\" ><span class=\"glyphicon glyphicon-remove-sign\" data-toggle=\"modal\" data-target=\"#deleteModal\" title=\"Delete\"></span></div></td>");
            table+=("</tr>");
        }
        
        table+="<table>";
        
        return table;
    }

    /**
     * Formats a list of users as a HTML table
     * @param users the list of users
     * @throws SQLException 
     */
    public static String getQuestionsTable(List<Questions> questions)
    {
        String table = "<table class=\"userTable\">";
        
        //create top row
        table+=("<tr>");
        
        //create headers        
        table+= ("<th>Number</th>");
        table+= ("<th>Question Text</th>");
        table+= ("<th class=\"Stats\">View Chart</th>");
        table+= ("<th class=\"Stats\">6 Month Overview</th>");
        
        table+=("</tr>");
        
        //loop to print the table
        for(int i = 0; i<questions.size();i++)
        {
            table+=("<tr id=\"row"+(i+1)+"\">");
            
            table+=("<td>"+(i+1)+"</td>");
            table+=("<td>"+questions.get(i).getQuestion()+"</td>");            
            
            if(questions.get(i).getQuestionTypeId()==4)
            {
                table+=("<td class=\"actions\"><div class=\"statsIcon\" >"
                        + "<a class=\"statsLink\" href=\"/survey_results/survey_answers/freetext?questionId="+questions.get(i).getQuestionId()+"\">"
                        + "<span class=\"glyphicon glyphicon glyphicon-comment\" ></span></a></td>"); 
                table+="<td></td>";
            }
            else
            {
                table+=("<td class=\"actions\"><div class=\"statsIcon\" >"
                + "<span index = \""+i+"\"class=\"fa fa-pie-chart statIcon\" data-toggle=\"modal\" data-target=\"#addModal\" ></span></td>");
                
                table+=("<td class=\"actions\"><div class=\"statsIcon\" >"
                + "<a class=\"statsLink\" href=\"/survey_results/survey_answers/monthly_stats?questionId="+questions.get(i).getQuestionId()+"\">"
                + "<span index = \""+i+"\"class=\"fa fa-bar-chart statIcon\"></span></a></td>");
                
            }
            
            table+=("</tr>");
        }
        
        table+="<table>";
        
        return table;
    }
    
    /**
     * Formats a list of answers as a HTML table
     * @param users the list of users
     * @throws SQLException 
     */
    public static String getFreeTextTable(List<RespondentAnswers> answers)
    {
        String table = "<table class=\"userTable\">";
        
        //create top row
        table+=("<tr>");
        
        //create headers        
        table+= ("<th>Respondent</th>");
        table+= ("<th>Answer</th>");
        
        table+=("</tr>");
        
        //loop to print the table
        for(int i = 0; i<answers.size();i++)
        {
            table+=("<tr id=\"row"+answers.get(i).getRespondentId()+"\">");
            
            table+=("<td>"+i+"</td>");
            table+=("<td>"+answers.get(i).getAnswerText()+"</td>");     
            
            table+=("</tr>");
        }
        
        table+="<table>";
        
        return table;
    }
    
    /**
     * Returns xml as a string
     * @return 
     */
    private String getTableHTML()
    {
        //configure serialisation
        DOMImplementation impl = doc.getImplementation();
        DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
        LSSerializer ser = implLS.createLSSerializer();
        
        String htmlText = ser.writeToString(doc);
        
        return htmlText;
    }
    
}
