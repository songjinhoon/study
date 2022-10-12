package com.study.demoinflearnrestapi.domain.event;

import com.study.demoinflearnrestapi.common.AppProperties;
import com.study.demoinflearnrestapi.common.BaseControllerTest;
import com.study.demoinflearnrestapi.domain.common.Role;
import com.study.demoinflearnrestapi.domain.member.Member;
import com.study.demoinflearnrestapi.domain.member.MemberRepository;
import com.study.demoinflearnrestapi.domain.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest extends BaseControllerTest {

    @Autowired
    AppProperties appProperties;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        this.eventRepository.deleteAll();
        this.memberRepository.deleteAll();
    }

    private Member postMember() {
        Member member = Member.builder()
                .account(appProperties.getUserUsername())
                .password(appProperties.getUserPassword())
                .email(appProperties.getUserEmail())
                .roles(Set.of(Role.ADMIN, Role.USER))
                .build();
        return memberService.save(member);
    }

    private String getAccessToken(boolean needToPostMember) throws Exception {
        //given
        if (needToPostMember) {
            postMember();
        }
        //when - then
        ResultActions resultActions = mockMvc.perform(post("/oauth/token")
                        .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                        .param("username", appProperties.getUserUsername())
                        .param("password", appProperties.getUserPassword())
                        .param("grant_type", "password")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
        String response = resultActions.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser jackson2JsonParser = new Jackson2JsonParser();
        return jackson2JsonParser.parseMap(response).get("access_token").toString();
    }

    private String getBearerToken(boolean needToPostMember) throws Exception {
        return "Bearer " + getAccessToken(needToPostMember);
    }

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
    */

    @Test
    @DisplayName("get success")
    public void getSuccess() throws Exception {
        //given
        Member member = postMember();
        Event event = this.generateEvent(100, member);
        //when - then
        this.mockMvc.perform(get("/api/event/{id}", event.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-event"))
        ;
    }

    @Test
    @DisplayName("이벤트 단일 조회 - 없는 경우")
    public void find404() throws Exception {
        this.mockMvc.perform(get("/api/event/10000"))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("이벤트 페이지 조회")
    public void findPage() throws Exception {
        // given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // when - then
        this.mockMvc.perform(get("/api/event")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sort", "name,DESC")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-events"))
        ;
    }

    @Test
    @DisplayName("이벤트 페이지 조회 - 인증정보")
    public void findPageWithAuth() throws Exception {
        // given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // when - then
        this.mockMvc.perform(get("/api/event")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .param("page", "1")
                        .param("size", "10")
                        .param("sort", "name,DESC")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(jsonPath("_links.create-event").exists())
                .andDo(document("query-events"))
        ;
    }

    @Test
    @DisplayName("비니지스 로직에 의한 파라미터값이 유효하지 않을 경우 에러 발생")
    public void create_valid_wrong_param() throws Exception {
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
        mockMvc.perform(post("/api/event")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("_links.index").exists())
        ;
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

        mockMvc.perform(post("/api/event")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                .andExpect(jsonPath("offline").value(true))
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
                                relaxedResponseFields(
//                                responseFields(
                                        fieldWithPath("id").description("id of new event"),
                                        fieldWithPath("name").description("Name of new event"),
                                        fieldWithPath("description").description("description of new event"),
                                        fieldWithPath("beginEnrollmentDateTime").description("beginEnrollmentDateTime of new event"),
                                        fieldWithPath("closeEnrollmentDateTime").description("closeEnrollmentDateTime of new event"),
                                        fieldWithPath("beginEventDateTime").description("beginEventDateTime of new event"),
                                        fieldWithPath("endEventDateTime").description("endEventDateTime of new event"),
                                        fieldWithPath("location").description("location of new event"),
                                        fieldWithPath("basePrice").description("basePrice of new event"),
                                        fieldWithPath("maxPrice").description("maxPrice of new event"),
                                        fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event"),
                                        fieldWithPath("free").description("free of new event"),
                                        fieldWithPath("offline").description("offline of new event"),
                                        fieldWithPath("eventStatus").description("eventStatus of new event"),
                                        fieldWithPath("_links.self.href").description("link to self"),
                                        fieldWithPath("_links.query-events.href").description("link to query events"),
                                        fieldWithPath("_links.update-events.href").description("link to update events"),
                                        fieldWithPath("_links.profile.href").description("link to profile")
                                )
                        ))
        ;
    }

    @Test
    @DisplayName("put success")
    public void putSuccess() throws Exception {
        //given
        Member member = postMember();
        String eventName = "Updated Event";
        Event event = this.generateEvent(100, member);
        EventDto eventDto = EventMapper.INSTANCE.toEventDto(event);
        eventDto.setName(eventName);

        //when-then
        mockMvc.perform(put("/api/event/{id}", event.getId())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(false))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                        .accept(MediaTypes.HAL_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(eventName))
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-event"))
        ;
    }

    @Test
    @DisplayName("put fail - 데이터가 비어있어서 실패")
    public void putFail400Empty() throws Exception {
        //given
        String eventName = "Updated Event";
        Event event = this.generateEvent(100);
        EventDto eventDto = new EventDto();
        eventDto.setName(eventName);

        //when-then
        mockMvc.perform(put("/api/event/{id}", event.getId())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                        .accept(MediaTypes.HAL_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("put fail - 데이터가 잘못 되어서 실패")
    public void putFail400Wrong() throws Exception {
        //given
        String eventName = "Updated Event";
        Event event = this.generateEvent(200);
        EventDto eventDto = EventMapper.INSTANCE.toEventDto(event);
        eventDto.setBasePrice(20000);
        eventDto.setMaxPrice(1000);

        //when-then
        mockMvc.perform(put("/api/event/{id}", event.getId())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                        .accept(MediaTypes.HAL_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("put fail - 존재하지 않아서 실패")
    public void putFail404() throws Exception {
        //given
        Event event = this.generateEvent(200);
        EventDto eventDto = EventMapper.INSTANCE.toEventDto(event);

        //when-then
        mockMvc.perform(put("/api/event/10000")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                        .accept(MediaTypes.HAL_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    private Event generateEvent(int i, Member member) {
        Event event = buildEvent(i);
        event.init(member);
        return eventRepository.save(event);
    }

    private Event generateEvent(int i) {
        Event event = buildEvent(i);
        return eventRepository.save(event);
    }

    private Event buildEvent(int i) {
        return Event.builder()
                .name("spring " + i)
                .description("spring rest api")
                .beginEnrollmentDateTime(LocalDateTime.of(2022, 7, 2, 14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 7, 3, 14, 21))
                .beginEventDateTime(LocalDateTime.of(2022, 7, 4, 14, 21))
                .endEventDateTime(LocalDateTime.of(2022, 7, 5, 14, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .eventStatus(EventStatus.DRAFT)
                .build();
    }

}
