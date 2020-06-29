package com.zwb.blog.controller;

import com.zwb.blog.common.Result;
import com.zwb.blog.entity.Label;
import com.zwb.blog.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    ILabelService service;

    @GetMapping
    public Result getLabels(){
        List<Label> labels = service.getLabels();
        return Result.ok().put("labels",labels);
    }
}
