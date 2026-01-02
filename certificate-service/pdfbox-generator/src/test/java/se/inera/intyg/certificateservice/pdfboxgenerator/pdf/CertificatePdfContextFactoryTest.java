package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7472CertificateBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.TemplatePdfSpecification;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory.CertificatePdfContextFactory;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory.TextFieldAppearanceFactory;

class CertificatePdfContextFactoryTest {

  private CertificatePdfContextFactory factory;
  private Certificate certificate;
  private PdfGeneratorOptions options;
  private TemplatePdfSpecification templatePdfSpecification;

  @BeforeEach
  void setUp() {
    factory = new CertificatePdfContextFactory(new TextFieldAppearanceFactory());

    certificate = fk7472CertificateBuilder().build();
    options = PdfGeneratorOptions.builder()
        .additionalInfoText("Additional info")
        .citizenFormat(false)
        .build();
    templatePdfSpecification = (TemplatePdfSpecification) certificate.certificateModel()
        .pdfSpecification();
  }

  @Nested
  class CreateContext {

    @Test
    void shouldCreateContextWithDocument() {
      try (final var context = factory.create(certificate, options, templatePdfSpecification)) {
        assertNotNull(context.getDocument());
        assertTrue(context.getDocument().getNumberOfPages() > 0);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    void shouldCreateContextWithCertificate() {
      try (final var context = factory.create(certificate, options, templatePdfSpecification)) {
        assertEquals(certificate, context.getCertificate());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    void shouldCreateContextWithTemplatePdfSpecification() {
      try (final var context = factory.create(certificate, options, templatePdfSpecification)) {
        assertEquals(templatePdfSpecification, context.getTemplatePdfSpecification());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    void shouldCreateContextWithCitizenFormat() {
      final var citizenOptions = PdfGeneratorOptions.builder()
          .additionalInfoText("info")
          .citizenFormat(true)
          .build();

      try (final var context = factory.create(certificate, citizenOptions,
          templatePdfSpecification)) {
        assertTrue(context.isCitizenFormat());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    void shouldCreateContextWithAdditionalInfoText() {
      try (final var context = factory.create(certificate, options, templatePdfSpecification)) {
        assertEquals("Additional info", context.getAdditionalInfoText());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    void shouldCreateContextWithEmptyPdfFieldsList() {
      try (final var context = factory.create(certificate, options, templatePdfSpecification)) {
        assertNotNull(context.getPdfFields());
        assertTrue(context.getPdfFields().isEmpty());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Nested
  class TemplateSelection {

    @Test
    void shouldUseAddressTemplateForUnsentCertificate() {
      final var unsentCertificate = fk7472CertificateBuilder()
          .sent(null)
          .build();

      try (final var context = factory.create(unsentCertificate, options,
          templatePdfSpecification)) {
        assertNotNull(context.getDocument());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    void shouldUseNoAddressTemplateForSentCertificate() {
      final var sentCertificate = fk7472CertificateBuilder()
          .sent(Sent.builder()
              .sentAt(java.time.LocalDateTime.now())
              .build())
          .build();

      try (final var context = factory.create(sentCertificate, options, templatePdfSpecification)) {
        assertNotNull(context.getDocument());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    void shouldUseNoAddressTemplateForCitizenFormat() {
      final var citizenOptions = PdfGeneratorOptions.builder()
          .additionalInfoText("info")
          .citizenFormat(true)
          .build();

      try (final var context = factory.create(certificate, citizenOptions,
          templatePdfSpecification)) {
        assertNotNull(context.getDocument());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    void shouldThrowExceptionWhenTemplateNotFound() {
      final var invalidSpec = TemplatePdfSpecification.builder()
          .pdfTemplatePath("non-existent-template.pdf")
          .pdfNoAddressTemplatePath("non-existent-template-no-address.pdf")
          .signature(templatePdfSpecification.signature())
          .patientIdFieldIds(templatePdfSpecification.patientIdFieldIds())
          .pdfMcid(templatePdfSpecification.pdfMcid())
          .build();

      final var exception = assertThrows(IllegalStateException.class,
          () -> factory.create(certificate, options, invalidSpec));

      assertTrue(exception.getMessage().contains("Pdf template not found at path"));
    }
  }
}


