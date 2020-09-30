package com.epam.microservices.shop.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;

@Component
public class InstantOffsetDateTimeSerializer extends JsonSerializer<Instant> {

    @Override
    public void serialize(Instant instantObject, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(String.valueOf(instantObject.atOffset(ZoneOffset.UTC)));
    }
}
