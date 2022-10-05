package com.study.demoinflearnrestapi.domain.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class EventResource extends RepresentationModel {

    private String code;

    private String message;

    private Object event;

    public EventResource(Object object, String code, String message) {
        this.event = object;
        this.code = code;
        this.message = message;
    }

}
/*
public class EventResource extends EntityModel<Event> {

    private String code;

    private String message;

    public EventResource(String code, String message, Event event, Link... links) {
        super(event, Arrays.asList(links));
        this.code = code;
        this.message = message;
    }

}
*/
