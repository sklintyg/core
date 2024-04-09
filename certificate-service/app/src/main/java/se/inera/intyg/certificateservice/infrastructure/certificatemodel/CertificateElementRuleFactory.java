/*
 * Copyright (C) 2024 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.inera.intyg.certificateservice.infrastructure.certificatemodel;

import java.util.Arrays;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

public class CertificateElementRuleFactory {

  private CertificateElementRuleFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementRule mandatory(ElementId id, FieldId fieldId) {
    return ElementRuleExpression.builder()
        .id(id)
        .type(ElementRuleType.MANDATORY)
        .expression(new RuleExpression(singleExpression(fieldId.value())))
        .build();
  }

  public static ElementRule mandatory(ElementId id, List<FieldId> fieldIds) {
    return ElementRuleExpression.builder()
        .id(id)
        .type(ElementRuleType.MANDATORY)
        .expression(
            new RuleExpression(
                multipleOrExpression(
                    fieldIds.stream()
                        .map(field -> singleExpression(field.value()))
                        .toArray(String[]::new)
                )
            )
        )
        .build();
  }

  private static String singleExpression(String id) {
    return "$" + id;
  }

  private static String multipleOrExpression(String... expression) {
    return Arrays.stream(expression).reduce("", (s, s2) -> {
      if (!s.isEmpty()) {
        s += " || ";
      }
      s += s2;
      return s;
    });
  }
}
