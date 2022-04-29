package se.inera.intyg.cts.infrastructure.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.inera.intyg.cts.application.service.MessageServiceImpl;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.SendPassword;
import se.inera.intyg.cts.infrastructure.integration.SendSMS;
import se.inera.intyg.cts.infrastructure.integration.tellustalk.dto.SMSResponseDTO;

@Service
public class SendPasswordImpl implements SendPassword {

    private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

    private final SendSMS sendSMS;

    private final TerminationRepository terminationRepository;

    public SendPasswordImpl(SendSMS sendSMS, TerminationRepository terminationRepository) {
        this.sendSMS = sendSMS;
        this.terminationRepository = terminationRepository;
    }

    @Override
    public void sendPassword(Termination termination) {
        //SMSResponseDTO smsResponseDTO = sendSMS.sendSMS(termination.export().organizationRepresentative().phoneNumber().number(), termination.export().password().password());
        //LOG.info("Password for TerminationId: {}, job_id: {}, log_href: {}", termination.terminationId().id(), smsResponseDTO.job_id(), smsResponseDTO.log_href());
        LOG.info("SMS is inactive!");

        Termination updateTermination = terminationRepository.findByTerminationId(termination.terminationId()).get();
        updateTermination.passwordSent();
        terminationRepository.store(updateTermination);
    }
}
