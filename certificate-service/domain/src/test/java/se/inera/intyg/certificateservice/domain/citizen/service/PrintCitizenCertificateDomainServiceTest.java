package se.inera.intyg.certificateservice.domain.citizen.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.exception.CitizenCertificateForbidden;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;

@ExtendWith(MockitoExtension.class)
class PrintCitizenCertificateDomainServiceTest {

  private static final String TOLVAN_ID = "191212121212";
  private static final String LILLTOLVAN_ID = "201212121212";
  private static final String ADDITIONAL_INFO_TEXT = "additionalInfoText";
  private static final PersonId TOLVAN_PERSON_ID = PersonId.builder()
      .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
      .id(TOLVAN_ID)
      .build();
  private static final PersonId LILLTOLVAN_PERSON_ID = PersonId.builder()
      .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
      .id(LILLTOLVAN_ID)
      .build();
  private static final CertificateId CERTIFICATE_ID = new CertificateId("CERTIFICATE_ID");
  private static final Certificate CERTIFICATE = getCertificate(TOLVAN_ID, true);

  private static final Pdf PDF = new Pdf(null, "fileName");
  private static final ElementId HIDDEN = new ElementId("hiddenElementId");

  @Mock
  CertificateRepository certificateRepository;

  @Mock
  PdfGenerator pdfGenerator;

  @Mock
  PdfGeneratorProvider pdfGeneratorProvider;

  @InjectMocks
  PrintCitizenCertificateDomainService printCitizenCertificateDomainService;

  @BeforeEach
  void setup() {
    when(certificateRepository.getById(CERTIFICATE_ID))
        .thenReturn(CERTIFICATE);
  }

  @Nested
  class AvailableForCitizen {

    @BeforeEach
    void setup() {
      when(certificateRepository.getById(CERTIFICATE_ID))
          .thenReturn(CERTIFICATE);
    }

    @Test
    void shouldThrowIfPatientIdDoesNotMatchCitizen() {
      assertThrows(CitizenCertificateForbidden.class,
          () -> printCitizenCertificateDomainService.get(CERTIFICATE_ID, LILLTOLVAN_PERSON_ID,
              ADDITIONAL_INFO_TEXT, HIDDEN)
      );
    }

    @Test
    void shouldReturnPdfIfPatientIdMatchesCitizen() {
      when(pdfGeneratorProvider.provider(CERTIFICATE))
          .thenReturn(pdfGenerator);
      when(pdfGenerator.generate(CERTIFICATE, ADDITIONAL_INFO_TEXT, true,
          List.of(HIDDEN))).thenReturn(PDF);

      final var response = printCitizenCertificateDomainService.get(CERTIFICATE_ID,
          TOLVAN_PERSON_ID, ADDITIONAL_INFO_TEXT, HIDDEN);

      assertEquals(PDF, response);
    }
  }

  @Test
  void shouldThrowIfCertificateIsNotAvailableForCitizen() {
    when(certificateRepository.getById(CERTIFICATE_ID))
        .thenReturn(getCertificate(LILLTOLVAN_ID, false));

    assertThrows(CitizenCertificateForbidden.class,
        () -> printCitizenCertificateDomainService.get(CERTIFICATE_ID, LILLTOLVAN_PERSON_ID,
            ADDITIONAL_INFO_TEXT, HIDDEN)
    );
  }

  private static Certificate getCertificate(String personId, Boolean availableForCitizen) {
    return MedicalCertificate.builder()
        .certificateModel(CertificateModel.builder()
            .availableForCitizen(availableForCitizen)
            .build())
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