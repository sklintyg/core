package se.inera.intyg.certificateservice.pdfboxgenerator.value;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID;

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfUnitValueGenerator {

  public List<PdfField> generate(Certificate certificate) {
    final var elementData = certificate.getElementDataById(UNIT_CONTACT_INFORMATION);

    final var contactInfo = elementData
        .map(data -> buildAddress(certificate, data.value()))
        .orElse("");

    return List.of(
        PdfField.builder()
            .id(SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID)
            .value(contactInfo)
            .build()
    );
  }

  private static String buildAddress(Certificate certificate,
      ElementValue elementValue) {
    if (!(elementValue instanceof ElementValueUnitContactInformation unitValue)) {
      throw new IllegalStateException(
          String.format("Wrong value type: '%s'", elementValue.getClass())
      );
    }

    return String.join("\n",
        certificate.certificateMetaData().issuingUnit().name().name(),
        unitValue.address(),
        String.join(" ", unitValue.zipCode(), unitValue.city()),
        String.join(" ", "Telefon:", unitValue.phoneNumber()));
  }
}
