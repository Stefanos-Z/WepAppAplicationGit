/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models.dao;


import com.inspiredGaming.surveyWebApp.models.Users;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access object for UsersDao
 * @authors     DALLOS
 * @version     1
 * @since       12/04/2018
 */
@Repository
@Transactional
public interface UsersDao extends CrudRepository<Users, Integer>
{
    /**
     * Returns a list of all users
     * @return a list of Users from the database
     */
    public List<Users> findAll();
    
    /**
     * Receives from the database a Users
     * @param userId the userID of the user that needs to be found
     * @return the Users
     */
    public Users findByUserId(Integer userId);
    
    /**
     * Receives from the database a Users
     * @param username of the username of the Users 
     * @return the User
     */
    public Users findByUsername(String username);

}
