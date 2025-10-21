package se.inera.intyg.certificateservice.application.unit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.StaffDTO;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.CertificatesRequestFactory;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoResponse;
import se.inera.intyg.certificateservice.application.unit.service.validator.GetUnitCertificatesInfoRequestValidator;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitCertificatesInfoDomainService;

@Service
@RequiredArgsConstructor
public class GetUnitCertificatesInfoService {

  private final GetUnitCertificatesInfoRequestValidator getUnitCertificatesInfoRequestValidator;
  private final GetUnitCertificatesInfoDomainService getUnitCertificatesInfoDomainService;
  private final ActionEvaluationFactory actionEvaluationFactory;

  public GetUnitCertificatesInfoResponse get(
      GetUnitCertificatesInfoRequest getUnitCertificatesInfoRequest) {
    getUnitCertificatesInfoRequestValidator.validate(getUnitCertificatesInfoRequest);

    final var actionEvaluation = actionEvaluationFactory.create(
        getUnitCertificatesInfoRequest.getUser(),
        getUnitCertificatesInfoRequest.getUnit(),
        getUnitCertificatesInfoRequest.getCareUnit(),
        getUnitCertificatesInfoRequest.getCareProvider()
    );

    final var staffs = getUnitCertificatesInfoDomainService.get(
        CertificatesRequestFactory.create(),
        actionEvaluation
    );

    return GetUnitCertificatesInfoResponse.builder()
        .staffs(
            staffs.stream()
                .map(staff ->
                    StaffDTO.builder()
                        .personId(staff.hsaId().id())
                        .firstName(staff.name().firstName())
                        .middleName(staff.name().middleName())
                        .lastName(staff.name().lastName())
                        .fullName(staff.name().fullName())
                        .build()
                )
                .toList()
        )
        .build();
  }
}
