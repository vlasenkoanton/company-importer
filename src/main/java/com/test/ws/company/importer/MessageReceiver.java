package com.test.ws.company.importer;

class MessageReceiver {

    private final DescriptionImporter descriptionImporter;

    MessageReceiver(DescriptionImporter descriptionImporter) {
        this.descriptionImporter = descriptionImporter;
    }

    void receive(String message) {
        if (message == null || message.trim().isEmpty()) {
            return;
        }
        DescriptionMessage descriptionMessage = DescriptionMessageConverter.convert(message);
        descriptionImporter.doImport(descriptionMessage);
    }
}
