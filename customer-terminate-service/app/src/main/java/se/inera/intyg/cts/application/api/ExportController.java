package se.inera.intyg.cts.application.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.cts.application.service.ExportService;
import se.inera.intyg.cts.application.service.MessageService;

@RestController
@RequestMapping("/api/v1/exports")
public class ExportController {

  private final static Logger LOG = LoggerFactory.getLogger(ExportController.class);

  private final ExportService exportService;
  private final MessageService messageService;

  public ExportController(ExportService exportService, MessageService messageService) {
    this.exportService = exportService;
    this.messageService = messageService;
  }

  @PostMapping("/collectCertificates")
  void startCollectCertificates() {
    LOG.info("Start collecting certificates");
    exportService.collectCertificatesToExport();
  }

  @PostMapping("/collectCertificateTexts")
  void startCollectCertificateTexts() {
    LOG.info("Start collecting certificate texts");
    exportService.collectCertificateTextsToExport();
  }

  @PostMapping("/exportPackage")
  void startExportPackage() {
    LOG.info("Start export package");
    exportService.export();
  }

  @PostMapping("/sendPasswords")
  void sendPasswords() {
    LOG.info("Start send passwords");
    messageService.sendPassword();
  }
}
