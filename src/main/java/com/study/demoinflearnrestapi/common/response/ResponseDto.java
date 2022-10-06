package com.study.demoinflearnrestapi.common.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

@Builder
@Getter
public class ResponseDto<T> {

    @Builder.Default
    private String code = ResponseMessage.SUCCESS_READ.getCode();

    @Builder.Default
    private String message = ResponseMessage.SUCCESS_READ.getValue();

    @Builder.Default
    private Object data = new HashMap<>();

    /*@Builder.Default
    private List<T> data = List.of();*/

    @Builder.Default
    private Object errors = List.of();

}
