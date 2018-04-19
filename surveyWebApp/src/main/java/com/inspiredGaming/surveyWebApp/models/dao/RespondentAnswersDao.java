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
    
    @Query(value = "SELECT a.answer," +
                    "case when rar.respondent_id is null then 0 " +
                    "else count(*) " +
                    "	   end as count " +
                    "FROM questions AS q " +
                    "LEFT JOIN answers as a on a.question_id = q.question_id " +
                    "LEFT JOIN " +
                    "	(SELECT ra.respondent_id,r.survey_date,ra.answer_id from respondent_answers AS ra " +
                    "    INNER JOIN respondents as r on r.respondent_id = ra.respondent_id " +
                    "	 WHERE concat(month(survey_date),year(survey_date)) = concat(month(curdate()-interval :months month),year(curdate()-interval :months month))) " +
                    "AS rar ON rar.answer_id = a.answer_id " +
                    "WHERE q.question_id = :questionId " +
                    "GROUP BY a.answer_id,a.answer " +
                    "ORDER BY a.answer_id ASC", nativeQuery=true)
    public List<AnswerCount> countAnswersByQuestionAndMonth(@Param("questionId") int questionId,@Param("months") int months);
    
    @Query(value = "SELECT 'Average Score' AS answer, CASE WHEN answer_weight is null then 0 else AVG(answer_weight) end as count FROM questions AS q\n" +
                    "LEFT JOIN \n" +
                    "	(SELECT ra.respondent_id,r.survey_date,ra.answer_id,a.question_id,a.answer_weight\n" +
                    "	 FROM answers AS a\n" +
                    "     INNER JOIN respondent_answers AS ra ON ra.answer_id = a.answer_id\n" +
                    "     INNER JOIN respondents as r on r.respondent_id = ra.respondent_id\n" +
                    "	 WHERE concat(month(survey_date),year(survey_date)) = concat(month(curdate()-interval :months month),year(curdate()-interval :months month))) " +
                    "AS a ON a.question_id = q.question_id\n" +
                    "WHERE q.question_id= :questionId", nativeQuery=true)
    public List<AnswerCount> avgAnswersByQuestionAndMonth(@Param("questionId") int questionId,@Param("months") int months);
    
}
