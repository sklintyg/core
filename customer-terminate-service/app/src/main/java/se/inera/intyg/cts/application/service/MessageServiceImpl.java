package se.inera.intyg.cts.application.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.SendPassword;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

    private final TerminationRepository terminationRepository;
    private final SendPassword sendPassword;
    private final Boolean sendPasswordActive;


    public MessageServiceImpl(TerminationRepository terminationRepository, SendPassword sendPassword,
        @Value("${send.password.active}") Boolean sendPasswordActive) {
        this.terminationRepository = terminationRepository;
        this.sendPassword = sendPassword;
        this.sendPasswordActive = sendPasswordActive;
    }

    public void sendPassword() {
        for (Termination termination: terminationRepository
            .findByStatuses(List.of(TerminationStatus.RECEIPT_RECEIVED))) {

            if (sendPasswordActive) {
                try {
                    sendPassword.sendPassword(termination);
                } catch (Exception e) {
                    LOG.error("Failure sending password for termination id {}.",
                        termination.terminationId(), e);
                }

            } else {
                LOG.info("Functionality for sending password is inactive. Not sending password for "
                    + "termination id {}", termination.terminationId());
            }
        }
    }
}
