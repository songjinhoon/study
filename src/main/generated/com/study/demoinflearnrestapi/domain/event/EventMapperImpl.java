package com.study.demoinflearnrestapi.domain.event;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-06T10:31:13+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.16 (Amazon.com Inc.)"
)
public class EventMapperImpl implements EventMapper {

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
