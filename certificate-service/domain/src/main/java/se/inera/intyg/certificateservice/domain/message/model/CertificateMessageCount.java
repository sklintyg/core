package se.inera.intyg.certificateservice.domain.message.model;

import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;

public record CertificateMessageCount(CertificateId certificateId, int complementsCount,
                                      int othersCount) {

}
