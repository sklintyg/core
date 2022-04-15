package se.inera.intyg.cts.infrastructure.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.cts.testutil.CertificateTestDataBuilder.certificates;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.cts.domain.model.CertificateBatch;
import se.inera.intyg.cts.domain.model.CertificateSummary;
import se.inera.intyg.cts.domain.model.Termination;

class IntegrationCertificateBatchRepositoryTest {

  private static final int BATCH_SIZE = 30;
  private IntegrationCertificateBatchRepository integrationCertificateBatchRepository;
  private GetCertificateBatchFromMemory getCertificateBatch;
  private Termination termination;

  @BeforeEach
  void setUp() {
    getCertificateBatch = new GetCertificateBatchFromMemory();
    integrationCertificateBatchRepository = new IntegrationCertificateBatchRepository(
        getCertificateBatch, BATCH_SIZE);
    termination = defaultTermination();
    getCertificateBatch.prepare(certificates(80, 5));
  }

  @Nested
  class FirstBatch {

    @Test
    void shallGetCertificateSummaryWithFirstBatch() {
      assertEquals(new CertificateSummary(80, 5),
          integrationCertificateBatchRepository.nextBatch(termination).certificateSummary());
    }

    @Test
    void shallGetCertificateWithFirstBatch() {
      assertEquals(30,
          integrationCertificateBatchRepository.nextBatch(termination).certificateList().size());
    }
  }

  @Nested
  class MiddleBatch {

    @BeforeEach
    void setUp() {
      termination.export()
          .processBatch(new CertificateBatch(new CertificateSummary(80, 5), certificates(30, 0)));
    }

    @Test
    void shallGetCertificateSummaryWithFirstBatch() {
      assertEquals(new CertificateSummary(80, 5),
          integrationCertificateBatchRepository.nextBatch(termination).certificateSummary());
    }

    @Test
    void shallGetCertificateWithFirstBatch() {
      assertEquals(30,
          integrationCertificateBatchRepository.nextBatch(termination).certificateList().size());
    }
  }

  @Nested
  class LastBatch {

    @BeforeEach
    void setUp() {
      termination.export()
          .processBatch(new CertificateBatch(new CertificateSummary(80, 5), certificates(60, 0)));
    }

    @Test
    void shallGetCertificateSummaryWithFirstBatch() {
      assertEquals(new CertificateSummary(80, 5),
          integrationCertificateBatchRepository.nextBatch(termination).certificateSummary());
    }

    @Test
    void shallGetCertificateWithFirstBatch() {
      assertEquals(20,
          integrationCertificateBatchRepository.nextBatch(termination).certificateList().size());
    }
  }
}