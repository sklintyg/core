package se.inera.intyg.certificateservice.domain.message.model;

import lombok.Builder;

@Builder
public record CertificateMessageCount(String certificateId, Integer complementsCount,
                                      Integer othersCount) {

}
