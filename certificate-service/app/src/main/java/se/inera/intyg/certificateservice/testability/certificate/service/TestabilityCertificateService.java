package se.inera.intyg.certificateservice.testability.certificate.service;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlSchemaValidator;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlSchematronValidator;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.testability.certificate.dto.SupportedCertificateTypesResponse;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityCertificateRequest;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityResetCertificateRequest;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityStatusTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;
import se.inera.intyg.certificateservice.testability.certificate.service.repository.TestabilityCertificateModelRepository;
import se.inera.intyg.certificateservice.testability.certificate.service.repository.TestabilityCertificateRepository;

@Profile(TESTABILITY_PROFILE)
@Service
@Transactional
@RequiredArgsConstructor
public class TestabilityCertificateService {

  private final TestabilityCertificateModelRepository testabilityCertificateModelRepository;
  private final TestabilityCertificateRepository testabilityCertificateRepository;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;
  private final List<TestabilityCertificateFillService> testabilityCertificateFillServices;
  private final XmlGenerator xmlGenerator;
  private final XmlSchematronValidator xmlSchematronValidator;
  private final XmlSchemaValidator xmlSchemaValidator;

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

    final var prefillData = prefillData(
        testabilityCertificateRequest, certificateModelId, certificateModel
    );

    final var unitContactInformation = unitContactInformation(
        testabilityCertificateRequest.getUnit());

    final var elementData = Stream.concat(
            prefillData.stream(),
            Stream.of(unitContactInformation)
        )
        .toList();

    certificate.updateData(
        elementData,
        certificate.revision(),
        actionEvaluation
    );

    if (CertificateStatusTypeDTO.SIGNED.equals(testabilityCertificateRequest.getStatus())) {
      certificate.sign(xmlGenerator, certificate.revision(), actionEvaluation,
          xmlSchematronValidator, xmlSchemaValidator);
    }

    testabilityCertificateRepository.insert(certificate);

    return CreateCertificateResponse.builder()
        .certificate(
            certificateConverter.convert(
                certificate,
                certificate.actions(actionEvaluation).stream()
                    .map(resourceLinkConverter::convert)
                    .toList())
        )
        .build();
  }

  private List<ElementData> prefillData(TestabilityCertificateRequest testabilityCertificateRequest,
      CertificateModelId certificateModelId, CertificateModel certificateModel) {
    return testabilityCertificateFillServices.stream()
        .filter(fillService -> fillService.certificateModelIds().contains(certificateModelId))
        .findAny()
        .map(fillService -> fillService.fill(certificateModel,
            testabilityCertificateRequest.getFillType()))
        .orElse(Collections.emptyList());
  }

  private static ElementData unitContactInformation(UnitDTO unit) {
    return ElementData.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .value(
            ElementValueUnitContactInformation.builder()
                .address(unit.getAddress())
                .city(unit.getCity())
                .zipCode(unit.getZipCode())
                .phoneNumber(unit.getPhoneNumber())
                .build()
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
