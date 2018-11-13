package com.test.ws.company.importer;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static com.test.ws.company.importer.DescriptionMessageTestFactory.createData;
import static com.test.ws.company.importer.DescriptionMessageTestFactory.createDescriptionMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class MessageReceiverTest {

    @Mock
    private DescriptionImporter descriptionImporter;
    private MessageReceiver messageReceiver;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        messageReceiver = new MessageReceiver(descriptionImporter);
    }

    private void assertImported(DescriptionMessage descriptionMessage) {
        ArgumentCaptor<DescriptionMessage> descCaptor = ArgumentCaptor.forClass(DescriptionMessage.class);
        verify(descriptionImporter).doImport(descCaptor.capture());
        assertThat(descCaptor.getValue()).isEqualToComparingFieldByFieldRecursively(descriptionMessage);
    }

    @Test
    public void importDescriptionMessage_When_ReceivedStringMessage() {
        messageReceiver.receive("{\n"
                + "  \"id\": 34,\n"
                + "  \"name\": \"Test Company\",\n"
                + "  \"data\": [\n"
                + "    {\n"
                + "      \"delete\": false,\n"
                + "      \"date\": \"2015-1-1\",\n"
                + "      \"description\": \"Some new company description\"\n"
                + "    },\n"
                + "    {\n"
                + "      \"delete\": true,\n"
                + "      \"date\": \"2014-2-2\",\n"
                + "      \"description\": \"Some old company description\"\n"
                + "    }\n"
                + "  ]\n"
                + "}");

        assertImported(createDescriptionMessage(34, "Test Company",
                createData(false, LocalDate.of(2015, 1, 1), "Some new company description"),
                createData(true, LocalDate.of(2014, 2, 2), "Some old company description")
        ));
    }

    @DataProvider
    private Object[][] blankStrings() {
        return new Object[][]{
                {null}, {""}, {" "}
        };
    }

    @Test(dataProvider = "blankStrings")
    public void notImport_When_ReceivedBlankString(String message) {
        messageReceiver.receive(message);
        verifyZeroInteractions(descriptionImporter);
    }
}