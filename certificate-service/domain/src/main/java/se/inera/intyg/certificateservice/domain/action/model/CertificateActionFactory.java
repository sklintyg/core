package se.inera.intyg.certificateservice.domain.action.model;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
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
                  new ActionRuleProtectedPerson(),
                  new ActionRulePatientAlive(),
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleInactiveUnit(),
                  new ActionRuleRole(
                      List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
                          Role.CARE_ADMIN)
                  )
              )
          )
          .build();
      case READ -> CertificateActionRead.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.ALL_CARE_PROVIDERS),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleRole(
                      List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
                          Role.CARE_ADMIN)
                  )
              )
          )
          .build();
      case UPDATE -> CertificateActionUpdate.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleStatus(List.of(Status.DRAFT)),
                  new ActionRuleRole(
                      List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
                          Role.CARE_ADMIN)
                  )
              )
          )
          .build();
      case DELETE -> CertificateActionDelete.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleStatus(List.of(Status.DRAFT)),
                  new ActionRuleRole(
                      List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
                          Role.CARE_ADMIN)
                  )
              )
          )
          .build();
      case SIGN -> CertificateActionSign.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleRole(actionSpecification.allowedRoles()),
                  new ActionRuleStatus(List.of(Status.DRAFT))
              )
          )
          .build();
      case SEND -> CertificateActionSend.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleRole(actionSpecification.allowedRoles()),
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleSent(false),
                  new ActionRuleChildRelationNoMatch(List.of(RelationType.REPLACE),
                      List.of(Status.DRAFT, Status.REVOKED))
              )
          )
          .build();
      case PRINT -> CertificateActionPrint.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_PROVIDER),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleStatus(List.of(Status.SIGNED, Status.DRAFT))
              )
          )
          .build();
      case REVOKE -> CertificateActionRevoke.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleRole(actionSpecification.allowedRoles()),
                  new ActionRuleStatus(List.of(Status.SIGNED))
              )
          )
          .build();
      case REPLACE -> CertificateActionReplace.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleRole(
                      List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
                  ),
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleChildRelationNoMatch(List.of(RelationType.REPLACE),
                      List.of(Status.REVOKED)),
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleNoComplementMessages()
              )
          )
          .build();
      case REPLACE_CONTINUE -> CertificateActionReplaceContinue.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleRole(
                      List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
                  ),
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleChildRelationMatch(List.of(RelationType.REPLACE)),
                  new ActionRuleNoComplementMessages()
              )
          )
          .build();
      case RENEW -> CertificateActionRenew.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleRole(
                      List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
                  ),
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleUserAllowCopy()
              )
          )
          .build();
      case RECEIVE_COMPLEMENT -> CertificateActionReceiveComplement.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED))
              )
          )
          .build();
      case RECEIVE_QUESTION -> CertificateActionReceiveQuestion.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED))
              )
          )
          .build();
      case RECEIVE_ANSWER -> CertificateActionReceiveAnswer.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED))
              )
          )
          .build();
      case RECEIVE_REMINDER -> CertificateActionReceiveReminder.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED))
              )
          )
          .build();
      case SEND_AFTER_SIGN -> CertificateActionSendAfterSign.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleSent(false)
              )
          )
          .build();
      case COMPLEMENT -> CertificateActionComplement.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleRole(
                      List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
                  ),
                  new ActionRuleSent(true),
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleInactiveUnit(),
                  new ActionRulePatientAlive(),
                  new ActionRuleUserAllowCopy(),
                  new ActionRuleComplementMessages(),
                  new ActionRuleChildRelationNoMatch(List.of(RelationType.COMPLEMENT),
                      List.of(Status.REVOKED))
              )
          )
          .build();
      case CANNOT_COMPLEMENT -> CertificateActionCannotComplement.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleInactiveUnit(),
                  new ActionRulePatientAlive(),
                  new ActionRuleUserAllowCopy(),
                  new ActionRuleChildRelationNoMatch(List.of(RelationType.COMPLEMENT),
                      List.of(Status.REVOKED))
              )
          )
          .build();
      case MESSAGES -> CertificateActionMessages.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED, Status.REVOKED))
              )
          )
          .build();
      case CREATE_MESSAGE -> CertificateActionCreateMessage.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED, Status.REVOKED)),
                  new ActionRuleSent(true),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson()
              )
          )
          .build();
      case ANSWER_MESSAGE -> CertificateActionAnswerMessage.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED, Status.REVOKED)),
                  new ActionRuleSent(true),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson()
              )
          )
          .build();
      case MESSAGES_ADMINISTRATIVE -> CertificateActionMessagesAdministrative.builder()
          .certificateActionSpecification(actionSpecification)
          .enabled(actionSpecification.enabled())
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED, Status.REVOKED))
              )
          )
          .build();
      case FORWARD_MESSAGE -> CertificateActionForwardMessage.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleInactiveUnit(),
                  new ActionRulePatientAlive(),
                  new ActionRuleUserAllowCopy()
              )
          )
          .build();
      case HANDLE_COMPLEMENT -> CertificateActionHandleComplement.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleInactiveUnit(),
                  new ActionRulePatientAlive(),
                  new ActionRuleUserAllowCopy()
              )
          )
          .build();
      case SAVE_MESSAGE -> CertificateActionSaveMessage.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED, Status.REVOKED)),
                  new ActionRuleSent(true),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson()
              )
          )
          .build();
      case DELETE_MESSAGE -> CertificateActionDeleteMessage.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED, Status.REVOKED)),
                  new ActionRuleSent(true),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson()
              )
          )
          .build();
      case SEND_MESSAGE -> CertificateActionSendMessage.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED, Status.REVOKED)),
                  new ActionRuleSent(true),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson()
              )
          )
          .build();
    };
  }
}

