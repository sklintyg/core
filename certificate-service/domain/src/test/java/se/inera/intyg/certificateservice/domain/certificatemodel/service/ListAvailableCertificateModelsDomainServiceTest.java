package se.inera.intyg.certificateservice.domain.certificatemodel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateUnitAccessEvaluationRepository;
import se.inera.intyg.certificateservice.domain.common.model.Role;

@ExtendWith(MockitoExtension.class)
class ListAvailableCertificateModelsDomainServiceTest {

  private static final CertificateModel CERTIFICATE_MODEL_FOR_DOCTORS_1 = CertificateModel.builder()
      .description("forDoctor1")
      .certificateActionSpecifications(
          List.of(
              CertificateActionSpecification.builder()
                  .certificateActionType(CertificateActionType.ACCESS_FOR_ROLES)
                  .allowedRoles(List.of(Role.DOCTOR))
                  .build()
          )
      )
      .build();
  private static final CertificateModel CERTIFICATE_MODEL_FOR_DOCTORS_2 = CertificateModel.builder()
      .description("forDoctor2")
      .certificateActionSpecifications(
          List.of(
              CertificateActionSpecification.builder()
                  .certificateActionType(CertificateActionType.ACCESS_FOR_ROLES)
                  .allowedRoles(List.of(Role.DOCTOR))
                  .build()
          )
      )
      .build();
  private static final CertificateModel CERTIFICATE_MODEL_FOR_CARE_ADMIN = CertificateModel.builder()
      .certificateActionSpecifications(
          List.of(
              CertificateActionSpecification.builder()
                  .certificateActionType(CertificateActionType.ACCESS_FOR_ROLES)
                  .allowedRoles(List.of(Role.CARE_ADMIN))
                  .build()
          )
      )
      .build();
  @Mock
  CertificateUnitAccessEvaluationRepository certificateUnitAccessEvaluationRepository;
  @Mock
  CertificateModelRepository certificateModelRepository;
  @InjectMocks
  ListAvailableCertificateModelsDomainService listAvailableCertificateModelsDomainService;

  @Test
  void shallFilterOnCertificateActionTypeAccessForRole() {
    final var certificateModels = List.of(
        CERTIFICATE_MODEL_FOR_DOCTORS_1,
        CERTIFICATE_MODEL_FOR_CARE_ADMIN
    );

    final var actionEvaluation = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .build();

    doReturn(certificateModels).when(certificateModelRepository).findAllActive();
    doReturn(true).when(certificateUnitAccessEvaluationRepository)
        .evaluate(actionEvaluation, CERTIFICATE_MODEL_FOR_DOCTORS_1);

    final var actualCertificateModels = listAvailableCertificateModelsDomainService.get(
        actionEvaluation);

    assertEquals(List.of(CERTIFICATE_MODEL_FOR_DOCTORS_1), actualCertificateModels);
  }

  @Test
  void shallFilterOnUnitAccessEvaluation() {
    final var certificateModels = List.of(
        CERTIFICATE_MODEL_FOR_DOCTORS_1,
        CERTIFICATE_MODEL_FOR_DOCTORS_2,
        CERTIFICATE_MODEL_FOR_CARE_ADMIN
    );

    final var actionEvaluation = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .build();

    doReturn(certificateModels).when(certificateModelRepository).findAllActive();
    doReturn(true).when(certificateUnitAccessEvaluationRepository)
        .evaluate(actionEvaluation, CERTIFICATE_MODEL_FOR_DOCTORS_1);
    doReturn(false).when(certificateUnitAccessEvaluationRepository)
        .evaluate(actionEvaluation, CERTIFICATE_MODEL_FOR_DOCTORS_2);

    final var actualCertificateModels = listAvailableCertificateModelsDomainService.get(
        actionEvaluation);

    assertEquals(List.of(CERTIFICATE_MODEL_FOR_DOCTORS_1), actualCertificateModels);
  }
}
