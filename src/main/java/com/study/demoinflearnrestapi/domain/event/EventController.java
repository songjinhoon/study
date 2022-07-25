package com.study.demoinflearnrestapi.domain.event;

import com.study.demoinflearnrestapi.common.response.ResponseDto;
import com.study.demoinflearnrestapi.common.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RequestMapping(value = "/api/event")
@RestController
public class EventController {

    private final EventRepository eventRepository;

    private final EventValidator eventValidator;

    @PostMapping
    public ResponseEntity<ResponseDto<?>> createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
        if (errors.hasErrors()) {
            // MethodArgumentNotValidException.class, HttpMessageNotReadableException.class
            return ResponseEntity.internalServerError().body(ResponseDto.builder()
                    .code(ResponseMessage.NOT_VALID_REQUEST_DATA_EXCEPTION.getCode())
                    .message(ResponseMessage.NOT_VALID_REQUEST_DATA_EXCEPTION.getValue())
                    .errors(errors)
                    .build());
        }
        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.internalServerError().body(ResponseDto.builder()
                    .code(ResponseMessage.NOT_VALID_REQUEST_DATA_EXCEPTION.getCode())
                    .message(ResponseMessage.NOT_VALID_REQUEST_DATA_EXCEPTION.getValue())
                    .errors(errors)
                    .build());
        }

        Event event = EventMapper.INSTANCE.toEvent(eventDto);
        event.update();
        Event saveEvent = eventRepository.save(event);
        URI uri = linkTo(EventController.class).slash(saveEvent.getId()).toUri();
        return ResponseEntity.created(uri).body(ResponseDto.builder()
                .code(ResponseMessage.SUCCESS_CREATE.getCode())
                .message(ResponseMessage.SUCCESS_CREATE.getValue())
                .data(Collections.singletonList(event))
                .build());
    }

}
/*
 *  강의에서는 ModelMapper를 사용했으나 나는 Mapstruct로 진행해보겠음
 * */
