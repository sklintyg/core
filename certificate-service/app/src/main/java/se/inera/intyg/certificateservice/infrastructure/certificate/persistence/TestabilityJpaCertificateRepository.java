package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateModelEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;
import se.inera.intyg.certificateservice.testability.certificate.service.repository.TestabilityCertificateRepository;

@Profile(TESTABILITY_PROFILE)
@Primary
@Repository
public class TestabilityJpaCertificateRepository
    extends JpaCertificateRepository implements
    TestabilityCertificateRepository {

  private final CertificateEntityRepository certificateEntityRepository;

  public TestabilityJpaCertificateRepository(
      CertificateEntityRepository certificateEntityRepository,
      CertificateModelEntityRepository certificateModelEntityRepository,
      StaffEntityRepository staffEntityRepository, UnitEntityRepository unitEntityRepository,
      PatientEntityRepository patientEntityRepository,
      CertificateModelRepository certificateModelRepository) {
    super(certificateEntityRepository, certificateModelEntityRepository, staffEntityRepository,
        unitEntityRepository, patientEntityRepository, certificateModelRepository);
    this.certificateEntityRepository = certificateEntityRepository;
  }

  @Override
  public Certificate insert(Certificate certificate) {
    return save(certificate);
  }

  @Override
  public void remove(List<CertificateId> certificateIds) {
    certificateEntityRepository.deleteAllByCertificateIdIn(
        certificateIds.stream()
            .map(CertificateId::id)
            .toList()
    );
  }

}
