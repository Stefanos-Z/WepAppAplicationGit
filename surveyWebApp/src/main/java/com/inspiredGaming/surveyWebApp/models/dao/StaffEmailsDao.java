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
 * Data Access object for StaffEmailsDao
 * @authors     DALLOS
 * @version     1
 * @since       12/04/2018
 */
@Repository
@Transactional
public interface StaffEmailsDao extends CrudRepository<StaffEmails, Integer>{
    
    /**
     * Gets all Staff emails
     * @return a list of StaffEmails
     */
    public List<StaffEmails> findAll();
    
    /**
     * Gets a Staff's email 
     * @param staffId the staff's id
     * @return a StaffEmails
     */
    public StaffEmails findByStaffId(Integer staffId);
    
    /**
     * Gets a list of staff emails
     * @param groupID the group id of the emails
     * @return a list of StaffEmails
     */
    public List<StaffEmails> findByGroupID(Integer groupID);
    
    
}
