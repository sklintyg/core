package se.inera.intyg.cts.application.service;

import org.springframework.transaction.annotation.Transactional;

public interface ExportService {

    @Transactional
    void collectCertificatesToExport();

    @Transactional
    void collectCertificateTextsToExport();

    @Transactional
    void export();
}
