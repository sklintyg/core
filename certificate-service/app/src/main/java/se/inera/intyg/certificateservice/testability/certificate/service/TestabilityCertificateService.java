package se.inera.intyg.certificateservice.testability.certificate.service;

import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.CertificateConverter;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.testability.certificate.dto.SupportedCertificateTypesResponse;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityCertificateRequest;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityResetCertificateRequest;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityStatusTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.repository.TestabilityCertificateModelRepository;
import se.inera.intyg.certificateservice.testability.certificate.service.repository.TestabilityCertificateRepository;

@Profile(TESTABILITY_PROFILE)
@Service
@RequiredArgsConstructor
public class TestabilityCertificateService {

  private final TestabilityCertificateModelRepository testabilityCertificateModelRepository;
  private final TestabilityCertificateRepository testabilityCertificateRepository;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final CertificateConverter certificateConverter;

  public CreateCertificateResponse create(
      TestabilityCertificateRequest testabilityCertificateRequest) {
    final var certificateModelIdDTO = testabilityCertificateRequest.getCertificateModelId();
    final var certificateModelId = CertificateModelId.builder()
        .type(new CertificateType(certificateModelIdDTO.getType()))
        .version(new CertificateVersion(certificateModelIdDTO.getVersion()))
        .build();

    final var certificateModel = testabilityCertificateModelRepository.all().stream()
        .filter(model -> model.id().equals(certificateModelId))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
                "No certificateModelId matching ä%s'".formatted(certificateModelId)
            )
        );

    final var actionEvaluation = actionEvaluationFactory.create(
        testabilityCertificateRequest.getPatient(),
        testabilityCertificateRequest.getUser(),
        testabilityCertificateRequest.getUnit(),
        testabilityCertificateRequest.getCareUnit(),
        testabilityCertificateRequest.getCareProvider()
    );

    final var certificate = testabilityCertificateRepository.create(certificateModel);
    certificate.updateMetadata(actionEvaluation);
    testabilityCertificateRepository.insert(certificate);

    return CreateCertificateResponse.builder()
        .certificate(
            certificateConverter.convert(certificate)
        )
        .build();
  }

  public void reset(TestabilityResetCertificateRequest testabilityCertificateRequest) {
    testabilityCertificateRepository.remove(
        testabilityCertificateRequest.getCertificateIds().stream()
            .map(CertificateId::new)
            .toList()
    );
  }

  public List<SupportedCertificateTypesResponse> supportedTypes() {
    final var supportedCertificateTypesResponseMap = testabilityCertificateModelRepository.all()
        .stream()
        .collect(Collectors.toMap(
            CertificateModel::name,
            certificateModel ->
                SupportedCertificateTypesResponse.builder()
                    .type(certificateModel.id().type().type())
                    .internalType(certificateModel.id().type().type())
                    .versions(List.of(certificateModel.id().version().version()))
                    .name(certificateModel.name())
                    .fillType(Arrays.asList(TestabilityFillTypeDTO.values()))
                    .statuses(Arrays.asList(TestabilityStatusTypeDTO.values()))
                    .build(),
            (existingValue, newValue) -> {
              final var combinedVersions = new ArrayList<>(existingValue.getVersions());
              combinedVersions.addAll(newValue.getVersions());
              return existingValue.withVersions(combinedVersions);
            }
        ));
    return List.copyOf(supportedCertificateTypesResponseMap.values());
  }
}
