package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

/**
 * PDF specification for general PDF generation. Uses a general PDF service to generate PDFs
 * dynamically without a template.
 */
@Value
@Builder
public class GeneralPdfSpecification implements PdfSpecification {

  String description;
}

