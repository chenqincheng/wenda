package com.wenda.service;

import com.wenda.common.JSONResponse;
import com.wenda.pojo.Question;

/**
 * Created by chen on 2017/6/12.
 */
public interface IQuestionService {
    JSONResponse<String> addQueston(Question question);
    Question selectQuestionByQuestionId(Integer questionId);
}
