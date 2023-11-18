package com.study.demoinflearnrestapi.domain.event;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventDto toEventDto(Event event);

    Event toEvent(EventDto eventDto);

}
