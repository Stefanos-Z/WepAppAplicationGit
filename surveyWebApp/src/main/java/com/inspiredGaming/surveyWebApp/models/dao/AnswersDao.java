/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models.dao;

import com.inspiredGaming.surveyWebApp.models.Answers;
import com.inspiredGaming.surveyWebApp.models.Questions;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access object for AnswersDao
 * @authors     DALLOS
 * @version     1
 * @since       12/04/2018
 */
@Repository
@Transactional
public interface AnswersDao extends CrudRepository<Answers, Integer>{
    
    /**
     * getter for answers, returns List of all answers
     * @return 
     */
    public List<Answers> findAll();
    
    /**
     * getter for answers, return answers by answerId
     * @param answerId
     * @return 
     */
    public Answers findByAnswerId(Integer answerId);
    
    /**
     * getter for answers, returns answer by questions
     * @param questions
     * @return 
     */
    public List<Answers> findByQuestions(Questions questions);
}
