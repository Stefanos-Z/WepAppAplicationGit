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
    public List<Users> findAll();
    
    public Users findByUserId(Integer userId);
    
    public Users findByUsername(String username);

}
