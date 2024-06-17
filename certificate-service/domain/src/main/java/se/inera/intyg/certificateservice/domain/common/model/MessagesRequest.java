package se.inera.intyg.certificateservice.domain.common.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;

@Value
@Builder
public class MessagesRequest {

  List<HsaId> issuedOnUnitIds;
  Forwarded forwarded;
  Author author;
  LocalDateTime sentDateFrom;
  LocalDateTime sentDateTo;
  HsaId issuedByStaffId;
  PersonId personId;
}
