package se.inera.intyg.certificateservice.infrastructure.diagnosiscode.icd10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.Diagnosis;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisCode;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisDescription;
import se.inera.intyg.certificateservice.infrastructure.diagnosiscode.DiagnosisCodeProvider;

@Component
public class IcdCodeProvider implements DiagnosisCodeProvider {

  private static final String CODE_HEADING = "Kod";
  private static final int DIAGNOSIS_CODE_INDEX = 0;
  private static final int DIAGNOSIS_TITLE_INDEX = 3;
  @Value("${diagnosis.codes.icd10se.file}")
  private String diagnosisCodesFile;

  @Override
  public List<Diagnosis> get() {
    try (final var inputStream = getClass().getClassLoader()
        .getResourceAsStream(diagnosisCodesFile)) {
      return readFromInputStream(inputStream).stream()
          .map(this::toDiagnosis)
          .filter(Objects::nonNull)
          .toList();
    } catch (IOException e) {
      throw new IllegalStateException("Could not generate diagnoses", e);
    }
  }

  private List<String> readFromInputStream(InputStream inputStream)
      throws IOException {
    final var codesFromFile = new ArrayList<String>();
    try (final var br = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        codesFromFile.add(line);
      }
    }
    return codesFromFile;
  }

  private Diagnosis toDiagnosis(String line) {
    final var text = line.replace("\"", "").split("\t");
    if (isDiagnosisChapter(text) || isDiagnosisGroup(text) || isNotActive(text) || isHeading(
        text)) {
      return null;
    }
    return Diagnosis.builder()
        .code(new DiagnosisCode(text[DIAGNOSIS_CODE_INDEX].replace(".", "")))
        .description(new DiagnosisDescription(text[DIAGNOSIS_TITLE_INDEX]))
        .build();
  }

  private boolean isHeading(String[] text) {
    return text[0].equals(CODE_HEADING);
  }

  private boolean isNotActive(String[] line) {
    return line[1].isEmpty();
  }

  private boolean isDiagnosisGroup(String[] line) {
    return line[0].contains("-");
  }

  private boolean isDiagnosisChapter(String[] line) {
    return Character.isDigit(line[0].charAt(0));
  }
}
