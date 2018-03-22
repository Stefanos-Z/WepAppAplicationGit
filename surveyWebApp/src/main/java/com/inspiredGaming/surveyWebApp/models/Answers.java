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
 *
 * @author Levi
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

    
    public Answers(String answer, Questions question, Integer answerWeight) {
        this.answer = answer;
        this.questions = questions;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "questionId", nullable = false)
    public Questions getQuestions() {
        return questions;
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

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

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