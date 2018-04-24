/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models;

/**
 * Creates an object corresponding to an HelloMesage tuple in the DB.
 * @authors     DALLOS
 * @version     1
 * @since       20/03/2018
 */
public class HelloMessage {
    public static String getMessage(String name)
    {
        return "Hello "+name+"!";
    }
}
