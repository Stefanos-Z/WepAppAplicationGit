/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models.dao;

import com.inspiredGaming.surveyWebApp.models.HelloLog;
import com.inspiredGaming.surveyWebApp.models.Respondents;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access object for RespondentsDao
 * @authors     DALLOS
 * @version     1
 * @since       12/04/2018
 */
@Repository
@Transactional
public interface RespondentsDao extends CrudRepository<Respondents, Integer>{
    
    /**
     * Gets all respondents in the database 
     * @return a list of all respondents
     */
    public List<Respondents> findAll();
    
    /**
     * Gets a respondent  on the database based on
     * @param respondentId the id of the respondent
     * @return a Respondents
     */
    public Respondents findByRespondentId(Integer respondentId);
    
    /**
     * Gets a list of respondents from the database based 
     * @param surveyId the surveys ID
     * @return return a list of respondent
     */
    public List<Respondents> findBySurveyId(Integer surveyId);
}
