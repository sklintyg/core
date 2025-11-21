package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateExportPage;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.PlaceholderCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PlaceholderCertificateRequest;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.service.PatientInformationProvider;
import se.inera.intyg.certificateservice.testability.certificate.service.repository.TestabilityCertificateRepository;

@Repository
@RequiredArgsConstructor
public class CertificateRepositoryImpl implements TestabilityCertificateRepository {

  private final JpaCertificateRepository jpaCertificateRepository;
  private final PatientInformationProvider patientInformationProvider;

  @Override
  public Certificate create(CertificateModel certificateModel) {
    return jpaCertificateRepository.create(certificateModel, this);
  }

  @Override
  public Certificate createFromPlaceholder(PlaceholderCertificateRequest request,
      CertificateModel model) {
    return jpaCertificateRepository.createFromPlaceholder(request, model, this);
  }

  @Override
  public Certificate save(Certificate certificate) {
    final var savedCertificate = jpaCertificateRepository.save(certificate, this);
    updatePatient(savedCertificate);
    return savedCertificate;
  }

  @Override
  public Certificate getById(CertificateId certificateId) {
    final var certificate = jpaCertificateRepository.getById(certificateId, this);
    updatePatient(certificate);
    return certificate;
  }

  @Override
  public List<Certificate> getByIds(List<CertificateId> certificateIds) {
    final var certificates = jpaCertificateRepository.getByIds(certificateIds, this);
    updatePatient(certificates);
    return certificates;
  }

  @Override
  public List<Certificate> findByIds(List<CertificateId> certificateIds) {
    final var certificates = jpaCertificateRepository.findByIds(certificateIds, this);
    updatePatient(certificates);
    return certificates;
  }

  @Override
  public boolean exists(CertificateId certificateId) {
    return jpaCertificateRepository.exists(certificateId);
  }

  @Override
  public List<Certificate> findByCertificatesRequest(CertificatesRequest request) {
    final var certificates = jpaCertificateRepository.findByCertificatesRequest(request, this);
    updatePatient(certificates);
    return certificates;
  }

  @Override
  public List<CertificateId> findIdsByCreatedBeforeAndStatusIn(CertificatesRequest request) {
    return jpaCertificateRepository.findIdsByCreatedBeforeAndStatusIn(request);
  }

  @Override
  public CertificateExportPage getExportByCareProviderId(HsaId careProviderId, int page, int size) {
    return jpaCertificateRepository.getExportByCareProviderId(careProviderId, page, size, this);
  }

  @Override
  public long deleteByCareProviderId(HsaId careProviderId) {
    return jpaCertificateRepository.deleteByCareProviderId(careProviderId);
  }

  @Override
  public boolean placeholderExists(CertificateId certificateId) {
    return jpaCertificateRepository.placeholderExists(certificateId);
  }

  @Override
  public PlaceholderCertificate getPlaceholderById(CertificateId certificateId) {
    return jpaCertificateRepository.getPlaceholderById(certificateId);
  }

  @Override
  public PlaceholderCertificate save(PlaceholderCertificate placeholderCertificate) {
    return jpaCertificateRepository.save(placeholderCertificate);
  }

  @Override
  public CertificateMetaData getMetadataFromSignInstance(CertificateMetaData certificateMetaData,
      LocalDateTime signed) {
    return jpaCertificateRepository.getMetadataFromSignInstance(certificateMetaData, signed);
  }

  @Override
  public void updateCertificateMetadataFromSignInstances(List<Certificate> certificates) {
    jpaCertificateRepository.updateCertificateMetadataFromSignInstances(certificates);
  }

  @Override
  public Certificate insert(Certificate certificate, Revision revision) {
    return jpaCertificateRepository.insert(certificate, revision, this);
  }

  @Override
  public void remove(List<CertificateId> certificateIds) {
    jpaCertificateRepository.remove(certificateIds);
  }

  @Override
  public List<CertificateId> findValidSickLeavesCertificateIdsByIds(
      List<CertificateId> certificateId) {
    return jpaCertificateRepository.findValidSickLeavesByIds(certificateId);
  }

  private void updatePatient(List<Certificate> certificates) {
    final var patients = patientInformationProvider.findPatients(
            certificates.stream()
                .map(Certificate::certificateMetaData)
                .map(CertificateMetaData::patient)
                .map(Patient::id)
                .toList()
        )
        .stream()
        .collect(Collectors.toMap(
            Patient::id,
            Function.identity()
        ));

    certificates.forEach(certificate -> {
          if (patients.containsKey(certificate.certificateMetaData().patient().id())) {
            final var patient = patients.get(certificate.certificateMetaData().patient().id());
            certificate.updateMetadata(patient);
          }
        }
    );
  }

  private void updatePatient(Certificate certificate) {
    final var patient = patientInformationProvider.findPatient(
        certificate.certificateMetaData().patient().id()
    );

    if (patient.isEmpty()) {
      return;
    }

    certificate.updateMetadata(patient.get());
  }
}