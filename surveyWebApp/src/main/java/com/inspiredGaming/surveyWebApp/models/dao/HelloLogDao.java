/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models.dao;

import com.inspiredGaming.surveyWebApp.models.HelloLog;
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
public interface HelloLogDao extends CrudRepository<HelloLog, Integer>{
    
    public List<HelloLog> findAll();
    
    public HelloLog findByUid(Integer uid);
}
