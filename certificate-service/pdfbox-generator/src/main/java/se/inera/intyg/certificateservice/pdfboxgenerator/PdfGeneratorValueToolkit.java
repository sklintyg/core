package se.inera.intyg.certificateservice.pdfboxgenerator;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.CHECKED_BOX_VALUE;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.PATIENT_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID;

import java.io.IOException;
import java.util.Optional;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

public class PdfGeneratorValueToolkit {

  public static void setValue(PDAcroForm acroForm, String fieldId, String value)
      throws IOException {
    if (value != null) {
      final var field = acroForm.getField(fieldId);
      field.setValue(value);
    }
  }

  public static void setCheckedBoxValue(PDAcroForm acroForm, String fieldId)
      throws IOException {
    final var field = acroForm.getField(fieldId);
    field.setValue(CHECKED_BOX_VALUE);
  }

  public static void setPatientInformation(PDAcroForm acroForm, Certificate certificate)
      throws IOException {
    final var patientId = certificate.certificateMetaData().patient().id().id();
    PdfGeneratorValueToolkit.setValue(acroForm, PATIENT_ID_FIELD_ID, patientId);
  }

  public static void setContactInformation(PDAcroForm acroForm, Certificate certificate)
      throws IOException {
    final var elementData = getElementData(certificate, UNIT_CONTACT_INFORMATION);

    final var contactInfo = elementData
        .map(data -> buildAddress(certificate, data.value()))
        .orElse("");

    setValue(acroForm, SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID, contactInfo);
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

  private static Optional<ElementData> getElementData(Certificate certificate, ElementId id) {
    return certificate.elementData().stream()
        .filter(data -> data.id().equals(id))
        .findFirst();
  }

}
