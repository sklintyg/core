package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDropdownCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationRadioCode;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfCodeValueGenerator implements PdfElementValue<ElementValueCode> {

  @Override
  public Class<ElementValueCode> getType() {
    return ElementValueCode.class;
  }

  @Override
  public List<PdfField> generate(ElementSpecification elementSpecification,
      ElementValueCode elementValueCode) {
    final var pdfConfiguration = elementSpecification.pdfConfiguration();

    return switch (pdfConfiguration) {
      case PdfConfigurationCode codeConfig -> getField(elementValueCode, codeConfig);
      case PdfConfigurationDropdownCode dropdownConfig ->
          getDropdownField(elementValueCode, dropdownConfig);
      case PdfConfigurationRadioCode radioConfig ->
          getRadioCodeField(elementValueCode, radioConfig);
      default -> throw new IllegalArgumentException(
          "Unsupported PDF configuration: " + pdfConfiguration.getClass());
    };
  }

  private List<PdfField> getDropdownField(ElementValueCode code,
      PdfConfigurationDropdownCode dropdownConfig) {
    if (codeIsInvalid(code) || !StringUtils.hasLength(code.code())) {
      return Collections.emptyList();
    }

    final var label = dropdownConfig.codes().get(code.codeId());
    if (label == null) {
      throw new IllegalArgumentException("Code " + code.codeId() + " not found");
    }

    return List.of(
        PdfField.builder()
            .id(dropdownConfig.fieldId().id())
            .value(label)
            .build()
    );
  }

  private List<PdfField> getField(ElementValueCode code, PdfConfigurationCode configuration) {
    if (codeIsInvalid(code)) {
      return Collections.emptyList();
    }

    final var codeId = configuration.codes().get(code.codeId());
    if (codeId == null) {
      throw new IllegalArgumentException("Code " + code.codeId() + " not found");
    }

    return List.of(
        PdfField.builder()
            .id(codeId.id())
            .value(CHECKED_BOX_VALUE)
            .build()
    );
  }

  private List<PdfField> getRadioCodeField(ElementValueCode code,
      PdfConfigurationRadioCode configuration) {
    if (codeIsInvalid(code)) {
      return Collections.emptyList();
    }

    final var codeId = configuration.codes().get(code.codeId());
    if (codeId == null) {
      throw new IllegalArgumentException("Code " + code.codeId() + " not found");
    }

    return List.of(
        PdfField.builder()
            .id(configuration.radioGroupFieldId().id())
            .value(codeId.id())
            .build()
    );
  }

  private static boolean codeIsInvalid(ElementValueCode code) {
    return code == null || code.codeId() == null || code.codeId().value() == null;
  }
}
