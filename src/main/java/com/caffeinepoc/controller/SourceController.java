package com.caffeinepoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caffeinepoc.repository.entity.Source;
import com.caffeinepoc.service.SourceService;

@RequestMapping("/source")
@RestController
public class SourceController {

    @Autowired
    private SourceService sourceService;

    @GetMapping("/{sourcecode}")
    public Source getSourceBySourceCode(@PathVariable("sourcecode")String sourceCode) {
        return sourceService.findSourceBySourceCode(sourceCode);
    }
}
