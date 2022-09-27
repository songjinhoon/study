package com.study.demoinflearnrestapi.domain.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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
                /*.andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].field").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists());
                .andExpect(jsonPath("$[0].rejectedValue").exists());*/
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
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data[0].id").exists())
                .andExpect(jsonPath("data[0].free").value(false))
                .andExpect(jsonPath("data[0].eventStatus").value(EventStatus.DRAFT.name()))
                .andExpect(jsonPath("data[0].offline").value(true))
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("data[0].links[0].rel").exists())
                .andExpect(jsonPath("data[0].links[1].rel").exists())
                .andExpect(jsonPath("data[0].links[2].rel").exists())
        ;
    }

}
