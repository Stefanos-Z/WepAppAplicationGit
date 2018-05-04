/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models.dao;

import com.inspiredGaming.surveyWebApp.models.SurveyKeys;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access object for SurveyKeysDao
 * @authors     DALLOS
 * @version     1
 * @since       12/04/2018
 */
@Repository
@Transactional
public interface SurveyKeysDao extends CrudRepository<SurveyKeys, Integer>{
    
    /**
     * Gets all surveyKeys from the database
     * @return a list of surveyKeys
     */
    public List<SurveyKeys> findAll();
    
    /**
     * Gets a SurveyKey 
     * @param keyId of the SurveyKeys
     * @return the survey key
     */
    public SurveyKeys findByKeyId(String keyId);
}
