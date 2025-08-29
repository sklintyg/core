package se.inera.intyg.certificateservice.application.certificate.service;

import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.toStatus;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewExternalCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.RenewExternalCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.service.RenewExternalCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PlaceholderCertificateRequest;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.unit.model.Inactive;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.domain.unit.model.UnitAddress;
import se.inera.intyg.certificateservice.domain.unit.model.UnitContactInfo;
import se.inera.intyg.certificateservice.domain.unit.model.UnitName;
import se.inera.intyg.certificateservice.domain.unit.model.WorkplaceCode;

@Service
@RequiredArgsConstructor
public class RenewExternalCertificateService {

  private final RenewExternalCertificateRequestValidator renewExternalCertificateRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final RenewExternalCertificateDomainService renewExternalCertificateDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  @Transactional
  public RenewCertificateResponse renew(
      RenewExternalCertificateRequest renewExternalCertificateRequest,
      String certificateId) {
    renewExternalCertificateRequestValidator.validate(
        renewExternalCertificateRequest,
        certificateId
    );

    final var actionEvaluation = actionEvaluationFactory.create(
        renewExternalCertificateRequest.getPatient(),
        renewExternalCertificateRequest.getUser(),
        renewExternalCertificateRequest.getUnit(),
        renewExternalCertificateRequest.getCareUnit(),
        renewExternalCertificateRequest.getCareProvider()
    );

    final var certificate = renewExternalCertificateDomainService.renew(
        actionEvaluation,
        renewExternalCertificateRequest.getExternalReference() != null
            ? new ExternalReference(renewExternalCertificateRequest.getExternalReference())
            : null,
        getPlaceholderRequest(renewExternalCertificateRequest, certificateId)
    );

    return RenewCertificateResponse.builder()
        .certificate(
            certificateConverter.convert(
                certificate,
                certificate.actionsInclude(Optional.of(actionEvaluation)).stream()
                    .map(certificateAction ->
                        resourceLinkConverter.convert(
                            certificateAction,
                            Optional.of(certificate),
                            actionEvaluation
                        )
                    )
                    .toList(),
                actionEvaluation
            )
        )
        .build();
  }

  private static PlaceholderCertificateRequest getPlaceholderRequest(
      RenewExternalCertificateRequest renewExternalCertificateRequest, String certificateId) {
    final var unit = renewExternalCertificateRequest.getIssuingUnit();
    return PlaceholderCertificateRequest.builder()
        .certificateId(new CertificateId(certificateId))
        .certificateModelId(certificateModelId(renewExternalCertificateRequest))
        .status(toStatus(renewExternalCertificateRequest.getStatus()))
        .prefillXml(new Xml(renewExternalCertificateRequest.getPrefillXml().value()))
        .issuingUnit(getIssuingUnit(unit))
        .build();
  }

  private static SubUnit getIssuingUnit(UnitDTO unit) {
    return SubUnit.builder()
        .hsaId(new HsaId(unit.getId()))
        .name(new UnitName(unit.getName()))
        .address(
            UnitAddress.builder()
                .address(unit.getAddress())
                .zipCode(unit.getZipCode())
                .city(unit.getCity())
                .build()
        )
        .contactInfo(
            UnitContactInfo.builder()
                .phoneNumber(unit.getPhoneNumber())
                .email(unit.getEmail())
                .build()
        )
        .inactive(unit.getInactive() != null ? new Inactive(unit.getInactive()) : null)
        .workplaceCode(
            unit.getWorkplaceCode() != null ? new WorkplaceCode(unit.getWorkplaceCode()) : null
        )
        .build();
  }

  private static CertificateModelId certificateModelId(
      RenewExternalCertificateRequest renewExternalCertificateRequest) {
    return CertificateModelId.builder()
        .type(
            new CertificateType(
                renewExternalCertificateRequest.getCertificateModelId().getType()
            )
        )
        .version(
            new CertificateVersion(
                renewExternalCertificateRequest.getCertificateModelId().getVersion()
            )
        )
        .build();
  }
}