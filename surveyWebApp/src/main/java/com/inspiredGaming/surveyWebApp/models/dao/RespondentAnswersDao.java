/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models.dao;

import com.inspiredGaming.surveyWebApp.models.HelloLog;
import com.inspiredGaming.surveyWebApp.models.RespondentAnswers;
import com.inspiredGaming.surveyWebApp.models.Respondents;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *  DATA ACCESS OBJECT
 * @author Levi
 *  interface is used on the back end by hibernate, so it
 *  doesn't need to be manually implemented anywhere
 */
@Repository
@Transactional
public interface RespondentAnswersDao extends CrudRepository<RespondentAnswers, Integer>{
    
    public List<RespondentAnswers> findAll();
    
    public Respondents findByRespondentAnswerId(Integer respondentAnswerId);
    
    public List<RespondentAnswers> findByRespondentId(Integer respondentId);
    
}
