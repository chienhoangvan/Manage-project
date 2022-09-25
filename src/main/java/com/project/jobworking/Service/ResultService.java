package com.project.jobworking.Service;

import com.project.jobworking.Entity.Project;
import com.project.jobworking.Entity.Result;

import java.util.Date;

public interface ResultService {

    Result save(Result result);

    void updateResult(Long id, String resultName, String progress, String result);

}
