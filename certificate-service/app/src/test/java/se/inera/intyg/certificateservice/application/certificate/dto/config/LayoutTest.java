package se.inera.intyg.certificateservice.application.certificate.dto.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;

class LayoutTest {

  @Test
  void shallConvertRows() {
    assertEquals(Layout.ROWS, Layout.toLayout(ElementLayout.ROWS));
  }

  @Test
  void shallConvertInline() {
    assertEquals(Layout.INLINE, Layout.toLayout(ElementLayout.INLINE));
  }

  @Test
  void shallConvertColumn() {
    assertEquals(Layout.COLUMNS, Layout.toLayout(ElementLayout.COLUMNS));
  }
}
