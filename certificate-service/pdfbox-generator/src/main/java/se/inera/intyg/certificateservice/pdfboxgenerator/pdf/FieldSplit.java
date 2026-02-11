package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.util.List;

public record FieldSplit(PdfField first, List<List<PdfField>> overflows) {


}
