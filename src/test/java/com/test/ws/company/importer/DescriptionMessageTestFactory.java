package com.test.ws.company.importer;

import java.time.LocalDate;
import java.util.List;

class DescriptionMessageTestFactory {
    private DescriptionMessageTestFactory() {
    }

    static DescriptionMessage createDescriptionMessage(int id, String name, DescriptionMessage.Data... data) {
        DescriptionMessage descriptionMessage = new DescriptionMessage();
        descriptionMessage.setId(id);
        descriptionMessage.setName(name);
        descriptionMessage.setData(List.of(data));
        return descriptionMessage;
    }

    static DescriptionMessage.Data createData(boolean delete, LocalDate date, String description) {
        DescriptionMessage.Data data = new DescriptionMessage.Data();
        data.setDelete(delete);
        data.setDate(date);
        data.setDescription(description);
        return data;
    }
}
