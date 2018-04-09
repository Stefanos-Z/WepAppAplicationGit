/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models.dao;

import com.inspiredGaming.surveyWebApp.models.Answers;
import com.inspiredGaming.surveyWebApp.models.HelloLog;
import com.inspiredGaming.surveyWebApp.models.RespondentAnswers;
import com.inspiredGaming.surveyWebApp.models.Respondents;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *  DATA ACCESS OBJECT
 * @author Levi
 *  interface is used on the back end by hibernate, so it
 *  doesn't need to be manually implemented anywhere
 */
@Repository
@Transactional
public interface RespondentAnswersDao extends CrudRepository<RespondentAnswers, Integer>{
    
    public List<RespondentAnswers> findAll();
    
    public Respondents findByRespondentAnswerId(Integer respondentAnswerId);
    
    public List<RespondentAnswers> findByRespondentId(Integer respondentId);
    
    public List<RespondentAnswers> findByAnswers(Answers answer);
    
    
    @Query(value = "SELECT ANSWER,count(Y.ANSWER_ID) AS COUNT " +
                    "FROM ANSWERS AS X LEFT JOIN RESPONDENT_ANSWERS AS Y ON X.ANSWER_ID = Y.ANSWER_ID " +
                    "WHERE QUESTION_ID=:questionId " +
                    "GROUP BY answer " +
                    "ORDER BY X.ANSWER_ID ASC", nativeQuery=true)
    public List<AnswerCount> countAnswersByQuestion(@Param("questionId") int questionId);
    
}
