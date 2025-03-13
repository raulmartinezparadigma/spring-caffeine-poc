package com.caffeinepoc.repository.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Source {

    private String name;
    private String id;
}
