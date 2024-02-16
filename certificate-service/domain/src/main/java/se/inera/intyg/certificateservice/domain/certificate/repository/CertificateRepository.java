package se.inera.intyg.certificateservice.domain.certificate.repository;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;

public interface CertificateRepository {

  Certificate create(CertificateModel certificateModel);

  Certificate save(Certificate certificate);

  Certificate getById(CertificateId certificateId);

  boolean exists(CertificateId certificateId);

  List<Certificate> findByPatientByCareUnit(Patient patient, CareUnit careUnit);

  List<Certificate> findByPatientBySubUnit(Patient patient, SubUnit subUnit);
}
