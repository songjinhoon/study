package com.study.demoinflearnrestapi.domain.event;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/*@Getter
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

}*/

public class EventResource extends EntityModel<Event> {

    public EventResource(Event event, Link... links) {
        super(event, Arrays.asList(links));
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }

}
