package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;

@Profile("!" + TESTABILITY_PROFILE)
@Repository
@RequiredArgsConstructor
public class InMemoryCertificateRepository implements CertificateRepository {

  protected final Map<CertificateId, Certificate> certificateMap = new HashMap<>();

  @Override
  public Certificate create(CertificateModel certificateModel) {
    if (certificateModel == null) {
      throw new IllegalArgumentException("Unable to create, certificateModel was null.");
    }
    return Certificate.builder()
        .id(new CertificateId(UUID.randomUUID().toString()))
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(certificateModel)
        .revision(new Revision(0))
        .build();
  }

  @Override
  public Certificate save(Certificate certificate) {
    if (certificate == null) {
      throw new IllegalArgumentException(
          "Unable to save, certificate was null"
      );
    }

    if (Status.DELETED_DRAFT.equals(certificate.status())) {
      certificateMap.remove(certificate.id());
      return certificate;
    }

    certificateMap.put(certificate.id(), certificate);
    return certificate;
  }

  @Override
  public Certificate getById(CertificateId certificateId) {
    if (certificateId == null) {
      throw new IllegalArgumentException("CertificateId is null!");
    }
    if (!certificateMap.containsKey(certificateId)) {
      throw new IllegalArgumentException(
          "CertificateId '%s' not present in repository".formatted(certificateId)
      );
    }
    return certificateMap.get(certificateId);
  }

  @Override
  public boolean exists(CertificateId certificateId) {
    if (certificateId == null) {
      throw new IllegalArgumentException("CertificateId is null!");
    }
    return certificateMap.containsKey(certificateId);
  }

  @Override
  public List<Certificate> findByPatientByCareUnit(Patient patient, CareUnit careUnit) {
    return certificateMap.values().stream()
        .filter(
            certificate -> certificate.certificateMetaData().patient().id().equals(patient.id())
        )
        .filter(certificate -> certificate.certificateMetaData().careUnit().hsaId()
            .equals(careUnit.hsaId())
        )
        .toList();
  }

  @Override
  public List<Certificate> findByPatientBySubUnit(Patient patient, SubUnit subUnit) {
    return certificateMap.values().stream()
        .filter(
            certificate -> certificate.certificateMetaData().patient().id().equals(patient.id())
        )
        .filter(certificate -> certificate.certificateMetaData().issuingUnit().hsaId()
            .equals(subUnit.hsaId())
        )
        .toList();
  }
}
