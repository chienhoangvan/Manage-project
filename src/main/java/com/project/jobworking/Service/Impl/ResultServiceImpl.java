package com.project.jobworking.Service.Impl;

import com.project.jobworking.Entity.Project;
import com.project.jobworking.Entity.Result;
import com.project.jobworking.Repository.ResultRepository;
import com.project.jobworking.Service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Override
    public Result save(Result result) {
        resultRepository.save(result);
        return result;
    }

    @Override
    public void updateResult(Long id, String resultName, String progress, String result) {
        Result resultObject = resultRepository.findById(id).get();
        resultObject.setResultName(resultName);
        resultObject.setProgress(progress);
        resultObject.setResult(result);
        this.save(resultObject);
    }
}
