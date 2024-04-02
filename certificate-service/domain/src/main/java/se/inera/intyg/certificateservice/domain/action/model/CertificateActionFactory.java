package se.inera.intyg.certificateservice.domain.action.model;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Role;

public class CertificateActionFactory {

  private CertificateActionFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static CertificateAction create(CertificateActionSpecification actionSpecification) {
    return switch (actionSpecification.certificateActionType()) {
      case CREATE -> CertificateActionCreate.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleProtectedPerson()
              )
          )
          .build();
      case READ -> CertificateActionRead.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinCareUnit(),
                  new ActionRuleProtectedPerson()
              )
          )
          .build();
      case UPDATE -> CertificateActionUpdate.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinCareUnit(),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleStatus(List.of(Status.DRAFT))
              )
          )
          .build();
      case DELETE -> CertificateActionDelete.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinCareUnit(),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleStatus(List.of(Status.DRAFT))
              )
          )
          .build();
      case SIGN -> CertificateActionSign.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinCareUnit(),
                  new ActionRuleRole(
                      List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
                  ),
                  new ActionRuleStatus(List.of(Status.DRAFT))
              )
          )
          .build();
      case SEND -> CertificateActionSend.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinCareUnit(),
                  new ActionRuleRole(
                      List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
                  ),
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleSent(false)
              )
          )
          .build();
      case PRINT -> CertificateActionPrint.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinCareUnit(),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleStatus(List.of(Status.SIGNED, Status.DRAFT))
              )
          )
          .build();
      case REVOKE -> CertificateActionRevoke.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinCareUnit(),
                  new ActionRuleRole(
                      List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
                  ),
                  new ActionRuleStatus(List.of(Status.SIGNED))
              )
          )
          .build();
    };
  }
}

