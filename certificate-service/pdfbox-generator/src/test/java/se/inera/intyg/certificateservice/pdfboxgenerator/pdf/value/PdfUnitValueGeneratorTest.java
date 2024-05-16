package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementData.contactInfoElementDataBuilder;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@ExtendWith(MockitoExtension.class)
class PdfUnitValueGeneratorTest {

  @InjectMocks
  PdfUnitValueGenerator pdfUnitValueGenerator;

  @Test
  void shouldSetContactInformationOfUnitToField() {
    final var certificate = buildCertificate();
    final var unit = certificate.certificateMetaData().issuingUnit();
    final var elementData = certificate.getElementDataById(
        new ElementId("UNIT_CONTACT_INFORMATION")
    );
    final var unitValue = (ElementValueUnitContactInformation) elementData.get().value();
    final var expectedAddress =
        unit.name().name() + "\n" + unitValue.address() + " \n" + unitValue.zipCode() + " "
            + unitValue.city() + "\nTelefon: " + unitValue.phoneNumber();

    final var expected = List.of(
        PdfField.builder()
            .id("form1[0].#subform[0].flt_txtVardgivarensNamnAdressTelefon[0]")
            .value(expectedAddress)
            .build()
    );

    final var result = pdfUnitValueGenerator.generate(certificate);

    assertEquals(expected, result);
  }

  private Certificate buildCertificate() {
    return fk7210CertificateBuilder()
        .elementData(List.of(contactInfoElementDataBuilder().build()))
        .build();
  }

}