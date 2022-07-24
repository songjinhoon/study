package com.study.demoinflearnrestapi.domain.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)
//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    /*@MockBean
    EventRepository eventRepository;*/

    @Test
    void create_valid_empty_param() throws Exception {
        // given
        EventDto eventDto = EventDto.builder().build();

        // when - then
        mockMvc.perform(post("/api/event")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
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
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
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
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
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
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
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
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100))) // 아이디 생성 규칙을 따라갈것이고
                .andExpect(jsonPath("free").value(Matchers.not(true))) // boolean 의 기본 값인 false가 되겠지.
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
    }

}
