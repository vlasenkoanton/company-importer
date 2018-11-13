package com.test.ws.company.importer;

class DescriptionImporter {
    private final CompanyService companyService;
    private final DescriptionService descriptionService;

    DescriptionImporter(CompanyService companyService, DescriptionService descriptionService) {
        this.companyService = companyService;
        this.descriptionService = descriptionService;
    }

    void doImport(DescriptionMessage descriptionMessage) {
        companyService.findByName(descriptionMessage.getName())
                .map(Company::getId)
                .ifPresent(companyId -> updateDescription(descriptionMessage, companyId));
    }

    private void updateDescription(DescriptionMessage descriptionMessage, Integer companyId) {
        descriptionMessage.getMostRecentData().ifPresent(data -> {
            if (data.getDelete()) {
                descriptionService.deleteByCompanyId(companyId);
            } else {
                descriptionService.update(companyId, data.getDescription());
            }
        });
    }
}
