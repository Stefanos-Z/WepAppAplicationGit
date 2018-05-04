/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Creates an object corresponding to an Answers tuple in the DB.
 * @authors     DALLOS
 * @version     1
 * @since       10/02/2018
 */

@Entity
@Table(name = "answers")
public class Answers {
    
    private int answerId;
    private String answer;
    //private int questionId;
    private Integer answerWeight;
    
    private Questions questions;
    
    //private Set<Answers> answers = new HashSet<>();

    /**
     * Constructor for the class.
     * @param answer
     * @param questions
     * @param answerWeight 
     */
    public Answers(String answer, Questions questions, Integer answerWeight) {
        this.answer = answer;
        this.questions = questions;
        this.answerWeight = answerWeight;
    }
    
    
    //empty constructor is required by hibernate
    public Answers()
    {
        
    }
       
    
    /**
     * returns answer_id
     * @return 
     */
    @Id
    @GeneratedValue //means hibernate will generate the value
    @NotNull
    @Column(name="answerId", unique = true)
    public int getAnswerId()
    {
        return answerId;
    }
    
    /**
     * returns the answer object
     * @return 
     */
    @NotNull
    @Column(name="answer")
    public String getAnswer()
    {
        return answer;
    }

    /**
     * get questions
     * @return 
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "questionId", nullable = false)
    public Questions getQuestions() {
        return questions;
    }

    /**
     * returns the answer weight.
     * @return 
     */
    @Column(name="answerWeight")
    public Integer getAnswerWeight() {
        return answerWeight;
    }

    /**
     * sets the answer id
     * @param answerId 
     */
    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    /**
     * sets the answer id
     * @param answer 
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * sets the question
     * @param questions 
     */
    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

    /**
     * sets the answerweight
     * @param answerWeight 
     */
    public void setAnswerWeight(Integer answerWeight) {
        this.answerWeight = answerWeight;
    }
    
    /*
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "answers")
    public Set<Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answers> answers) {
        this.answers = answers;
    }*/
    
}