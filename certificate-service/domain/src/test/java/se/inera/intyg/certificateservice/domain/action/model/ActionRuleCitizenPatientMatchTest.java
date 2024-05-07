package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;

@ExtendWith(MockitoExtension.class)
class ActionRuleCitizenPatientMatchTest {

  private static final String TOLVAN_PERSON_ID = "191212121212";
  private static final String LILLTOLVAN_PERSON_ID = "201212121212";

  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder()
      .citizen(PersonId.builder()
          .id(TOLVAN_PERSON_ID)
          .build())
      .build();

  @InjectMocks
  private ActionRuleCitizenPatientMatch actionRuleCitizenPatientMatch;

  @Test
  void shouldReturnFalseIfCertificateIsEmpty() {
    final var actionEvaluation = ActionEvaluation.builder().build();
    Optional<Certificate> certificate = Optional.empty();

    assertFalse(
        actionRuleCitizenPatientMatch.evaluate(certificate, actionEvaluation),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shouldReturnFalseIfPatientIdDoesNotMatchCitizen() {
    final var certificate = getCertificate(LILLTOLVAN_PERSON_ID);

    assertFalse(
        actionRuleCitizenPatientMatch.evaluate(Optional.of(certificate), ACTION_EVALUATION),
        () -> "Expected false when passing %s and %s".formatted(ACTION_EVALUATION, certificate)
    );
  }

  @Test
  void shouldReturnTrueIfPatientIdMatchesCitizen() {
    final var certificate = getCertificate(TOLVAN_PERSON_ID);

    assertTrue(
        actionRuleCitizenPatientMatch.evaluate(Optional.of(certificate), ACTION_EVALUATION),
        () -> "Expected true when passing %s and %s".formatted(ACTION_EVALUATION, certificate)
    );
  }

  private static Certificate getCertificate(String personId) {
    return Certificate.builder()
        .certificateMetaData(CertificateMetaData.builder()
            .patient(Patient.builder()
                .id(PersonId.builder()
                    .id(personId)
                    .build())
                .build())
            .build())
        .build();
  }
}