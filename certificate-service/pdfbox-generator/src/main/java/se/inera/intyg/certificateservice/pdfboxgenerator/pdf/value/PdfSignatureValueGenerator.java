package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.SIGNATURE_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.SIGNATURE_FULL_NAME_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.SIGNATURE_HSA_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.SIGNATURE_PA_TITLE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.SIGNATURE_SPECIALITY_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.SIGNATURE_WORKPLACE_CODE_FIELD_ID;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.common.model.Speciality;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfSignatureValueGenerator {

  public List<PdfField> generate(Certificate certificate) {

    final var fields = new ArrayList<PdfField>();
    fields.add(getSignedDate(certificate));
    fields.add(getIssuerFullName(certificate));
    fields.add(getPaTitles(certificate).orElse(null));
    fields.add(getSpeciality(certificate).orElse(null));
    fields.add(getHsaId(certificate));
    fields.add(getWorkplaceCode(certificate).orElse(null));

    return fields;
  }

  private PdfField getSignedDate(Certificate certificate) {
    return PdfField.builder()
        .id(SIGNATURE_DATE_FIELD_ID)
        .value(certificate.signed().format(DateTimeFormatter.ISO_DATE))
        .build();
  }

  private PdfField getIssuerFullName(Certificate certificate) {
    return PdfField.builder()
        .id(SIGNATURE_FULL_NAME_FIELD_ID)
        .value(certificate.certificateMetaData().issuer().name().fullName())
        .build();
  }

  private Optional<PdfField> getPaTitles(Certificate certificate) {
    final var paTitles = certificate.certificateMetaData().issuer().paTitles();
    if (paTitles != null) {
      final var paTitleCodes = paTitles.stream()
          .map(PaTitle::code)
          .collect(Collectors.joining(", "));

      return Optional.of(PdfField.builder()
          .id(SIGNATURE_PA_TITLE_FIELD_ID)
          .value(paTitleCodes)
          .build());
    }

    return Optional.empty();
  }

  private Optional<PdfField> getSpeciality(Certificate certificate) {
    final var specialities = certificate.certificateMetaData().issuer().specialities();
    if (specialities != null) {
      final var mappedSpecialities = specialities.stream()
          .map(Speciality::value)
          .collect(Collectors.joining(", "));

      return Optional.of(PdfField.builder()
          .id(SIGNATURE_SPECIALITY_FIELD_ID)
          .value(mappedSpecialities)
          .build()
      );
    }

    return Optional.empty();
  }

  private PdfField getHsaId(Certificate certificate) {
    final var hsaId = certificate.certificateMetaData().issuer().hsaId().id();
    return PdfField.builder()
        .id(SIGNATURE_HSA_ID_FIELD_ID)
        .value(hsaId)
        .build();
  }

  private Optional<PdfField> getWorkplaceCode(Certificate certificate) {
    final var workplaceCode = certificate.certificateMetaData().issuingUnit().workplaceCode();
    if (workplaceCode != null) {
      return Optional.of(
          PdfField.builder()
              .id(SIGNATURE_WORKPLACE_CODE_FIELD_ID)
              .value(workplaceCode.code())
              .build()
      );
    }

    return Optional.empty();
  }
}