package se.inera.intyg.certificateprintservice.print.api;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Category {

  String id;
  String name;
  List<Question> questions;
}