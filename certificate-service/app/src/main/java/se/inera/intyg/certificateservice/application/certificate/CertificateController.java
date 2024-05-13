package se.inera.intyg.certificateservice.application.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateResponse;
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
import se.inera.intyg.certificateservice.application.certificate.dto.ValidateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.config.ValidateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.service.CertificateExistsService;
import se.inera.intyg.certificateservice.application.certificate.service.CreateCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.DeleteCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificatePdfService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateXmlService;
import se.inera.intyg.certificateservice.application.certificate.service.RenewCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.ReplaceCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.RevokeCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.SendCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.SignCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.SignCertificateWithoutSignatureService;
import se.inera.intyg.certificateservice.application.certificate.service.UpdateCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.ValidateCertificateService;

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

  @PostMapping
  CreateCertificateResponse createCertificate(
      @RequestBody CreateCertificateRequest createCertificateRequest) {
    return createCertificateService.create(createCertificateRequest);
  }

  @GetMapping("/{certificateId}/exists")
  CertificateExistsResponse findExistingCertificate(
      @PathVariable("certificateId") String certificateId) {
    return certificateExistsService.exist(certificateId);
  }

  @PostMapping("/{certificateId}")
  GetCertificateResponse getCertificate(
      @RequestBody GetCertificateRequest getCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return getCertificateService.get(getCertificateRequest, certificateId);
  }

  @PutMapping("/{certificateId}")
  UpdateCertificateResponse updateCertificate(
      @RequestBody UpdateCertificateRequest updateCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return updateCertificateService.update(updateCertificateRequest, certificateId);
  }

  @DeleteMapping("/{certificateId}/{version}")
  DeleteCertificateResponse deleteCertificate(
      @RequestBody DeleteCertificateRequest deleteCertificateRequest,
      @PathVariable("certificateId") String certificateId, @PathVariable("version") Long version) {
    return deleteCertificateService.delete(deleteCertificateRequest, certificateId, version);
  }

  @PostMapping("/{certificateId}/validate")
  ValidateCertificateResponse validateCertificate(
      @RequestBody ValidateCertificateRequest validateCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return validateCertificateService.validate(validateCertificateRequest, certificateId);
  }

  @PostMapping("/{certificateId}/xml")
  GetCertificateXmlResponse getCertificateXml(
      @RequestBody GetCertificateXmlRequest getCertificateXmlRequest,
      @PathVariable("certificateId") String certificateId) {
    return getCertificateXmlService.get(getCertificateXmlRequest, certificateId);
  }

  @PostMapping("/{certificateId}/sign/{version}")
  SignCertificateResponse signCertificate(
      @RequestBody SignCertificateRequest signCertificateRequest,
      @PathVariable("certificateId") String certificateId, @PathVariable("version") Long version) {
    return signCertificateService.sign(signCertificateRequest, certificateId, version);
  }

  @PostMapping("/{certificateId}/signwithoutsignature/{version}")
  SignCertificateResponse signCertificateWithoutSignature(
      @RequestBody SignCertificateWithoutSignatureRequest signCertificateRequest,
      @PathVariable("certificateId") String certificateId, @PathVariable("version") Long version) {
    return signCertificateWithoutSignatureService.sign(signCertificateRequest, certificateId,
        version);
  }

  @PostMapping("/{certificateId}/send")
  SendCertificateResponse sendCertificate(
      @RequestBody SendCertificateRequest sendCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return sendCertificateService.send(sendCertificateRequest, certificateId);
  }

  @PostMapping("/{certificateId}/pdf")
  GetCertificatePdfResponse getCertificatePdf(
      @RequestBody GetCertificatePdfRequest getCertificatePdfRequest,
      @PathVariable("certificateId") String certificateId) {
    return getCertificatePdfService.get(getCertificatePdfRequest, certificateId);
  }

  @PostMapping("/{certificateId}/revoke")
  RevokeCertificateResponse revokeCertificate(
      @RequestBody RevokeCertificateRequest revokeCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return revokeCertificateService.revoke(revokeCertificateRequest, certificateId);
  }

  @PostMapping("/{certificateId}/replace")
  ReplaceCertificateResponse replaceCertificate(
      @RequestBody ReplaceCertificateRequest replaceCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return replaceCertificateService.replace(replaceCertificateRequest, certificateId);
  }

  @PostMapping("/{certificateId}/renew")
  RenewCertificateResponse renewCertificate(
      @RequestBody RenewCertificateRequest renewCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return renewCertificateService.renew(renewCertificateRequest, certificateId);
  }
}
