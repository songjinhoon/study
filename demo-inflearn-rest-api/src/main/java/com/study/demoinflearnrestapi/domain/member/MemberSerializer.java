package com.study.demoinflearnrestapi.domain.member;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MemberSerializer extends JsonSerializer<Member> {

    @Override
    public void serialize(Member value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeEndObject();
    }

}
