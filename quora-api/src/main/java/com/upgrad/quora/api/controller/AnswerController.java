package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.AnswerService;
import com.upgrad.quora.service.business.QuestionService;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    /**
     * @param questionId
     * @param answerRequest
     * @param accessToken
     * @return the UUID and the status of the answer
     * @throws AuthorizationFailedException
     * @throws InvalidQuestionException
     */
    @RequestMapping(method = RequestMethod.POST, path = "/question/{questionId}/answer/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnswerResponse> createAnswer(@PathVariable("questionId") String questionId, @RequestBody AnswerRequest answerRequest, @RequestHeader("authorization") String accessToken) throws AuthorizationFailedException, InvalidQuestionException {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setAnswer(answerRequest.getAnswer());

        QuestionEntity questionEntity = questionService.getQuestionById(questionId, accessToken);
        answerEntity.setQuestionEntity(questionEntity);
        answerEntity = answerService.createAnswer(answerEntity, accessToken);

        AnswerResponse answerResponse = new AnswerResponse();
        answerResponse.setId(answerEntity.getUuid().toString());
        answerResponse.setStatus("ANSWER CREATED");
        return new ResponseEntity<>(answerResponse, HttpStatus.CREATED);

    }

    /**
     * @param accessToken
     * @param answerId
     * @param answerEditRequest
     * @return the edited answer uuid and the status
     * @throws AuthorizationFailedException
     * @throws AnswerNotFoundException
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/answer/edit/{answerId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerEditResponse> editAnswer(@RequestHeader("authorization") final String accessToken, @PathVariable("answerId") final String answerId, AnswerEditRequest answerEditRequest) throws AuthorizationFailedException, AnswerNotFoundException {
        AnswerEditResponse answerEditResponse = new AnswerEditResponse();
        AnswerEntity answerEntity = answerService.editAnswer(accessToken, answerId, answerEditRequest.getContent());
        answerEditResponse.setId(answerEntity.getUuid().toString());
        answerEditResponse.setStatus("ANSWER EDITED");
        return new ResponseEntity<>(answerEditResponse, HttpStatus.OK);
    }

}
