/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.XMLParser;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
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
        Document doc = builder.parse(new File(filename));
        int questionCount = Integer.parseInt(path.evaluate("count(/survey/questions/question)", doc));
        
        System.out.println("\n\n");
        
        for(int i = 0; i<questionCount;i++)
        {
            String quesTitle = path.evaluate("/survey/questions/question[" + (i+1) + "]/title",doc);
            System.out.println("QuestionTitle: " + quesTitle);
            int answerCount = Integer.parseInt(path.evaluate("count(/survey/questions/question["+(i+1)+"]/answers/answer)",doc));
            System.out.println("this is the answer count "+answerCount);
            for(int j = 0; j<answerCount;j++)
            {
                String thisAnswer = path.evaluate("/survey/questions/question["+(i+1)+"]/answers/answer["+(j+1)+"]",doc);
                System.out.println("Answer"+(j+1)+" "+thisAnswer);
            }
            String type = path.evaluate("/survey/questions/question["+(i+1)+"]/type",doc);
            System.out.println("Type: "+type);
            String required = path.evaluate("/survey/questions/question["+(i+1)+"]/required",doc);
            System.out.println("Required "+required);
            String aggregatted = path.evaluate("/survey/questions/question["+(i+1)+"]/aggregated",doc);
            System.out.println("Aggregated: "+aggregatted);
            System.out.println("\n\n");
            
        }
        String sss = path.evaluate("/survey/title",doc);
        
        return sss;
    }
    
}
