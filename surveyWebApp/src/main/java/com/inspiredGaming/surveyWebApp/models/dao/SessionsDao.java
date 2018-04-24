/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models.dao;

import com.inspiredGaming.surveyWebApp.models.Answers;
import com.inspiredGaming.surveyWebApp.models.Sessions;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Data Access object for SessionsDao
 * @authors     DALLOS
 * @version     1
 * @since       12/04/2018
 */
public interface SessionsDao extends CrudRepository<Sessions, String>
{
    public List<Sessions> findAll();
    
    public Sessions findBySessionId(String sessionId);

}
