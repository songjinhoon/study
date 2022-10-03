package com.study.demoinflearnrestapi.domain.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.demoinflearnrestapi.common.RestDocsConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// RestDocs
@Import(RestDocsConfig.class)
@AutoConfigureRestDocs
//
@AutoConfigureMockMvc
@SpringBootTest
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
/*
    @Test
    @DisplayName("파라미터가 비어있는 경우 에러 발생")
    void create_valid_empty_param() throws Exception {
        // given
        EventDto eventDto = EventDto.builder().build();

        // when - then
        mockMvc.perform(post("/api/event")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errors").exists());
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].field").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists());
                .andExpect(jsonPath("$[0].rejectedValue").exists());
    }

    @Test
    @DisplayName("불필요한 파라미터가 존재하는 경우 에러 발생")
    void create_valid_add_param() throws Exception {
        // given
        Event event = Event.builder()
                .id(100)
                .name("spring")
                .description("spring rest api")
                .beginEnrollmentDateTime(LocalDateTime.of(2022, 7, 2, 14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 7, 3, 14, 21))
                .beginEventDateTime(LocalDateTime.of(2022, 7, 4, 14, 21))
                .endEventDateTime(LocalDateTime.of(2022, 7, 5, 14, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        // when - then
        mockMvc.perform(post("/api/event/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errors").exists());
    }

    @Test
    @DisplayName("파라미터의 필드가 부족한 경우 에러 발생")
    void create_valid_lack_param() throws Exception {
        // given
        EventDto event = EventDto.builder()
//                .name("spring")
                .description("spring rest api")
                .beginEnrollmentDateTime(LocalDateTime.of(2022, 7, 2, 14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 7, 3, 14, 21))
                .beginEventDateTime(LocalDateTime.of(2022, 7, 4, 14, 21))
                .endEventDateTime(LocalDateTime.of(2022, 7, 5, 14, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();
        // when - then
        mockMvc.perform(post("/api/event/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errors").exists());
    }

    @Test
    @DisplayName("비니지스 로직에 의한 파라미터값이 유효하지 않을 경우 에러 발생")
    void create_valid_wrong_param() throws Exception {
        // given
        EventDto event = EventDto.builder()
                .name("spring")
                .description("spring rest api")
                // 시작일이 종료일보다빠름
                .beginEnrollmentDateTime(LocalDateTime.of(2022, 7, 3, 14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 7, 2, 14, 21))
                .beginEventDateTime(LocalDateTime.of(2022, 7, 4, 14, 21))
                .endEventDateTime(LocalDateTime.of(2022, 7, 5, 14, 21))
                // max < base
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();
        // when - then
        mockMvc.perform(post("/api/event/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errors").exists());
    }
*/
    @Test
    @DisplayName("event 정상 저장")
    public void create_success() throws Exception {
        EventDto event = EventDto.builder()
                .name("spring")
                .description("spring rest api")
                .beginEnrollmentDateTime(LocalDateTime.of(2022, 7, 2, 14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 7, 3, 14, 21))
                .beginEventDateTime(LocalDateTime.of(2022, 7, 4, 14, 21))
                .endEventDateTime(LocalDateTime.of(2022, 7, 5, 14, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();

        mockMvc.perform(post("/api/event/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("event.id").exists())
                .andExpect(jsonPath("event.free").value(false))
                .andExpect(jsonPath("event.eventStatus").value(EventStatus.DRAFT.name()))
                .andExpect(jsonPath("event.offline").value(true))
                .andExpect(jsonPath("errors").doesNotExist())
                .andDo(
                        document("create-event",
                                links(
                                        linkWithRel("self").description("link to self"),
                                        linkWithRel("query-events").description("link to query events"),
                                        linkWithRel("update-events").description("link to update events"),
                                        linkWithRel("profile").description("link to profile")
                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                                ),
                                requestFields(
                                        fieldWithPath("name").description("Name of new event"),
                                        fieldWithPath("description").description("description of new event"),
                                        fieldWithPath("beginEnrollmentDateTime").description("beginEnrollmentDateTime of new event"),
                                        fieldWithPath("closeEnrollmentDateTime").description("closeEnrollmentDateTime of new event"),
                                        fieldWithPath("beginEventDateTime").description("beginEventDateTime of new event"),
                                        fieldWithPath("endEventDateTime").description("endEventDateTime of new event"),
                                        fieldWithPath("location").description("location of new event"),
                                        fieldWithPath("basePrice").description("basePrice of new event"),
                                        fieldWithPath("maxPrice").description("maxPrice of new event"),
                                        fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event")
                                ),
                                responseHeaders(
                                        headerWithName(HttpHeaders.LOCATION).description("Location header"),
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
                                ),
//                                relaxedResponseFields(
                                responseFields(
                                        fieldWithPath("code").description("Code of new Event"),
                                        fieldWithPath("message").description("Message of New Event"),
                                        fieldWithPath("event.id").description("id of new event"),
                                        fieldWithPath("event.name").description("Name of new event"),
                                        fieldWithPath("event.description").description("description of new event"),
                                        fieldWithPath("event.beginEnrollmentDateTime").description("beginEnrollmentDateTime of new event"),
                                        fieldWithPath("event.closeEnrollmentDateTime").description("closeEnrollmentDateTime of new event"),
                                        fieldWithPath("event.beginEventDateTime").description("beginEventDateTime of new event"),
                                        fieldWithPath("event.endEventDateTime").description("endEventDateTime of new event"),
                                        fieldWithPath("event.location").description("location of new event"),
                                        fieldWithPath("event.basePrice").description("basePrice of new event"),
                                        fieldWithPath("event.maxPrice").description("maxPrice of new event"),
                                        fieldWithPath("event.limitOfEnrollment").description("limitOfEnrollment of new event"),
                                        fieldWithPath("event.free").description("free of new event"),
                                        fieldWithPath("event.offline").description("offline of new event"),
                                        fieldWithPath("event.eventStatus").description("eventStatus of new event"),
                                        fieldWithPath("_links.self.href").description("link to self"),
                                        fieldWithPath("_links.query-events.href").description("link to query events"),
                                        fieldWithPath("_links.update-events.href").description("link to update events"),
                                        fieldWithPath("_links.profile.href").description("link to profile")
                                )
                        ))
        ;
    }

}
