package com.study.demoinflearnrestapi.domain.event;

import com.study.demoinflearnrestapi.common.response.ResponseDto;
import com.study.demoinflearnrestapi.common.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
    public ResponseEntity<?> createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
        if (errors.hasErrors()) {
            // MethodArgumentNotValidException.class, HttpMessageNotReadableException.class
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .code(ResponseMessage.NOT_VALID_REQUEST_DATA_EXCEPTION.getCode())
                    .message(ResponseMessage.NOT_VALID_REQUEST_DATA_EXCEPTION.getValue())
                    .errors(errors)
                    .build());
        }
        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .code(ResponseMessage.NOT_VALID_REQUEST_DATA_EXCEPTION.getCode())
                    .message(ResponseMessage.NOT_VALID_REQUEST_DATA_EXCEPTION.getValue())
                    .errors(errors)
                    .build());
        }
        Event event = EventMapper.INSTANCE.toEvent(eventDto);
        event.update();
        Event saveEvent = eventRepository.save(event);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(saveEvent.getId());
        URI createdUri = selfLinkBuilder.toUri();

        EventResource eventResource = new EventResource(event, ResponseMessage.SUCCESS_CREATE.getCode(), ResponseMessage.SUCCESS_CREATE.getValue());
        eventResource.add(selfLinkBuilder.withSelfRel());
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(selfLinkBuilder.withRel("update-events"));
        eventResource.add(Link.of("/docs/index.html#resources-events-create", "profile"));

        return ResponseEntity.created(createdUri).body(eventResource);

        /*return ResponseEntity.created(createdUri).body(ResponseDto.builder()
                .code(ResponseMessage.SUCCESS_CREATE.getCode())
                .message(ResponseMessage.SUCCESS_CREATE.getValue())
                .data(Collections.singletonList(eventResource))
                .build());*/
    }

}
