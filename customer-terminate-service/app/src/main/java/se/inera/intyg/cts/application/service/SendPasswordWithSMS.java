package se.inera.intyg.cts.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.SendPassword;
import se.inera.intyg.cts.infrastructure.integration.SendSMS;

@Service
public class SendPasswordWithSMS implements SendPassword {

    private static final Logger LOG = LoggerFactory.getLogger(SendPasswordWithSMS.class);

    private final SendSMS sendSMS;
    private final TerminationRepository terminationRepository;

    public SendPasswordWithSMS(SendSMS sendSMS, TerminationRepository terminationRepository) {
        this.sendSMS = sendSMS;
        this.terminationRepository = terminationRepository;
    }

    @Override
    public void sendPassword(Termination termination) {
        final var message = termination.export().password().password();
        final var phoneNumber = termination.export().organizationRepresentative()
            .phoneNumber().number();

        final var smsResponseDTO = sendSMS.sendSMS(phoneNumber, message);
        LOG.info("Password sent for terminationId '{}' with jobId '{}' and logHref '{}",
            termination.terminationId().id(), smsResponseDTO.job_id(), smsResponseDTO.log_href());

        final var updateTermination = terminationRepository
            .findByTerminationId(termination.terminationId()).orElseThrow(
                () -> new IllegalStateException(String.format("Could not set status password sent "
                + "for termination id %s.", termination.terminationId())));

        updateTermination.passwordSent();
        terminationRepository.store(updateTermination);
    }
}
