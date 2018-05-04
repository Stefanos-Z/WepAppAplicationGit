/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models.dao;

import com.inspiredGaming.surveyWebApp.models.EmailGroups;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Data Access object for EmailGroups
 * @authors     DALLOS
 * @version     1
 * @since       12/04/2018
 */
public interface EmailGroupsDao extends CrudRepository<EmailGroups, Integer>
{
    /**
     * getter for emailGroups, return all 
     * @return 
     */
    public List<EmailGroups> findAll();
    
    /**
     * getter for emailGroups, returns emailGroup by groupId
     * @param groupID
     * @return 
     */    
    public EmailGroups findByGroupID(Integer groupID);
    
    /**
     * getter for emailGroups, returns emailGroup by groupName
     * @param groupName
     * @return 
     */
    public EmailGroups findByGroupName(String groupName);
}
