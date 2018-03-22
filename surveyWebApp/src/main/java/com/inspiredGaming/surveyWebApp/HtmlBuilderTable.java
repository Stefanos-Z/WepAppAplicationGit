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
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
    public String buildSurveyTable(List<Surveys> surveys)
    {
        //add header row
        Element headerRow = doc.createElement("tr");

        //add surveyname
        Element surveyNameHeader = doc.createElement("th");
        surveyNameHeader.appendChild(doc.createTextNode("Survey Name"));
        headerRow.appendChild(surveyNameHeader);

        //add userid
        Element userIdHeader = doc.createElement("th");
        userIdHeader.appendChild(doc.createTextNode("user Id"));
        headerRow.appendChild(userIdHeader);

        //add creation date
        Element creationDateHeader = doc.createElement("th");
        creationDateHeader.appendChild(doc.createTextNode("Creation Date"));
        headerRow.appendChild(creationDateHeader);

        table.appendChild(headerRow);
        
        //create rows in the table
        for (int i =0; i<surveys.size();i++)
        {
            Element row = doc.createElement("tr");
            
            //add surveyname
            Element surveyName = doc.createElement("td");
            Element link = doc.createElement("a");
            link.setAttribute("href", "http://localhost:8080/survey_results/responses?survey="+surveys.get(i).getSurveyId());
            link.appendChild(doc.createTextNode(surveys.get(i).getSurveyName()));
            surveyName.appendChild(link);
            row.appendChild(surveyName);
            
            //add userid
            Element userId = doc.createElement("td");
            userId.appendChild(doc.createTextNode(""+surveys.get(i).getSurveyId()));
            row.appendChild(userId);
            
            //add creation date
            Element creationDate = doc.createElement("td");
            creationDate.appendChild(doc.createTextNode(""+surveys.get(i).getCreationDate()));
            row.appendChild(creationDate);
            
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
        surveyNameHeader.appendChild(doc.createTextNode("Survey Respondent"));
        headerRow.appendChild(surveyNameHeader);

        //add creation date
        Element creationDateHeader = doc.createElement("th");
        creationDateHeader.appendChild(doc.createTextNode("Response Date"));
        headerRow.appendChild(creationDateHeader);

        table.appendChild(headerRow);
        
        //create rows in the table
        for (int i =0; i<respondents.size();i++)
        {
            Element row = doc.createElement("tr");
            
            //add respondent
            Element respondentId = doc.createElement("td");
            Element link = doc.createElement("a");
            link.setAttribute("href", "http://localhost:8080/survey_results/responses/user?id="+respondents.get(i).getRespondentId());
            link.appendChild(doc.createTextNode(""+respondents.get(i).getRespondentId()));
            respondentId.appendChild(link);
            row.appendChild(respondentId);
            
            //add creation date
            Element creationDate = doc.createElement("td");
            creationDate.appendChild(doc.createTextNode(""+respondents.get(i).getSurveyDate()));
            row.appendChild(creationDate);
            
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
            answer.appendChild(doc.createTextNode(ansString));
            row.appendChild(answer);
            
            table.appendChild(row);
        }
        
        return getTableHTML();
        
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
