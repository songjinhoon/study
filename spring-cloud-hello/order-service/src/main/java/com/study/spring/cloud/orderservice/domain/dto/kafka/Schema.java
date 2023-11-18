package com.study.spring.cloud.orderservice.domain.dto.kafka;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Builder
@Data
public class Schema {

    private String type;

    private List<Field> fields;

    private boolean optional;

    private String name;

}
