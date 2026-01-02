package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.TextFieldAppearance;

@ExtendWith(MockitoExtension.class)
class TextFieldAppearanceFactoryTest {

  @Test
  void shouldCreateTextFieldAppearanceForTextField() {
    final var pdTextField = new PDTextField(mock(PDAcroForm.class));
    final var expected = new TextFieldAppearance(pdTextField);

    final var actual = new TextFieldAppearanceFactory().create(pdTextField).orElseThrow();

    assertEquals(expected, actual);

  }

  @Test
  void shouldReturnEmptyForNonTextField() {
    final var pdCheckBox = new PDCheckBox(mock(PDAcroForm.class));

    final var actual = new TextFieldAppearanceFactory().create(pdCheckBox);

    assertFalse(actual.isPresent());
  }

}