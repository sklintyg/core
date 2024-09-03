package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

public class TextfieldAppearance {
    public String fontName;
    public double fontSize;
    public String fontCommand;
    public String colorValue;
    public String colorCommand;

    public TextfieldAppearance(PDTextField field) {
        final var appearance = field.getDefaultAppearance();
        String[] parts = appearance.split("\\s+");

        this.fontName = parts.length > 0 ? parts[0] : null;
        this.fontSize = parts.length > 1 ? Double.parseDouble(parts[1]) : 0;
        this.fontCommand = parts.length > 2 ? parts[2] : null;
        this.colorValue = parts.length > 3 ? parts[3] : null;
        this.colorCommand = parts.length > 4 ? parts[4] : null;
    }

    @Override
    public String toString() {
        return "FontName: " + fontName + "\n" +
                "FontSize: " + fontSize + "\n" +
                "FontCommand: " + fontCommand + "\n" +
                "ColorValue: " + colorValue + "\n" +
                "ColorCommand: " + colorCommand;
    }
}
