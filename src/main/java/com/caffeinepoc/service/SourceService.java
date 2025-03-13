package com.caffeinepoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.caffeinepoc.repository.SourceRepository;
import com.caffeinepoc.repository.entity.Source;

import static com.caffeinepoc.CacheConfig.SOURCE_INFO_CACHE;

@Service
public class SourceService {

    @Autowired
    private SourceRepository sourceRepository;

    /**
     * Esto cacheará una colección de clave/valor siendo
     *
     * clave:sourcecode
     * valor:Source
     * @param sourceCode
     * @return
     */
    @Cacheable(value=SOURCE_INFO_CACHE,unless = "#result==null")
    public Source findSourceBySourceCode(String sourceCode) {
        return sourceRepository.findSourceBySourceCode(sourceCode);
    }
}
