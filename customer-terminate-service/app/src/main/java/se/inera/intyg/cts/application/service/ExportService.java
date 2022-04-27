package se.inera.intyg.cts.application.service;

import java.util.Arrays;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.CollectExportContent;
import se.inera.intyg.cts.domain.service.ExportPackage;

@Service
public class ExportService {

  private final TerminationRepository terminationRepository;
  private final CollectExportContent collectExportContent;
  private final ExportPackage exportPackage;
  private final RandomPasswordGenerator randomPasswordGenerator;

  public ExportService(TerminationRepository terminationRepository,
      CollectExportContent collectExportContent,
      ExportPackage exportPackage, RandomPasswordGenerator randomPasswordGenerator) {
    this.terminationRepository = terminationRepository;
    this.collectExportContent = collectExportContent;
    this.exportPackage = exportPackage;
    this.randomPasswordGenerator = randomPasswordGenerator;
  }

  @Transactional
  public void collectCertificatesToExport() {
    final var terminations = terminationRepository.findByStatuses(
        Arrays.asList(TerminationStatus.CREATED, TerminationStatus.COLLECTING_CERTIFICATES)
    );

    terminations.forEach(
        termination -> collectExportContent.collectCertificates(termination.terminationId())
    );
  }

  @Transactional
  public void collectCertificateTextsToExport() {
    final var terminationsToExport = terminationRepository.findByStatuses(
        Arrays.asList(TerminationStatus.COLLECTING_CERTIFICATES_COMPLETED)
    );

    terminationsToExport.forEach(
        termination -> collectExportContent.collectCertificateTexts(termination)
    );
  }

  @Transactional
  public void export() {
    final var terminationsToExport = terminationRepository.findByStatuses(
        Arrays.asList(TerminationStatus.COLLECTING_CERTIFICATE_TEXTS_COMPLETED)
    );

    terminationsToExport.forEach(
        termination -> exportPackage.export(termination, randomPasswordGenerator.generateSecureRandomPassword())
    );
  }
}
