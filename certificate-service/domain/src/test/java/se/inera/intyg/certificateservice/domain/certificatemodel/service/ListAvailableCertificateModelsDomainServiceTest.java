package se.inera.intyg.certificateservice.domain.certificatemodel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.LIST_CERTIFICATE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_VARDCENTRAL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessUnitConfiguration;

@ExtendWith(MockitoExtension.class)
class ListAvailableCertificateModelsDomainServiceTest {

  private static final CertificateType CERTIFICATE_TYPE_1 = new CertificateType("type1");
  private static final CertificateType CERTIFICATE_TYPE_2 = new CertificateType("type2");
  private static final CertificateModelId CERTIFICATE_MODEL_ID_1 = CertificateModelId.builder()
      .type(CERTIFICATE_TYPE_1)
      .build();
  private static final CertificateModelId CERTIFICATE_MODEL_ID_2 = CertificateModelId.builder()
      .type(CERTIFICATE_TYPE_2)
      .build();
  @Mock
  CertificateModel certificateModelOne;
  @Mock
  CertificateModel certificateModelTwo;
  @Mock
  CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  @Mock
  CertificateModelRepository certificateModelRepository;
  @InjectMocks
  ListAvailableCertificateModelsDomainService listAvailableCertificateModelsDomainService;

  @Test
  void shallFilterOnCertificateActionTypeAccessForRole() {
    final var certificateModels = List.of(
        certificateModelOne,
        certificateModelTwo
    );

    final var actionEvaluation = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .build();

    doReturn(certificateModels).when(certificateModelRepository).findAllActive();
    doReturn(CERTIFICATE_MODEL_ID_1).when(certificateModelOne).id();

    doReturn(true).when(certificateModelOne)
        .allowTo(LIST_CERTIFICATE_TYPE, Optional.of(actionEvaluation));
    doReturn(false).when(certificateModelTwo)
        .allowTo(LIST_CERTIFICATE_TYPE, Optional.of(actionEvaluation));

    final var actualCertificateModels = listAvailableCertificateModelsDomainService.get(
        actionEvaluation);

    assertEquals(List.of(certificateModelOne), actualCertificateModels);
  }


  @Test
  void shallFilterOnUnitAccessEvaluation() {
    final var certificateModels = List.of(
        certificateModelOne,
        certificateModelTwo
    );

    final var actionEvaluation = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .careProvider(ALFA_REGIONEN)
        .careUnit(ALFA_VARDCENTRAL)
        .build();

    doReturn(certificateModels).when(certificateModelRepository).findAllActive();
    doReturn(CERTIFICATE_MODEL_ID_1).when(certificateModelOne).id();
    doReturn(CERTIFICATE_MODEL_ID_2).when(certificateModelTwo).id();

    doReturn(true).when(certificateModelOne)
        .allowTo(LIST_CERTIFICATE_TYPE, Optional.of(actionEvaluation));
    doReturn(true).when(certificateModelTwo)
        .allowTo(LIST_CERTIFICATE_TYPE, Optional.of(actionEvaluation));

    final var certificateAccessConfigurations = List.of(
        CertificateAccessConfiguration.builder()
            .configuration(
                List.of(
                    CertificateAccessUnitConfiguration.builder()
                        .fromDateTime(LocalDateTime.now().minusDays(1))
                        .type("block")
                        .issuedOnUnit(
                            List.of(ALFA_ALLERGIMOTTAGNINGEN.hsaId().id())
                        )
                        .build()
                )
            )
            .build()
    );

    doReturn(Collections.emptyList()).when(certificateActionConfigurationRepository)
        .findAccessConfiguration(CERTIFICATE_TYPE_1);
    doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
        .findAccessConfiguration(CERTIFICATE_TYPE_2);

    final var actualCertificateModels = listAvailableCertificateModelsDomainService.get(
        actionEvaluation);

    assertEquals(List.of(certificateModelOne), actualCertificateModels);
  }

  @Test
  void shallReturnOnlyLatestVersionWhenMultipleMajorVersionsExist() {
    final var actionEvaluation = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .careProvider(ALFA_REGIONEN)
        .careUnit(ALFA_VARDCENTRAL)
        .build();

    doReturn(List.of(certificateModelOne, certificateModelTwo)).when(certificateModelRepository)
        .findAllActive();

    doReturn(CertificateModelId.builder()
        .type(CERTIFICATE_TYPE_1)
        .version(new CertificateVersion("1.0"))
        .build()).when(certificateModelOne).id();

    doReturn(CertificateModelId.builder()
        .type(CERTIFICATE_TYPE_1)
        .version(new CertificateVersion("2.0"))
        .build()).when(certificateModelTwo).id();

    doReturn(true).when(certificateModelOne)
        .allowTo(LIST_CERTIFICATE_TYPE, Optional.of(actionEvaluation));
    doReturn(true).when(certificateModelTwo)
        .allowTo(LIST_CERTIFICATE_TYPE, Optional.of(actionEvaluation));

    doReturn(Collections.emptyList()).when(certificateActionConfigurationRepository)
        .findAccessConfiguration(CERTIFICATE_TYPE_1);

    final var actualCertificateModels = listAvailableCertificateModelsDomainService.getLatestVersions(
        actionEvaluation);

    assertEquals(1, actualCertificateModels.size());
    assertEquals("2.0", actualCertificateModels.getFirst().id().version().version());
  }

  @Test
  void shallReturnOnlyLatestVersionWhenMultipleMinorVersionsExist() {
    final var actionEvaluation = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .careProvider(ALFA_REGIONEN)
        .careUnit(ALFA_VARDCENTRAL)
        .build();

    doReturn(List.of(certificateModelOne, certificateModelTwo)).when(certificateModelRepository)
        .findAllActive();

    doReturn(CertificateModelId.builder()
        .type(CERTIFICATE_TYPE_1)
        .version(new CertificateVersion("1.0"))
        .build()).when(certificateModelOne).id();

    doReturn(CertificateModelId.builder()
        .type(CERTIFICATE_TYPE_1)
        .version(new CertificateVersion("1.5"))
        .build()).when(certificateModelTwo).id();

    doReturn(true).when(certificateModelOne)
        .allowTo(LIST_CERTIFICATE_TYPE, Optional.of(actionEvaluation));
    doReturn(true).when(certificateModelTwo)
        .allowTo(LIST_CERTIFICATE_TYPE, Optional.of(actionEvaluation));

    doReturn(Collections.emptyList()).when(certificateActionConfigurationRepository)
        .findAccessConfiguration(CERTIFICATE_TYPE_1);

    final var actualCertificateModels = listAvailableCertificateModelsDomainService.getLatestVersions(
        actionEvaluation);

    assertEquals(1, actualCertificateModels.size());
    assertEquals("1.5", actualCertificateModels.getFirst().id().version().version());
  }
}