package com.test.ws.company.importer;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Optional;

import static com.test.ws.company.importer.DescriptionMessageTestFactory.createData;
import static com.test.ws.company.importer.DescriptionMessageTestFactory.createDescriptionMessage;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class DescriptionImporterTest {
    @Mock
    private CompanyService companyService;
    @Mock
    private DescriptionService descriptionService;

    private DescriptionImporter importer;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        importer = new DescriptionImporter(companyService, descriptionService);
    }

    @Test
    public void deleteDescription_When_DeleteTrue() {
        when(companyService.findByName("Name")).thenReturn(Optional.of(new Company(10)));
        importer.doImport(
                createDescriptionMessage(1, "Name", createData(true, LocalDate.now(), "desc"))
        );
        verify(descriptionService, only()).deleteByCompanyId(10);
    }

    @Test
    public void updateDescription_When_DeleteFalse() {
        when(companyService.findByName("Name")).thenReturn(Optional.of(new Company(10)));
        importer.doImport(
                createDescriptionMessage(1, "Name", createData(false, LocalDate.now(), "desc"))
        );
        verify(descriptionService, only()).update(10, "desc");
    }

    @Test
    public void notUpdateDescription_When_CompanyNotExists() {
        when(companyService.findByName("name")).thenReturn(Optional.empty());
        importer.doImport(
                createDescriptionMessage(1, "Name", createData(false, LocalDate.now(), "desc"))
        );
        verifyZeroInteractions(descriptionService);
    }

    @Test
    public void updateDescription_When_UpdateDataIsMostRecent() {
        when(companyService.findByName("Name")).thenReturn(Optional.of(new Company(10)));
        importer.doImport(
                createDescriptionMessage(1, "Name",
                        createData(true, LocalDate.of(2015, 5, 5), "desc2"),
                        createData(false, LocalDate.of(2016, 6, 6), "desc1"))
        );
        verify(descriptionService, only()).update(10, "desc1");
    }

    @Test
    public void deleteDescription_When_DeleteDataIsMostRecent() {
        when(companyService.findByName("Name")).thenReturn(Optional.of(new Company(10)));
        importer.doImport(
                createDescriptionMessage(1, "Name",
                        createData(true, LocalDate.of(2016, 6, 6), "desc1"),
                        createData(false, LocalDate.of(2015, 5, 5), "desc2"))
        );
        verify(descriptionService, only()).deleteByCompanyId(10);
    }

    @Test
    public void notUpdateDescription_When_DataIsEmpty() {
        when(companyService.findByName("Name")).thenReturn(Optional.of(new Company(10)));
        importer.doImport(
                createDescriptionMessage(1, "Name")
        );
        verifyZeroInteractions(descriptionService);
    }
}