package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TextfieldAppearanceTest {

    private PDTextField field;
    private TextfieldAppearance textfieldAppearance;

    @BeforeEach
    void setUp() {
        field = mock(PDTextField.class);
        textfieldAppearance = new TextfieldAppearance(field);
    }

    @Test
    void shouldAdjustMultilineFieldHeight() {
        final var widget1 = mock(PDAnnotationWidget.class);
        final var widget2 = mock(PDAnnotationWidget.class);
        final var captor1 = ArgumentCaptor.forClass(PDRectangle.class);
        final var captor2 = ArgumentCaptor.forClass(PDRectangle.class);

        when(widget1.getRectangle()).thenReturn(new PDRectangle(0,0,100,50));
        when(widget2.getRectangle()).thenReturn(new PDRectangle(0,0,100,75));

        when(field.getWidgets()).thenReturn(Arrays.asList(widget1, widget2));
        when(field.getDefaultAppearance()).thenReturn("/ArialMT 12.0");

        textfieldAppearance.adjustMultilineFieldHeight();

        verify(widget1).setRectangle(captor1.capture());
        verify(widget2).setRectangle(captor2.capture());

        assertPDRectangleEquals(new PDRectangle(0, 0, 100, 61), captor1.getValue());
        assertPDRectangleEquals(new PDRectangle(0, 0, 100, 86), captor2.getValue());
    }

    @Test
    void shouldGetFontSize() {
        when(field.getDefaultAppearance()).thenReturn("/ArialMT 12.0");
        assertEquals(12.0, textfieldAppearance.getFontSize());
    }

    private void assertPDRectangleEquals(PDRectangle expected, PDRectangle actual) {
        assertEquals(expected.getLowerLeftX(), actual.getLowerLeftX());
        assertEquals(expected.getLowerLeftY(), actual.getLowerLeftY());
        assertEquals(expected.getWidth(), actual.getWidth());
        assertEquals(expected.getHeight(), actual.getHeight());
    }
}