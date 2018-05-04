/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp;

import com.inspiredGaming.surveyWebApp.models.Answers;
import com.inspiredGaming.surveyWebApp.models.Questions;
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
 * Contains methods to build the HTML for a survey.
 * @author      Levi Roque-Nunes
 * @version     1
 * @since       07/03/2018
 */
public class HtmlBuilderSurvey {
    
    private Document doc;
    private Element form;
    
    /**
     * Constructor which creates a new doc object.
     */
    public HtmlBuilderSurvey()
    {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
            
            //build form xml
            form = doc.createElement("form");
            
            doc.appendChild(form);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(HtmlBuilderSurvey.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * The HTML for a single question to the DOM.
     * @param question
     * @param answers
     * @param questionNumber 
     */
    public void addQuestion(Questions question, List<Answers> answers,int questionNumber)
    {
        int questionTypeId = question.getQuestionTypeId();
        
        //create div for new question
        Element div = doc.createElement("div");
        div.setAttribute("class", "question");
        
        //create header
        Element header = doc.createElement("h3");
        header.setAttribute("class", "question_text");
        if(questionTypeId == 5)
        {
            header.appendChild(doc.createTextNode(""+questionNumber+") "+question.getQuestion() + "(choose all that apply)"));
        }
        else if(questionTypeId == 2)
        {
            header.appendChild(doc.createTextNode(""+questionNumber+") "+question.getQuestion() + " (choose one)"));
        }
        else
        {
            header.appendChild(doc.createTextNode(""+questionNumber+") "+question.getQuestion()));
        }
        
        div.appendChild(header);
        form.appendChild(div);
        
        //create answer options
        for (int i =0; i<answers.size();i++)
        {
            String questionType = getInputAttribute(question.getQuestionTypeId());
            
            Element ans=null;
            
            //append textarea
            if(questionType.equals("textarea"))
            {
                ans = doc.createElement("textarea");
                ans.setAttribute("name", ""+question.getQuestionId());
                ans.setAttribute("cols", "100");
                ans.setAttribute("rows", "5");
                ans.setAttribute("value", ""+answers.get(i).getAnswerId());
            }
            //append radio buttons
            else
            {
                ans = doc.createElement("input");
                ans.setAttribute("type", questionType);
                ans.setAttribute("name", ""+question.getQuestionId());
                ans.setAttribute("value", ""+answers.get(i).getAnswerId());                
            }
            
            ans.appendChild(doc.createTextNode(answers.get(i).getAnswer()));
            form.appendChild(doc.createElement("br"));   //add carriage return
            form.appendChild(ans);
        }
        
    }

    /**
     * Helper method to convert an integer to an input attribute.
     * @param questionType
     * @return 
     */
    private String getInputAttribute(int questionType)
    {
        String inputType = "";
        if(questionType ==1)
        {
            inputType = "radio";
        }
        else if (questionType ==2)
        {
            inputType = "radio";
        }
        else if (questionType ==3)
        {
            inputType = "radio";
        }
        else if (questionType ==4)
        {
            inputType = "textarea";
        }
        else if (questionType ==5)
        {
            inputType = "checkbox";
        }
        
        return inputType;
    }

    /**
     * Prints the DOM as a string.
     * @return 
     */
    public String getSurveyHTML()
    {
        //append submit Button
        Element buttonDiv = doc.createElement("div");
        buttonDiv.setAttribute("id", "submitButton");
        form.appendChild(doc.createElement("br"));
        Element submitButton = doc.createElement("button");
        submitButton.setAttribute("type", "submit");
        submitButton.appendChild(doc.createTextNode("Submit Survey"));
        buttonDiv.appendChild(submitButton);
        form.appendChild(buttonDiv);
            
        //configure serialisation
        DOMImplementation impl = doc.getImplementation();
        DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
        LSSerializer ser = implLS.createLSSerializer();
        
        String htmlText = ser.writeToString(doc);
        
        return htmlText;
    }
    
    /**
     * Generates a HTML for a select box, containing surveys.
     * @param surveys
     * @return 
     */
    public String getSurveysSelect(List<Surveys> surveys)
    {
        String htmlText = "";
        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            
            //build form xml
            Element selectList = doc.createElement("select");
            
            doc.appendChild(form);
            
            //add all surveys to the form
            for(int i = 0;i<surveys.size();i++)
            {
                Element option = doc.createElement("option");
                option.setAttribute("value", ""+surveys.get(i).getSurveyId());
                option.appendChild(doc.createTextNode(surveys.get(i).getSurveyName()));
                selectList.appendChild(option);
            }
            
            //configure serialisation
            DOMImplementation impl = doc.getImplementation();
            DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
            LSSerializer ser = implLS.createLSSerializer();
            
            htmlText = ser.writeToString(doc);
            
            System.out.println(htmlText);
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(HtmlBuilderSurvey.class.getName()).log(Level.SEVERE, null, ex);
        }
        return htmlText;
    }
    
    
}
