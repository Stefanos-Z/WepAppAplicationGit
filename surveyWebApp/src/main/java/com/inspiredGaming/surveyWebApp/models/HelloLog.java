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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Levi
 */

@Entity
@Table(name = "hellolog")

public class HelloLog {
    private String name;
    private Date timestamp;
    private int uid;
    
    public HelloLog(String name)
    {
        this.name = name;
        timestamp = new Date();
    }
    
    public HelloLog()
    {
        //needs to be empty for hibernate to work?
    }
    
    @Column(name="name")
    public String getName()
    {
        return name;
    }
    
    @Id
    @GeneratedValue //means hibernate will generate the value
    @NotNull
    @Column(name="uid", unique = true)
    public int getUid()
    {
        return uid;
    }
    
    @NotNull
    @Column(name="timestamp")
    public Date getTimestamp()
    {
        return timestamp;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setTimestamp(Date timestamp)
    {
        this.timestamp = timestamp;
    }
    
    public void setUid(int uid)
    {
        this.uid=uid;
    }
    
}
