package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.Arrays;
import java.util.Objects;

public record Pdf(byte[] pdfData, String fileName) {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pdf pdf = (Pdf) o;
    return Arrays.equals(pdfData, pdf.pdfData) && Objects.equals(fileName,
        pdf.fileName);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(fileName);
    result = 31 * result + Arrays.hashCode(pdfData);
    return result;
  }

  @Override
  public String toString() {
    return "Pdf{"
        + "pdfData=" + Arrays.toString(pdfData)
        + ", fileName='" + fileName + '\'' + '}';
  }
}
