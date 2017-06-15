package com.wenda.service;

import com.wenda.common.JsonResponse;
import com.wenda.pojo.Question;

/**
 * Created by chen on 2017/6/12.
 */
public interface IQuestionService {
    JsonResponse<String> addQueston(Question question);
}
