package se.inera.intyg.cts.application.service;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.CollectExportContent;
import se.inera.intyg.cts.domain.service.ExportPackage;

@Service
public class ExportServiceImpl implements ExportService {

  private final static Logger LOG = LoggerFactory.getLogger(ExportServiceImpl.class);

  private final TerminationRepository terminationRepository;
  private final CollectExportContent collectExportContent;
  private final ExportPackage exportPackage;

  public ExportServiceImpl(TerminationRepository terminationRepository,
      CollectExportContent collectExportContent,
      ExportPackage exportPackage) {
    this.terminationRepository = terminationRepository;
    this.collectExportContent = collectExportContent;
    this.exportPackage = exportPackage;
  }

  @Override
  @Transactional
  public void collectCertificatesToExport() {
    final var terminations = terminationRepository.findByStatuses(
        Arrays.asList(TerminationStatus.CREATED, TerminationStatus.COLLECTING_CERTIFICATES)
    );

    terminations.forEach(
        termination -> {
          try {
            collectExportContent.collectCertificates(termination.terminationId());
            LOG.info("Collected certificates for termination '{}'",
                termination.terminationId().id().toString());
          } catch (Exception ex) {
            LOG.error(
                String.format("Failed to collect certificates for termination '%s'"), ex
            );
          }
        }
    );
  }

  @Override
  @Transactional
  public void collectCertificateTextsToExport() {
    final var terminationsToExport = terminationRepository.findByStatuses(
        Arrays.asList(TerminationStatus.COLLECTING_CERTIFICATES_COMPLETED)
    );

    terminationsToExport.forEach(
        termination -> {
          try {
            collectExportContent.collectCertificateTexts(termination);
            LOG.info("Collected certificate texts for termination '{}'",
                termination.terminationId().id().toString());
          } catch (Exception ex) {
            LOG.error(
                String.format("Failed to collect certificate texts for termination '%s'"), ex
            );
          }
        }
    );
  }

  @Override
  @Transactional
  public void export() {
    final var terminationsToExport = terminationRepository.findByStatuses(
        Arrays.asList(TerminationStatus.COLLECTING_CERTIFICATE_TEXTS_COMPLETED)
    );

    terminationsToExport.forEach(
        termination -> {
          try {
            exportPackage.export(termination);
            LOG.info("Exported termination '{}'",
                termination.terminationId().id().toString());
          } catch (Exception ex) {
            LOG.error(
                String.format("Failed to export termination '%s'"), ex
            );
          }
        }
    );
  }
}
