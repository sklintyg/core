package se.inera.intyg.cts.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.cts.domain.util.TerminationTestDataFactory.defaultTermination;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.cts.domain.model.Certificate;
import se.inera.intyg.cts.domain.model.CertificateBatch;
import se.inera.intyg.cts.domain.model.CertificateId;
import se.inera.intyg.cts.domain.model.CertificateSummary;
import se.inera.intyg.cts.domain.model.CertificateXML;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.domain.repository.InMemoryCertificateBatchRepository;
import se.inera.intyg.cts.domain.repository.InMemoryCertificateRepository;
import se.inera.intyg.cts.domain.repository.InMemoryTerminationRepository;

class CollectExportContentTest {

  private CollectExportContent collectExportContent;
  private InMemoryTerminationRepository inMemoryTerminationRepository;
  private InMemoryCertificateBatchRepository inMemoryCertificateBatchRepository;
  private InMemoryCertificateRepository inMemoryCertificateRepository;

  @BeforeEach
  void setUp() {
    inMemoryTerminationRepository = new InMemoryTerminationRepository();
    inMemoryCertificateBatchRepository = new InMemoryCertificateBatchRepository();
    inMemoryCertificateRepository = new InMemoryCertificateRepository();
    collectExportContent = new CollectExportContent(inMemoryTerminationRepository,
        inMemoryCertificateBatchRepository, inMemoryCertificateRepository);
  }

  @Test
  void shallThrowIllegalArgumentExceptionIfTerminationDoesntExists() {
    assertThrows(IllegalArgumentException.class,
        () -> collectExportContent.collectCertificates(new TerminationId(UUID.randomUUID()))
    );
  }

  @Nested
  class CreatedTermination {

    private static int BATCH_SIZE = 10;
    private TerminationId terminationId;

    @BeforeEach
    void setUp() {
      final var termination = defaultTermination();
      inMemoryTerminationRepository.store(termination);
      inMemoryCertificateBatchRepository.prepare(
          new CertificateBatch(
              new CertificateSummary(50, 0),
              certificates(BATCH_SIZE)
          )
      );
      terminationId = termination.terminationId();
    }

    @Test
    void shallChangeStatusOfTerminationToCollectingWhenFirstBatchIsCollected() {
      collectExportContent.collectCertificates(terminationId);

      assertEquals(TerminationStatus.COLLECTING, termination(terminationId).status());
    }

    @Test
    void shallUpdateCertificateSummaryWhenBatchIsCollected() {
      collectExportContent.collectCertificates(terminationId);

      assertEquals(BATCH_SIZE, termination(terminationId).export().certificateSummary().total());
    }

    @Test
    void shallStoreCertificatesWhenBatchIsCollected() {
      collectExportContent.collectCertificates(terminationId);

      assertEquals(BATCH_SIZE, certificatesCount(terminationId));
    }

    @Test
    void shallChangeStatusOfTerminationToCollectionCompletedWhenNoCertificates() {
      inMemoryCertificateBatchRepository.prepare(CertificateBatch.emptyBatch());

      collectExportContent.collectCertificates(terminationId);

      assertEquals(TerminationStatus.COLLECTION_COMPLETED, termination(terminationId).status());
    }
  }

  @Nested
  class CollectingTermination {

    private static int BATCH_SIZE = 25;
    private TerminationId terminationId;

    @BeforeEach
    void setUp() {
      final var termination = defaultTermination();
      inMemoryTerminationRepository.store(termination);
      inMemoryCertificateBatchRepository.prepare(
          new CertificateBatch(
              new CertificateSummary(50, 0),
              certificates(BATCH_SIZE)
          )
      );
      terminationId = termination.terminationId();
    }

    @Test
    void shallChangeStatusOfTerminationToCollectionCompletedWhenLastBatch() {
      collectExportContent.collectCertificates(terminationId);
      collectExportContent.collectCertificates(terminationId);

      assertEquals(TerminationStatus.COLLECTION_COMPLETED, termination(terminationId).status());
    }
  }

  private List<Certificate> certificates(int batchSize) {
    final List<Certificate> certificates = new ArrayList<>();
    for (int i = 0; i < batchSize; i++) {
      certificates.add(
          new Certificate(
              new CertificateId(UUID.randomUUID().toString()),
              false,
              new CertificateXML("<xml></xml>")
          )
      );
    }
    return certificates;
  }

  private Termination termination(TerminationId terminationId) {
    return inMemoryTerminationRepository.findByTerminationId(terminationId).orElseThrow();
  }

  private int certificatesCount(TerminationId terminationId) {
    return inMemoryCertificateRepository.totalCount(terminationId);
  }
}