/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.controllers;

import com.inspiredGaming.surveyWebApp.models.Answers;
import com.inspiredGaming.surveyWebApp.models.Questions;
import com.inspiredGaming.surveyWebApp.models.dao.AnswersDao;
import com.inspiredGaming.surveyWebApp.models.dao.QuestionsDao;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Owain
 */
@Controller
public class EditSurveyController {
    
    @Autowired
    private QuestionsDao qDao;
    
    @Autowired
    private AnswersDao aDao;
    
    
    @RequestMapping(value = "/surveyEditor", method = RequestMethod.GET)
    public String EditSurvey(HttpServletResponse responce, HttpServletRequest request, Model model)
    {
        int surveyId = Integer.parseInt(request.getParameter("surveyId"));
        List<Questions> questionsArray = qDao.findBySurveyId(surveyId);
        String toReturn = "";    
    
        
        toReturn += "<div th:replace=\"template :: navbar\"/>";
        toReturn += "<div class =\"sbuilderheader\">Survey Name:";
        toReturn += "<input id =\"surveyName\" type=\"text\" name=\"sName\" class=\"textbox\"></input>";
        toReturn += "<button onclick=\"createQuestion()\" type=\"button\" name=\"createQ\">Add Question</button>";
        toReturn += "<form id=\"submitForm\" action=\"surveyBuilder\" onsubmit=\"return constructString()\" method=\"post\">";
        toReturn += "<input type=\"text\" id=\"xmlForm\" name=\"mytextform\" value=\"\" style=\"display:none\"></input>";
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
            toReturn += "<option value=\"RadioButtons\">Radio Button</option>";
            toReturn += "<option value=\"ScoreRange\">Score Range</option>";
            toReturn += "<option value=\"OpenText\">Open Text</option></select>";
            toReturn += "<button onclick=\"deleteQuestion("+i+")\" type=\"button\" name=\"deleteQ\">Delete Question</button>";
            toReturn += "</form>";
            toReturn += "<button id=\"createAnswer"+i+"\" onclick=\"createAnswer("+i+")\" type=\"button\" name=\"createQ\">Create Answer</button>";
            

            //loop through answers       
            List<Answers> answers = aDao.findByQuestions(questionsArray.get(i));

            for(int j = 0; j < answers.size(); j++)
            {
                String answerText = answers.get(j).getAnswer();
                toReturn += "<div id=\"ansDiv"+i+"\">Answer";
                toReturn += "<input id=ans"+i+" class=\"textbox\" name=\"answer"+i+"\" type=\"text\" value=\""+answerText+"\" >";
                toReturn +=  "<span onclick=\"deleteAnswer("+i+")\" id=\"deleteIcon\">   ×</span>";
                toReturn += "</div>";
            }   
            toReturn += "</div>";                  
        }
        toReturn += "<div id=\"div"+questionsArray.size()+"\" class=\"questions\">";
        model.addAttribute("surveyEditorPage", toReturn);
        return "ourSurveyEditor";
    }    
}
