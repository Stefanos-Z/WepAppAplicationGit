/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models.dao;

import com.inspiredGaming.surveyWebApp.models.HelloLog;
import com.inspiredGaming.surveyWebApp.models.Respondents;
import com.inspiredGaming.surveyWebApp.models.StaffEmails;
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
public interface StaffEmailsDao extends CrudRepository<StaffEmails, Integer>{
    
    public List<StaffEmails> findAll();
    
    public StaffEmails findByStaffId(Integer staffId);
    
    public List<StaffEmails> findByGroupID(Integer groupID);
    
    
}
