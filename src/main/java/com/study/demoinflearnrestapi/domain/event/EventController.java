package com.study.demoinflearnrestapi.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RequestMapping(value = "/api/event", produces = MediaTypes.HAL_JSON_VALUE)
@RestController
public class EventController {

    private final EventRepository eventRepository;

    private final EventValidator eventValidator;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
        if (errors.hasErrors()) {
            // MethodArgumentNotValidException.class, HttpMessageNotReadableException.class
            return ResponseEntity.internalServerError().build();
        }
        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.internalServerError().build();
        }

        Event event = EventMapper.INSTANCE.toEvent(eventDto);
        Event saveEvent = eventRepository.save(event);
        URI uri = linkTo(EventController.class).slash(saveEvent.getId()).toUri();
        return ResponseEntity.created(uri).body(event);
    }

}
/*
*  강의에서는 ModelMapper를 사용했으나 나는 Mapstruct로 진행해보겠음
* */
