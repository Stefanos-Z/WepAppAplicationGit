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
    public List<EmailGroups> findAll();
    
    public EmailGroups findByGroupID(Integer groupID);
    
    public EmailGroups findByGroupName(String groupName);
}
