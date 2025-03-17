package com.caffeinepoc.repository.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Source {

    private String name;
    private String id;
}
