package com.study.demoinflearnrestapi.domain.event;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-11T10:26:18+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.16 (Amazon.com Inc.)"
)
public class EventMapperImpl implements EventMapper {

    @Override
    public EventDto toEventDto(Event event) {
        if ( event == null ) {
            return null;
        }

        EventDto.EventDtoBuilder eventDto = EventDto.builder();

        eventDto.name( event.getName() );
        eventDto.description( event.getDescription() );
        eventDto.beginEnrollmentDateTime( event.getBeginEnrollmentDateTime() );
        eventDto.closeEnrollmentDateTime( event.getCloseEnrollmentDateTime() );
        eventDto.beginEventDateTime( event.getBeginEventDateTime() );
        eventDto.endEventDateTime( event.getEndEventDateTime() );
        eventDto.location( event.getLocation() );
        eventDto.basePrice( event.getBasePrice() );
        eventDto.maxPrice( event.getMaxPrice() );
        eventDto.limitOfEnrollment( event.getLimitOfEnrollment() );

        return eventDto.build();
    }

    @Override
    public Event toEvent(EventDto eventDto) {
        if ( eventDto == null ) {
            return null;
        }

        Event.EventBuilder event = Event.builder();

        event.name( eventDto.getName() );
        event.description( eventDto.getDescription() );
        event.beginEnrollmentDateTime( eventDto.getBeginEnrollmentDateTime() );
        event.closeEnrollmentDateTime( eventDto.getCloseEnrollmentDateTime() );
        event.beginEventDateTime( eventDto.getBeginEventDateTime() );
        event.endEventDateTime( eventDto.getEndEventDateTime() );
        event.location( eventDto.getLocation() );
        event.basePrice( eventDto.getBasePrice() );
        event.maxPrice( eventDto.getMaxPrice() );
        event.limitOfEnrollment( eventDto.getLimitOfEnrollment() );

        return event.build();
    }
}
