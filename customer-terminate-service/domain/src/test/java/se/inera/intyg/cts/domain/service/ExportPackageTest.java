package se.inera.intyg.cts.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.cts.domain.util.TerminationTestDataFactory.defaultTermination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.domain.repository.InMemoryTerminationRepository;

class ExportPackageTest {

  private InMemoryTerminationRepository inMemoryTerminationRepository;
  private DummyCreatePackage dummyCreatePackage;
  private ExportPackage exportPackage;
  private UploadPackageToMemory uploadPackage;
  private Termination termination;

  @BeforeEach
  void setUp() {
    termination = defaultTermination();
    dummyCreatePackage = new DummyCreatePackage();
    inMemoryTerminationRepository = new InMemoryTerminationRepository();
    uploadPackage = new UploadPackageToMemory();
    exportPackage = new ExportPackage(dummyCreatePackage, uploadPackage,
        inMemoryTerminationRepository);
  }

  @Test
  void shallGeneratePasswordForPackage() {
    exportPackage.export(termination);

    assertNotNull(dummyCreatePackage.password());
  }

  @Test
  void shallStorePasswordForPackageInTermination() {
    exportPackage.export(termination);

    assertEquals(dummyCreatePackage.password(),
        inMemoryTerminationRepository.findByTerminationId(termination.terminationId())
            .orElseThrow()
            .export()
            .password());
  }

  @Test
  void shallUpdateStatusToExported() {
    exportPackage.export(termination);

    assertEquals(TerminationStatus.EXPORTED,
        inMemoryTerminationRepository.findByTerminationId(termination.terminationId())
            .orElseThrow()
            .status());
  }
}