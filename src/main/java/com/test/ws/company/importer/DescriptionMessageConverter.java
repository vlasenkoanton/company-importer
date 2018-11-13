package com.test.ws.company.importer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class DescriptionMessageConverter {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");
    private static JsonDeserializer<LocalDate> localDateDeserializer() {
        return (json, typeOfT, context) -> LocalDate.parse(json.getAsString(), dateFormatter);
    }

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, localDateDeserializer())
            .create();

    private DescriptionMessageConverter() {
    }

    static DescriptionMessage convert(String message) {
        return GSON.fromJson(message, DescriptionMessage.class);
    }
}
