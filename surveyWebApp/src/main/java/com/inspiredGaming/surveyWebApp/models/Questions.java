/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Creates an object corresponding to an Questions tuple in the DB.
 * @authors     DALLOS
 * @version     1
 * @since       07/03/2018
 */

@Entity
@Table(name = "questions")
public class Questions {

    private int questionId;
    private String question;
    private int questionTypeId;
    private int surveyId;
    
    /**
     * constructor for the class
     * @param question
     * @param questionTypeId
     * @param surveyId 
     */
    
    public Questions(String question, int questionTypeId, int surveyId)
    {
        this.question = question;
        this.questionTypeId = questionTypeId;
        this.surveyId = surveyId;
    }
    
    public Questions()
    {
        //needs to be empty for hibernate to work
    }
    
     /**
      * getter for questionid
      * @return 
      */
    @Id
    @GeneratedValue 
    @NotNull
    @Column(name="questionId", unique = true)
    public int getQuestionId()
    {
        return questionId;
    }
    
    /**
     * getter for question
     * @return 
     */
    @NotNull
    @Column(name="question")
    public String getQuestion()
    {
        return question;
    }
    
    /**
     * getter for questionTypeId
     * @return 
     */
    @NotNull
    @Column(name="questionTypeId")
    public int getQuestionTypeId()
    {
        return questionTypeId;
    }
    
    /**
     * getter for surveyId
     * @return 
     */
    @Column(name="surveyId")
    public int getSurveyId()
    {
        return surveyId;
    }

    /**
     * getter for questionId
     * @param questionId 
     */
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    /**
     * setter for question
     * @param question 
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * setter for questionTypeId
     * @param questionTypeId 
     */
    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    /**
     * setter for surveyId
     * @param surveyId 
     */
    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }
    
    

}
