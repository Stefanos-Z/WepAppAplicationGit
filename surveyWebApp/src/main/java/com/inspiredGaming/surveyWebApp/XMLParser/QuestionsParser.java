/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.XMLParser;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author oneZt
 */
public class QuestionsParser {
    private DocumentBuilder builder;
    private XPath path;
    
    public QuestionsParser() throws ParserConfigurationException
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        builder = dbFactory.newDocumentBuilder();
        
        XPathFactory xpfactory = XPathFactory.newInstance();
        path = xpfactory.newXPath();
    }
    
    public String parse(String filename) throws SAXException, IOException, XPathExpressionException
    {
        
        //System.out.println(filename);
        InputSource is = new InputSource(new StringReader(filename));
        Document doc = builder.parse(is);
        
        System.out.println("\n\n");
        String surveyName = path.evaluate("/survey/surveyName" ,doc);
        String userId = path.evaluate("/survey/userId" ,doc);
        System.out.println("survey name :"+surveyName);
        System.out.println("user ID :"+userId);
        int questionCount = Integer.parseInt(path.evaluate("count(/survey/questions/question)", doc));
        for(int i = 0; i<questionCount;i++)
        {
            String quesTitle = path.evaluate("/survey/questions/question[" + (i+1) + "]/questionText",doc);
            System.out.println("QuestionTitle: " + quesTitle);
            String quesType = path.evaluate("/survey/questions/question[" + (i+1) + "]/questionType",doc);
            System.out.println("QuestionType: " + quesType);
            int answerCount = Integer.parseInt(path.evaluate("count(/survey/questions/question["+(i+1)+"]/answers/answerText)",doc));
            System.out.println("this is the answer count "+answerCount);
            for(int j = 0; j<answerCount;j++)
            {
                String answerText = path.evaluate("/survey/questions/question["+(i+1)+"]/answers/answerText["+(j+1)+"]",doc);
                System.out.println("Answer"+(j+1)+" text "+answerText);
            }

            System.out.println("\n\n");
            
        }

        
        return surveyName;

    }
    
}
