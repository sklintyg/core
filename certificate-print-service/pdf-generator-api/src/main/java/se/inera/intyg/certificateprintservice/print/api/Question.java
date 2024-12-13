package se.inera.intyg.certificateprintservice.print.api;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValue;

@Value
@Builder
public class Question {

  String id;
  String name;
  ElementValue value;
  List<Question> subQuestions;
}