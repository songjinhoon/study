package com.study.demoinflearnrestapi.common.response;

import com.study.demoinflearnrestapi.index.IndexController;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter @Setter
public class ErrorResource extends RepresentationModel {

    private String code;

    private String message;

    private Object errors;

    public ErrorResource(Errors errors) {
        this.errors = errors;
        this.code = ResponseMessage.NOT_VALID_REQUEST_DATA_EXCEPTION.getCode();
        this.message = ResponseMessage.NOT_VALID_REQUEST_DATA_EXCEPTION.getValue();
        add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
    }

}
