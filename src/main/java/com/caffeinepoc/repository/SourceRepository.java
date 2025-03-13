package com.caffeinepoc.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.caffeinepoc.repository.entity.Source;

@Repository
public class SourceRepository implements InitializingBean {

    private Map<String,Source> sources;
    public Source findSourceBySourceCode(String sourceCode) {
        return Optional.ofNullable(sources.get(sourceCode)).orElseThrow(() -> new RuntimeException("source not found"));
    }

    @Override
    public void afterPropertiesSet(){
        sources = new HashMap<>();
        sources.put("source1",Source.builder().name("source1").id("1").build());
        sources.put("source2",Source.builder().name("source2").id("2").build());
        sources.put("source3",Source.builder().name("source3").id("3").build());
        sources.put("source4",Source.builder().name("source4").id("4").build());
        sources.put("source5",Source.builder().name("source5").id("5").build());
        sources.put("source6",null);


    }
}
