package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateModelId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePrefillXml;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewExternalCertificateRequest;

@Component
public class RenewExternalCertificateRequestValidator {

  public void validate(RenewExternalCertificateRequest renewCertificateRequest,
      String certificateId) {
    validateUser(renewCertificateRequest.getUser());
    validateUnitExtended(renewCertificateRequest.getUnit(), "Unit");
    validateUnit(renewCertificateRequest.getCareUnit(), "CareUnit");
    validateUnit(renewCertificateRequest.getCareProvider(), "CareProvider");
    validatePatient(renewCertificateRequest.getPatient());
    validateCertificateId(certificateId);
    validateCertificateModelId(renewCertificateRequest.getCertificateModelId());
    validatePrefillXml(renewCertificateRequest.getPrefillXml());
    if (renewCertificateRequest.getStatus() == null) {
      throw new IllegalArgumentException("Required parameter missing: status");
    }
    validateUnit(renewCertificateRequest.getIssuingUnit(), "IssuingUnit");
  }
}