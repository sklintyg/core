package se.inera.intyg.certificateservice.application.common;

import java.util.Collections;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.unit.dto.MessagesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.application.unit.dto.QuestionSenderTypeDTO;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.MessagesRequest;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;

@Component
public class MessagesRequestFactory {

  public MessagesRequest create() {
    return MessagesRequest.builder()
        .build();
  }

  public MessagesRequest create(MessagesQueryCriteriaDTO queryCriteria) {
    return MessagesRequest.builder()
        .sentDateFrom(queryCriteria.getSentDateFrom())
        .sentDateTo(queryCriteria.getSentDateTo())
        .personId(
            queryCriteria.getPatientId() != null
                ? PersonId.builder()
                .id(queryCriteria.getPatientId().getId())
                .type(PersonIdType.valueOf(queryCriteria.getPatientId().getType()))
                .build()
                : null
        )
        .author(
            queryCriteria.getSenderType() != null
                && queryCriteria.getSenderType() != QuestionSenderTypeDTO.SHOW_ALL
                ? new Author(queryCriteria.getSenderType().name())
                : null
        )
        .issuedOnUnitIds(queryCriteria.getIssuedOnUnitIds() != null ?
            queryCriteria.getIssuedOnUnitIds().stream()
                .map(HsaId::new)
                .toList() : Collections.emptyList()
        )
        .forwarded(
            queryCriteria.getForwarded() != null ? new Forwarded(queryCriteria.getForwarded())
                : null)
        .issuedByStaffId(queryCriteria.getIssuedByStaffId() != null ? new HsaId(
            queryCriteria.getIssuedByStaffId()) : null)
        .build();
  }
}
