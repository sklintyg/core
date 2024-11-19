package se.inera.intyg.certificateservice.application.message.service;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageInternalResponse;
import se.inera.intyg.certificateservice.application.message.service.converter.QuestionConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@Service
@RequiredArgsConstructor
public class GetCertificateMessageInternalService {

  private final CertificateRepository certificateRepository;
  private final QuestionConverter questionConverter;

  public GetCertificateMessageInternalResponse get(String certificateId) {
    final var exists = certificateRepository.exists(new CertificateId(certificateId));
    if (!exists) {
      return GetCertificateMessageInternalResponse.builder()
          .questions(Collections.emptyList())
          .build();
    }

    final var certificate = certificateRepository.getById(new CertificateId(certificateId));
    return GetCertificateMessageInternalResponse.builder()
        .questions(
            certificate.messages().stream()
                .map(message ->
                    questionConverter.convert(
                        message,
                        Collections.emptyList()
                    )
                )
                .toList()
        )
        .build();
  }
}
