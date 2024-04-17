package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Map;

public record QuestionTexts(Map<QuestionTextType, String> value) {

}
