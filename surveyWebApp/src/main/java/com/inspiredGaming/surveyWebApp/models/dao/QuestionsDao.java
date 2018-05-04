/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models.dao;

import com.inspiredGaming.surveyWebApp.models.HelloLog;
import com.inspiredGaming.surveyWebApp.models.Questions;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access object for QuestionsDao
 * @authors     DALLOS
 * @version     1
 * @since       12/04/2018
 */
@Repository
@Transactional
public interface QuestionsDao extends CrudRepository<Questions, Integer>{
    
    /**
     * getter for questions, returns all questions
     * @return 
     */
    public List<Questions> findAll();
    
    /**
     * getter for questions, returns questions by questionId
     * @param questionId
     * @return 
     */    
    public Questions findByQuestionId(Integer questionId);
    
    /**
     * getter for questions, returns questions by surveyId
     * @param surveyId
     * @return 
     */
    public List<Questions> findBySurveyId(Integer surveyId);
}
