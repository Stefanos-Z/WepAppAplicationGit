/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *  Database email groups
 * @author Stefan
 */

@Entity
@Table(name = "emailGroups")
public class EmailGroups {
    
    private int groupID;
    
    private String groupName;
    
    private Date dateCreated;

    public EmailGroups(String groupName) {
        this.groupName = groupName;
        dateCreated = new Date();
    }
    


    
    
    public EmailGroups()
    {
        //needs to be empty for hibernate to work
    }    

    
    

    
    @Id
    @GeneratedValue 
    @NotNull
    @Column(name="groupID", unique = true)
    public int getGroupID() {
        return groupID;
    }



    @NotNull
    @Column(name="groupName")
    public String getGroupName() {
        return groupName;
    }

    @NotNull
    @Column(name="dateCreated")
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    
    
    
    
    
    
    

}
