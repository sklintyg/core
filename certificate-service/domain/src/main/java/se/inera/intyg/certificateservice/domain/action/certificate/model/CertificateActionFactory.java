package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.common.model.Role;

@RequiredArgsConstructor
public class CertificateActionFactory {

  private final CertificateActionConfigurationRepository certificateActionConfigurationRepository;

  public CertificateAction create(CertificateActionSpecification actionSpecification) {
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
                  new ActionRuleStatus(List.of(Status.DRAFT)),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleCertificateTypeActiveForUnit(
                      certificateActionConfigurationRepository)
              )
          )
          .build();
      case SEND -> CertificateActionSend.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleBlockTestIndicatedPerson(),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleRole(actionSpecification.allowedRoles()),
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleSent(false),
                  new ActionRuleChildRelationNoMatch(List.of(RelationType.REPLACE),
                      List.of(Status.DRAFT, Status.REVOKED)),
                  new ActionRuleProtectedPerson()
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
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleProtectedPerson()
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
                  new ActionRuleNoComplementMessages(),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleInactiveUnit(),
                  new ActionRulePatientAlive()
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
                  new ActionRuleNoComplementMessages(),
                  new ActionRuleProtectedPerson()
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
                  new ActionRuleUserAllowCopy(),
                  new ActionRuleProtectedPerson(),
                  new ActionRulePatientAlive(),
                  new ActionRuleChildRelationNoMatch(
                      List.of(RelationType.REPLACE, RelationType.COMPLEMENT),
                      List.of(Status.DRAFT, Status.REVOKED)
                  )
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
                  new ActionRuleSent(false),
                  new ActionRuleProtectedPerson()
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
                  new ActionRuleChildRelationNoMatch(List.of(RelationType.COMPLEMENT),
                      List.of(Status.REVOKED))
              )
          )
          .build();
      case MESSAGES -> CertificateActionMessages.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleSent(true),
                  new ActionRuleStatus(List.of(Status.SIGNED, Status.REVOKED))
              )
          )
          .build();
      case CREATE_MESSAGE -> CertificateActionCreateMessage.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleSent(true),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleUserNotBlocked()
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
                  new ActionRuleProtectedPerson(),
                  new ActionRuleUserNotBlocked()
              )
          )
          .build();
      case MESSAGES_ADMINISTRATIVE -> CertificateActionMessagesAdministrative.builder()
          .certificateActionSpecification(actionSpecification)
          .enabled(actionSpecification.enabled())
          .actionRules(
              List.of(
                  new ActionRuleSent(true),
                  new ActionRuleStatus(List.of(Status.SIGNED, Status.REVOKED))
              )
          )
          .build();
      case FORWARD_CERTIFICATE -> CertificateActionForwardCertificate.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.DRAFT)),
                  new ActionRuleRole(List.of(Role.CARE_ADMIN, Role.NURSE, Role.MIDWIFE)),
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleInactiveUnit(),
                  new ActionRuleUserHasAccessScope(
                      List.of(AccessScope.WITHIN_CARE_UNIT)
                  )
              )
          )
          .build();
      case FORWARD_MESSAGE -> CertificateActionForwardMessage.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleSent(true),
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleInactiveUnit(),
                  new ActionRuleUserHasAccessScope(
                      List.of(AccessScope.WITHIN_CARE_UNIT)
                  )
              )
          )
          .build();
      case HANDLE_COMPLEMENT -> CertificateActionHandleComplement.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleSent(true),
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(),
                  new ActionRuleInactiveUnit(),
                  new ActionRulePatientAlive()
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
      case SAVE_ANSWER -> CertificateActionSaveAnswer.builder()
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
      case DELETE_ANSWER -> CertificateActionDeleteAnswer.builder()
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
      case SEND_ANSWER -> CertificateActionSendAnswer.builder()
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
      case HANDLE_MESSAGE -> CertificateActionHandleMessage.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleSent(true),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson()
              )
          )
          .build();
      case READY_FOR_SIGN -> CertificateActionReadyForSign.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(
                      List.of(Status.DRAFT)),
                  new ActionRuleUserHasAccessScope(
                      List.of(AccessScope.WITHIN_CARE_PROVIDER, AccessScope.ALL_CARE_PROVIDERS)),
                  new ActionRuleRole(
                      List.of(Role.CARE_ADMIN, Role.NURSE, Role.MIDWIFE)
                  )
              )
          )
          .build();
      case LIST_CERTIFICATE_TYPE -> CertificateActionListCertificateType.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleRole(actionSpecification.allowedRoles())
              )
          )
          .build();
      case QUESTIONS_NOT_AVAILABLE -> CertificateActionQuestionsNotAvailable.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleSent(false))
          )
          .build();

    };
  }
}