package se.inera.intyg.cts.application.api;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.cts.application.service.ExportService;

@ExtendWith(MockitoExtension.class)
class ExportControllerTest {

    @Mock
    private ExportService exportService;
    @InjectMocks
    private ExportController exportController;

    @Test
    void startCollectCertificates() {
        exportController.startCollectCertificates();
        verify(exportService, times(1)).collectCertificatesToExport();
    }

    @Test
    void startExportPackage() {
        exportController.startExportPackage();
        verify(exportService, times(1)).export();
    }
}