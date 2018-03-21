/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.XMLParser;

import com.inspiredGaming.surveyWebApp.models.Questions;
import com.inspiredGaming.surveyWebApp.models.Surveys;
import com.inspiredGaming.surveyWebApp.models.dao.AnswersDao;
import com.inspiredGaming.surveyWebApp.models.dao.QuestionsDao;
import com.inspiredGaming.surveyWebApp.models.dao.SurveysDao;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author oneZt
 */
public class QuestionsParser {
    
    @Autowired
    private SurveysDao surveysDao;
    
    @Autowired
    private QuestionsDao questionsDao;
    
    @Autowired
    private AnswersDao answersDao;
    
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
        
        //test:survey to database
        
        //save survey to the database
        Surveys survey  = new Surveys(surveyName,1);
        surveysDao.save(survey);
        
        for(int i = 0; i<doc.getElementsByTagName("question").getLength();i++)
        {
            
            System.out.println(doc.getElementsByTagName("question").item(i).getTextContent());
            
            
            Element qE = (Element)doc.getElementsByTagName("question").item(i);
            
            String questionText = qE.getElementsByTagName("questionText").item(0).getTextContent();
            String questionType = qE.getElementsByTagName("questionType").item(0).getTextContent();
            
            int questionTypeId =2;
            
            if(questionType.equals("Open Text"))
            {
                questionTypeId = 4;
            }
            
            
            Questions question = new Questions(questionText,questionTypeId,survey.getSurveyId());
            
            questionsDao.save(question);
        }
        
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
