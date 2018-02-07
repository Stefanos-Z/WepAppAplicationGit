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
 *
 * @author Levi
 */
@Repository
@Transactional
public interface RespondentsDao extends CrudRepository<Respondents, Integer>{
    
    public List<Respondents> findAll();
    
    public Respondents findByRespondentId(Integer respondentId);
}
