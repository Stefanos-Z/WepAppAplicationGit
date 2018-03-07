/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models;

/**
 *
 * @author eeu823
 */
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Levi
 */

@Entity
@Table(name = "answers")
public class Answers {
    
    private int answerId;
    private String answer;
    private int questionId;
    private Integer answerWeight;

    public Answers(int answerId, String answer, int questionId, Integer answerWeight) {
        this.answerId = answerId;
        this.answer = answer;
        this.questionId = questionId;
        this.answerWeight = answerWeight;
    }
    
    
    
    public Answers()
    {
        
    }
        
    @Id
    @GeneratedValue //means hibernate will generate the value
    @NotNull
    @Column(name="answerId", unique = true)
    public int getAnswerId()
    {
        return answerId;
    }
    
    
    @NotNull
    @Column(name="answer")
    public String getAnswer()
    {
        return answer;
    }

    @NotNull
    @Column(name="questionId")
    public int getQuestionId() {
        return questionId;
    }

    @Column(name="answerWeight")
    public Integer getAnswerWeight() {
        return answerWeight;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setAnswerWeight(Integer answerWeight) {
        this.answerWeight = answerWeight;
    }
    
    
    
}