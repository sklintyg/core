package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;

@RequiredArgsConstructor
public class CertificateActionFactory {

  private final CertificateActionConfigurationRepository certificateActionConfigurationRepository;

  public CertificateAction create(CertificateActionSpecification actionSpecification) {
    return switch (actionSpecification.certificateActionType()) {
      case CREATE -> CertificateActionCreate.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRulePatientAlive(),
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleInactiveUnit(),
                  new ActionRuleRole(
                      actionSpecification.allowedRoles()
                  ),
                  new ActionRuleUserAgreement()
              )
          )
          .build();
      case READ -> CertificateActionRead.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.ALL_CARE_PROVIDERS),
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleRole(
                      actionSpecification.allowedRoles()
                  )
              )
          )
          .build();
      case UPDATE -> CertificateActionUpdate.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleStatus(List.of(Status.DRAFT)),
                  new ActionRuleRole(
                      actionSpecification.allowedRoles()
                  ),
                  new ActionRuleUserAgreement(),
                  new ActionRuleLimitedCertificateFunctionality(
                      certificateActionConfigurationRepository,
                      actionSpecification.certificateActionType()
                  ),
                  new ActionRuleActiveCertificateModel()
              )
          )
          .build();
      case DELETE -> CertificateActionDelete.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleStatus(List.of(Status.DRAFT)),
                  new ActionRuleRole(
                      actionSpecification.allowedRoles()
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleCertificateTypeActiveForUnit(
                      certificateActionConfigurationRepository
                  ),
                  new ActionRuleUserAgreement(),
                  new ActionRuleLimitedCertificateFunctionality(
                      certificateActionConfigurationRepository,
                      actionSpecification.certificateActionType()
                  ),
                  new ActionRuleActiveCertificateModel()
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleUserAgreement(),
                  new ActionRuleLimitedCertificateFunctionality(
                      certificateActionConfigurationRepository,
                      actionSpecification.certificateActionType()
                  ),
                  new ActionRuleActiveCertificateModel()
              )
          )
          .build();
      case PRINT -> CertificateActionPrint.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_PROVIDER),
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  )
              )
          )
          .build();
      case REPLACE -> CertificateActionReplace.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleRole(
                      actionSpecification.allowedRoles()
                  ),
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleChildRelationNoMatch(
                      List.of(RelationType.REPLACE),
                      List.of(Status.REVOKED)),
                  new ActionRuleChildRelationNoMatch(
                      List.of(RelationType.COMPLEMENT),
                      List.of(Status.DRAFT, Status.REVOKED)),
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleNoComplementMessages(),
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleInactiveUnit(),
                  new ActionRulePatientAlive(),
                  new ActionRuleUserAgreement(),
                  new ActionRuleLimitedCertificateFunctionality(
                      certificateActionConfigurationRepository,
                      actionSpecification.certificateActionType()
                  ),
                  new ActionRuleActiveCertificateModel()
              )
          )
          .build();
      case REPLACE_CONTINUE -> CertificateActionReplaceContinue.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleRole(
                      actionSpecification.allowedRoles()
                  ),
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleChildRelationMatch(List.of(RelationType.REPLACE)),
                  new ActionRuleNoComplementMessages(),
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleUserAgreement(),
                  new ActionRuleActiveCertificateModel()
              )
          )
          .build();
      case RENEW -> CertificateActionRenew.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.ALL_CARE_PROVIDERS),
                  new ActionRuleRole(
                      actionSpecification.allowedRoles()
                  ),
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleUserAllowCopy(),
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRulePatientAlive(),
                  new ActionRuleChildRelationNoMatch(
                      List.of(RelationType.REPLACE, RelationType.COMPLEMENT),
                      List.of(Status.DRAFT, Status.REVOKED)
                  ),
                  new ActionRuleUserAgreement(),
                  new ActionRuleActiveCertificateModel()
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleBlockTestIndicatedPerson(),
                  new ActionRuleUserAgreement()
              )
          )
          .build();
      case SEND_AFTER_COMPLEMENT -> CertificateActionSendAfterComplement.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleParentRelationMatch(List.of(RelationType.COMPLEMENT)),
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleSent(false),
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleBlockTestIndicatedPerson(),
                  new ActionRuleUserAgreement()
              )
          )
          .build();
      case COMPLEMENT -> CertificateActionComplement.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleRole(
                      actionSpecification.allowedRoles()
                  ),
                  new ActionRuleSent(true),
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleInactiveUnit(),
                  new ActionRulePatientAlive(),
                  new ActionRuleComplementMessages(),
                  new ActionRuleChildRelationNoMatch(
                      List.of(RelationType.COMPLEMENT),
                      List.of(Status.REVOKED)
                  ),
                  new ActionRuleUserAgreement(),
                  new ActionRuleActiveCertificateModel()
              )
          )
          .build();
      case CANNOT_COMPLEMENT -> CertificateActionCannotComplement.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleInactiveUnit(),
                  new ActionRulePatientAlive(),
                  new ActionRuleChildRelationNoMatch(
                      List.of(RelationType.COMPLEMENT),
                      List.of(Status.REVOKED)
                  ),
                  new ActionRuleUserAgreement()
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleUserAgreement()
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleUserAgreement()
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
                  new ActionRuleRole(actionSpecification.allowedRoles()),
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleInactiveUnit(),
                  new ActionRuleUserHasAccessScope(
                      List.of(AccessScope.WITHIN_CARE_UNIT)
                  ),
                  new ActionRuleUserAgreement(),
                  new ActionRuleLimitedCertificateFunctionality(
                      certificateActionConfigurationRepository,
                      actionSpecification.certificateActionType()
                  ),
                  new ActionRuleActiveCertificateModel()
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleInactiveUnit(),
                  new ActionRuleUserHasAccessScope(
                      List.of(AccessScope.WITHIN_CARE_UNIT)
                  ),
                  new ActionRuleUserAgreement(),
                  new ActionRuleActiveCertificateModel()
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleInactiveUnit(),
                  new ActionRulePatientAlive(),
                  new ActionRuleUserAgreement()
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleUserAgreement()
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleUserAgreement()
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleUserAgreement(),
                  new ActionRuleUserAgreement()
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleUserAgreement()
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleUserAgreement()
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleUserAgreement()
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
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleUserAgreement()
              )
          )
          .build();
      case READY_FOR_SIGN -> CertificateActionReadyForSign.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.DRAFT)),
                  new ActionRuleUserHasAccessScope(
                      List.of(AccessScope.WITHIN_CARE_PROVIDER, AccessScope.ALL_CARE_PROVIDERS)
                  ),
                  new ActionRuleRole(actionSpecification.allowedRoles()),
                  new ActionRuleUserAgreement(),
                  new ActionRuleLimitedCertificateFunctionality(
                      certificateActionConfigurationRepository,
                      actionSpecification.certificateActionType()
                  ),
                  new ActionRuleActiveCertificateModel()
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
                  new ActionRuleSent(false),
                  new ActionRuleStatus(List.of(Status.SIGNED))
              )
          )
          .build();
      case FORWARD_CERTIFICATE_FROM_LIST -> CertificateActionForwardCertificateFromList.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.DRAFT)),
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleInactiveUnit(),
                  new ActionRuleUserHasAccessScope(
                      List.of(AccessScope.WITHIN_CARE_UNIT)
                  ),
                  new ActionRuleUserAgreement(),
                  new ActionRuleLimitedCertificateFunctionality(
                      certificateActionConfigurationRepository,
                      actionSpecification.certificateActionType()
                  ),
                  new ActionRuleActiveCertificateModel()
              )
          )
          .build();
      case FMB -> CertificateActionFMB.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleStatus(List.of(Status.DRAFT))
              )
          )
          .build();
      case SRS_DRAFT -> CertificateActionSrsDraft.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleSrs(),
                  new ActionRuleStatus(List.of(Status.DRAFT))
              )
          )
          .build();
      case SRS_SIGNED -> CertificateActionSrsSigned.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleSrs(),
                  new ActionRuleStatus(List.of(Status.SIGNED))
              )
          )
          .build();
      case CREATE_DRAFT_FROM_CERTIFICATE -> CertificateActionCreateDraftFromCertificate.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleRole(
                      actionSpecification.allowedRoles()
                  ),
                  new ActionRuleStatus(List.of(Status.SIGNED)),
                  new ActionRuleUserNotBlocked(),
                  new ActionRuleUserAllowCopy(),
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRulePatientAlive(),
                  new ActionRuleChildRelationNoMatch(
                      List.of(RelationType.REPLACE, RelationType.COMPLEMENT),
                      List.of(Status.DRAFT, Status.REVOKED)
                  ),
                  new ActionRuleUserAgreement(),
                  new ActionRuleUserHasAccessScope(
                      List.of(AccessScope.WITHIN_CARE_UNIT)
                  ),
                  new ActionRuleActiveCertificateModel()
              )
          )
          .build();
      case UPDATE_DRAFT_FROM_CERTIFICATE -> CertificateActionUpdateDraftFromCertificate.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT),
                  new ActionRuleProtectedPerson(
                      actionSpecification.allowedRolesForProtectedPersons()
                  ),
                  new ActionRuleStatus(List.of(Status.DRAFT)),
                  new ActionRuleRole(
                      actionSpecification.allowedRoles()
                  ),
                  new ActionRuleUserAgreement(),
                  new ActionRuleUserHasAccessScope(
                      List.of(AccessScope.WITHIN_CARE_PROVIDER, AccessScope.ALL_CARE_PROVIDERS)
                  ),
                  new ActionRuleActiveCertificateModel()
              )
          )
          .build();
      case INACTIVE_CERTIFICATE_MODEL -> CertificateActionInactiveCertificateModel.builder()
          .certificateActionSpecification(actionSpecification)
          .actionRules(
              List.of(
                  new ActionRuleInactiveCertificateModel()
              )
          )
          .build();
    };
  }
}