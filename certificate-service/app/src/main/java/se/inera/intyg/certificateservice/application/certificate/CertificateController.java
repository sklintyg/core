package se.inera.intyg.certificateservice.application.certificate;

import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_CHANGE;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_CREATION;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_DELETION;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificate.dto.AnswerComplementRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.AnswerComplementResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateReadyForSignRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateReadyForSignResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ComplementCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ComplementCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateDraftFromCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateDraftFromCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ForwardCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ForwardCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateCandidateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateCandidateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateEventsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateEventsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewExternalCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ReplaceCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ReplaceCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SendCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SendCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateWithoutSignatureRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateWithCertificateCandidateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateWithCertificateCandidateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ValidateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.config.ValidateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.service.AnswerComplementService;
import se.inera.intyg.certificateservice.application.certificate.service.CertificateExistsService;
import se.inera.intyg.certificateservice.application.certificate.service.ComplementCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.CreateCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.CreateDraftFromCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.DeleteCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.ForwardCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateCandidateService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateEventsService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificatePdfService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateXmlService;
import se.inera.intyg.certificateservice.application.certificate.service.RenewCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.RenewExternalCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.ReplaceCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.RevokeCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.SendCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.SetCertificateReadyForSignService;
import se.inera.intyg.certificateservice.application.certificate.service.SignCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.SignCertificateWithoutSignatureService;
import se.inera.intyg.certificateservice.application.certificate.service.UpdateCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.UpdateWithCertificateCandidateService;
import se.inera.intyg.certificateservice.application.certificate.service.ValidateCertificateService;
import se.inera.intyg.certificateservice.infrastructure.errorutil.OptimisticLockErrorHandler;
import se.inera.intyg.certificateservice.logging.PerformanceLogging;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

  private final GetCertificateService getCertificateService;
  private final CreateCertificateService createCertificateService;
  private final CertificateExistsService certificateExistsService;
  private final UpdateCertificateService updateCertificateService;
  private final DeleteCertificateService deleteCertificateService;
  private final ValidateCertificateService validateCertificateService;
  private final GetCertificateXmlService getCertificateXmlService;
  private final SendCertificateService sendCertificateService;
  private final SignCertificateService signCertificateService;
  private final SignCertificateWithoutSignatureService signCertificateWithoutSignatureService;
  private final GetCertificatePdfService getCertificatePdfService;
  private final RevokeCertificateService revokeCertificateService;
  private final ReplaceCertificateService replaceCertificateService;
  private final RenewCertificateService renewCertificateService;
  private final RenewExternalCertificateService renewExternalCertificateService;
  private final ComplementCertificateService complementCertificateService;
  private final AnswerComplementService answerComplementService;
  private final ForwardCertificateService forwardCertificateService;
  private final GetCertificateEventsService getCertificateEventsService;
  private final SetCertificateReadyForSignService setCertificateReadyForSignService;
  private final CreateDraftFromCertificateService createDraftFromCertificateService;
  private final GetCertificateCandidateService getCertificateCandidateService;
  private final UpdateWithCertificateCandidateService updateWithCertificateCandidateService;

  @PostMapping
  @PerformanceLogging(eventAction = "create-certificate", eventType = EVENT_TYPE_CREATION)
  @OptimisticLockErrorHandler
  CreateCertificateResponse createCertificate(
      @RequestBody CreateCertificateRequest createCertificateRequest) {
    return createCertificateService.create(createCertificateRequest);
  }

  @GetMapping("/{certificateId}/exists")
  @PerformanceLogging(eventAction = "find-existing-certificate", eventType = EVENT_TYPE_ACCESSED)
  CertificateExistsResponse findExistingCertificate(
      @PathVariable("certificateId") String certificateId) {
    return certificateExistsService.exist(certificateId);
  }

  @PostMapping("/{certificateId}")
  @PerformanceLogging(eventAction = "retrieve-certificate", eventType = EVENT_TYPE_ACCESSED)
  GetCertificateResponse getCertificate(
      @RequestBody GetCertificateRequest getCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return getCertificateService.get(getCertificateRequest, certificateId);
  }

  @PutMapping("/{certificateId}")
  @PerformanceLogging(eventAction = "update-certificate", eventType = EVENT_TYPE_CHANGE)
  @OptimisticLockErrorHandler
  UpdateCertificateResponse updateCertificate(
      @RequestBody UpdateCertificateRequest updateCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return updateCertificateService.update(updateCertificateRequest, certificateId);
  }

  @DeleteMapping("/{certificateId}/{version}")
  @PerformanceLogging(eventAction = "delete-certificate", eventType = EVENT_TYPE_DELETION)
  @OptimisticLockErrorHandler
  DeleteCertificateResponse deleteCertificate(
      @RequestBody DeleteCertificateRequest deleteCertificateRequest,
      @PathVariable("certificateId") String certificateId, @PathVariable("version") Long version) {
    return deleteCertificateService.delete(deleteCertificateRequest, certificateId, version);
  }

  @PostMapping("/{certificateId}/validate")
  @PerformanceLogging(eventAction = "validate-certificate", eventType = EVENT_TYPE_ACCESSED)
  ValidateCertificateResponse validateCertificate(
      @RequestBody ValidateCertificateRequest validateCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return validateCertificateService.validate(validateCertificateRequest, certificateId);
  }

  @PostMapping("/{certificateId}/xml")
  @PerformanceLogging(eventAction = "retrieve-certificate-xml", eventType = EVENT_TYPE_ACCESSED)
  GetCertificateXmlResponse getCertificateXml(
      @RequestBody GetCertificateXmlRequest getCertificateXmlRequest,
      @PathVariable("certificateId") String certificateId) {
    return getCertificateXmlService.get(getCertificateXmlRequest, certificateId);
  }

  @PostMapping("/{certificateId}/sign/{version}")
  @PerformanceLogging(eventAction = "sign-certificate", eventType = EVENT_TYPE_CHANGE)
  @OptimisticLockErrorHandler
  SignCertificateResponse signCertificate(
      @RequestBody SignCertificateRequest signCertificateRequest,
      @PathVariable("certificateId") String certificateId, @PathVariable("version") Long version) {
    return signCertificateService.sign(signCertificateRequest, certificateId, version);
  }

  @PostMapping("/{certificateId}/signwithoutsignature/{version}")
  @PerformanceLogging(eventAction = "sign-certificate-without-signature", eventType = EVENT_TYPE_CHANGE)
  @OptimisticLockErrorHandler
  SignCertificateResponse signCertificateWithoutSignature(
      @RequestBody SignCertificateWithoutSignatureRequest signCertificateRequest,
      @PathVariable("certificateId") String certificateId, @PathVariable("version") Long version) {
    return signCertificateWithoutSignatureService.sign(signCertificateRequest, certificateId,
        version);
  }

  @PostMapping("/{certificateId}/send")
  @PerformanceLogging(eventAction = "send-certificate", eventType = EVENT_TYPE_CHANGE)
  @OptimisticLockErrorHandler
  SendCertificateResponse sendCertificate(
      @RequestBody SendCertificateRequest sendCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return sendCertificateService.send(sendCertificateRequest, certificateId);
  }

  @PostMapping("/{certificateId}/pdf")
  @PerformanceLogging(eventAction = "retrieve-certificate-pdf", eventType = EVENT_TYPE_ACCESSED)
  GetCertificatePdfResponse getCertificatePdf(
      @RequestBody GetCertificatePdfRequest getCertificatePdfRequest,
      @PathVariable("certificateId") String certificateId) {
    return getCertificatePdfService.get(getCertificatePdfRequest, certificateId);
  }

  @PostMapping("/{certificateId}/revoke")
  @PerformanceLogging(eventAction = "revoke-certificate", eventType = EVENT_TYPE_CHANGE)
  @OptimisticLockErrorHandler
  RevokeCertificateResponse revokeCertificate(
      @RequestBody RevokeCertificateRequest revokeCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return revokeCertificateService.revoke(revokeCertificateRequest, certificateId);
  }

  @PostMapping("/{certificateId}/replace")
  @PerformanceLogging(eventAction = "replace-certificate", eventType = EVENT_TYPE_CREATION)
  @OptimisticLockErrorHandler
  ReplaceCertificateResponse replaceCertificate(
      @RequestBody ReplaceCertificateRequest replaceCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return replaceCertificateService.replace(replaceCertificateRequest, certificateId);
  }

  @PostMapping("/{certificateId}/renew")
  @PerformanceLogging(eventAction = "renew-certificate", eventType = EVENT_TYPE_CREATION)
  @OptimisticLockErrorHandler
  RenewCertificateResponse renewCertificate(
      @RequestBody RenewCertificateRequest renewCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return renewCertificateService.renew(renewCertificateRequest, certificateId);
  }

  @PostMapping("/{certificateId}/renew/external")
  @PerformanceLogging(eventAction = "renew-external-certificate", eventType = EVENT_TYPE_CREATION)
  @OptimisticLockErrorHandler
  RenewCertificateResponse renewExternalCertificate(
      @RequestBody RenewExternalCertificateRequest renewExternalCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return renewExternalCertificateService.renew(renewExternalCertificateRequest, certificateId);
  }

  @PostMapping("/{certificateId}/complement")
  @PerformanceLogging(eventAction = "complement-certificate", eventType = EVENT_TYPE_CREATION)
  @OptimisticLockErrorHandler
  ComplementCertificateResponse complementCertificate(
      @RequestBody ComplementCertificateRequest request,
      @PathVariable("certificateId") String certificateId) {
    return complementCertificateService.complement(request, certificateId);
  }

  @PostMapping("/{certificateId}/answerComplement")
  @PerformanceLogging(eventAction = "answer-complement", eventType = EVENT_TYPE_CHANGE)
  AnswerComplementResponse answerComplement(
      @RequestBody AnswerComplementRequest answerComplementRequest,
      @PathVariable("certificateId") String certificateId) {
    return answerComplementService.answer(answerComplementRequest, certificateId);
  }

  @PostMapping("/{certificateId}/forward")
  @PerformanceLogging(eventAction = "forward-certificate", eventType = EVENT_TYPE_CHANGE)
  @OptimisticLockErrorHandler
  ForwardCertificateResponse forwardCertificate(
      @RequestBody ForwardCertificateRequest forwardCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return forwardCertificateService.forward(forwardCertificateRequest, certificateId);
  }

  @PostMapping("/{certificateId}/events")
  @PerformanceLogging(eventAction = "retrieve-certificate-events", eventType = EVENT_TYPE_ACCESSED)
  GetCertificateEventsResponse getCertificateEvents(
      @RequestBody GetCertificateEventsRequest request,
      @PathVariable("certificateId") String certificateId) {
    return getCertificateEventsService.get(request, certificateId);
  }

  @PostMapping("/{certificateId}/readyForSign")
  @PerformanceLogging(eventAction = "ready-for-sign-certificate", eventType = EVENT_TYPE_CHANGE)
  @OptimisticLockErrorHandler
  CertificateReadyForSignResponse setCertificateReadyForSign(
      @RequestBody CertificateReadyForSignRequest request,
      @PathVariable("certificateId") String certificateId) {
    return setCertificateReadyForSignService.set(request, certificateId);
  }

  @PostMapping("/{certificateId}/draft")
  @PerformanceLogging(eventAction = "create-draft-from-certificate", eventType = EVENT_TYPE_CREATION)
  CreateDraftFromCertificateResponse createDraftFromCertificate(
      @RequestBody CreateDraftFromCertificateRequest request,
      @PathVariable("certificateId") String certificateId) {
    return createDraftFromCertificateService.create(request, certificateId);
  }

  @PostMapping("/{certificateId}/candidate")
  @PerformanceLogging(eventAction = "get-certificate-candidate", eventType = EVENT_TYPE_ACCESSED)
  GetCertificateCandidateResponse getCertificateCandidate(
      @RequestBody GetCertificateCandidateRequest request,
      @PathVariable("certificateId") String certificateId) {
    return getCertificateCandidateService.get(request, certificateId);
  }

  @PostMapping("/{certificateId}/candidate/{candidateCertificateId}")
  @PerformanceLogging(eventAction = "update-with-certificate-candidate", eventType = EVENT_TYPE_ACCESSED)
  UpdateWithCertificateCandidateResponse updateWithCertificateCandidate(
      @RequestBody UpdateWithCertificateCandidateRequest request,
      @PathVariable("certificateId") String certificateId,
      @PathVariable("candidateCertificateId") String candidateCertificateId) {
    return updateWithCertificateCandidateService.update(request, certificateId,
        candidateCertificateId);
  }
}