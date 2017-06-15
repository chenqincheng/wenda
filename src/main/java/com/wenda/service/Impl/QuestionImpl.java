package com.wenda.service.Impl;

import com.wenda.common.JsonResponse;
import com.wenda.dao.QuestionMapper;
import com.wenda.pojo.Question;
import com.wenda.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chen on 2017/6/12.
 */
@Service("iQuestionService")
public class QuestionImpl implements IQuestionService {
    @Autowired
    QuestionMapper questionMapper;

    @Override
    public JsonResponse<String> addQueston(Question question) {
        int resultCount = questionMapper.insert(question);
        if (resultCount>0){
            return JsonResponse.returnSuccessMessage("添加问题成功！");
        }
        return JsonResponse.returnErrorMessage("添加问题失败！");
    }
}
