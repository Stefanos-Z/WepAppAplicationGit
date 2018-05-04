/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models.dao;

import com.inspiredGaming.surveyWebApp.models.Surveys;
import com.inspiredGaming.surveyWebApp.models.Questions;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access object for SurveysDao
 * @authors     DALLOS
 * @version     1
 * @since       12/04/2018
 */
@Repository
@Transactional
public interface SurveysDao extends CrudRepository<Surveys, Integer>{
    
    /**
     * Gets all surveys from the database
     * @return a list of surveys
     */
    public List<Surveys> findAll();
    
    /**
     * Gets a Survey from the database
     * @param surveyId the survey's id 
     * @return the Survey
     */
    public Surveys findBySurveyId(Integer surveyId);
    
}
