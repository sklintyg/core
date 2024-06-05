package se.inera.intyg.certificateservice.application.certificate.dto.config;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;

public enum Layout {
  ROWS, COLUMNS, INLINE;

  public static Layout toLayout(ElementLayout elementLayout) {
    return switch (elementLayout) {
      case ROWS -> ROWS;
      case COLUMNS -> COLUMNS;
      case INLINE -> INLINE;
    };
  }
}
