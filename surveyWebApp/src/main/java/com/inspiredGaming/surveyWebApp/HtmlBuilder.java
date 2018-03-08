/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp;

import com.inspiredGaming.surveyWebApp.models.Answers;
import com.inspiredGaming.surveyWebApp.models.Questions;
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
public class HtmlBuilder {
    
    private Document doc;
    private Element form;
    
    public HtmlBuilder()
    {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
            
            //build form xml
            form = doc.createElement("form");
            
            doc.appendChild(form);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(HtmlBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addQuestion(Questions question, List<Answers> answers)
    {
        //create header tag
        Element header = doc.createElement("h2");
        header.appendChild(doc.createTextNode(question.getQuestion()));
        form.appendChild(header);
        
        //create answer options
        for (int i =0; i<answers.size();i++)
        {
            String questionType = getInputAttribute(question.getQuestionTypeId());
            Element input = doc.createElement("input");
            input.setAttribute("type", questionType);
            input.setAttribute("name", ""+question.getQuestionId());
            
            //find answer
            if(!questionType.equals("text"))
            {
                input.setAttribute("value", ""+answers.get(i).getAnswerId());
            }
            
            input.appendChild(doc.createTextNode(answers.get(i).getAnswer()));
            form.appendChild(input);
        }
        
    }

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
            inputType = "text";
        }
        
        return inputType;
    }

    public String getSurveyHTML()
    {
        //append submit Button
        form.appendChild(doc.createElement("br"));
        Element submitButton = doc.createElement("button");
        submitButton.setAttribute("type", "submit");
        submitButton.appendChild(doc.createTextNode("Submit Survey"));
        form.appendChild(submitButton);
            
        //configure serialisation
        DOMImplementation impl = doc.getImplementation();
        DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
        LSSerializer ser = implLS.createLSSerializer();
        
        String htmlText = ser.writeToString(doc);
        
        return htmlText;
    }
    
    
}
