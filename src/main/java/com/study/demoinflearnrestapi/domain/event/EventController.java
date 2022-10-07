package com.study.demoinflearnrestapi.domain.event;

import com.study.demoinflearnrestapi.common.response.ErrorResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RequestMapping(value = "/api/event", produces = MediaTypes.HAL_JSON_VALUE)
@RestController
public class EventController {

    private final EventRepository eventRepository;

    private final EventValidator eventValidator;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Event event = optionalEvent.get();
        EventResource eventResource = new EventResource(event);
        eventResource.add(Link.of("/docs/index.html#resources-events-get", "profile"));
        return ResponseEntity.ok().body(eventResource);
    }

    @GetMapping
    public ResponseEntity<?> queryEvents(Pageable pageable, PagedResourcesAssembler<Event> pagedResourcesAssembler) {
        Page<Event> page = eventRepository.findAll(pageable);
        var entityModels = pagedResourcesAssembler.toModel(page, EventResource::new);
        entityModels.add(Link.of("/docs/index.html#resources-events-list", "profile"));
        return ResponseEntity.ok(entityModels);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        Event event = EventMapper.INSTANCE.toEvent(eventDto);
        event.update();
        Event saveEvent = eventRepository.save(event);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(saveEvent.getId());
        URI createdUri = selfLinkBuilder.toUri();

        EventResource eventResource = new EventResource(event);
        eventResource.add(Link.of("/docs/index.html#resources-events-create", "profile"));
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(selfLinkBuilder.withRel("update-events"));
        return ResponseEntity.created(createdUri).body(eventResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable Integer id, @RequestBody @Valid EventDto eventDto, Errors errors) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Event event = optionalEvent.get();
        event.update(eventDto);
        Event saveEvent = eventRepository.save(event);

        EventResource eventResource = new EventResource(saveEvent);
        eventResource.add(Link.of("/docs/index.html#resources-events-update", "profile"));

        return ResponseEntity.ok().body(eventResource);
    }

    private ResponseEntity<?> badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorResource(errors));
    }

}
