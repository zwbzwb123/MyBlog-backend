package com.zwb.blog.service.impl;

import com.zwb.blog.entity.Label;
import com.zwb.blog.mapper.LabelMapper;
import com.zwb.blog.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService implements ILabelService {

    @Autowired
    LabelMapper mapper;

    @Override
    public List<Label> getLabels() {
        List<Label> labels = mapper.getLabels();
        return labels;
    }
}
