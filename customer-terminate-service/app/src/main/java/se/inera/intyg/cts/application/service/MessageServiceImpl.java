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
public class MessageServiceImpl implements se.inera.intyg.cts.application.service.MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

    private final TerminationRepository terminationRepository;

    private final SendPassword sendPassword;


    public MessageServiceImpl(TerminationRepository terminationRepository, SendPassword sendPassword) {
        this.terminationRepository = terminationRepository;
        this.sendPassword = sendPassword;
    }

    public void sendPassword() {
        for (Termination termination: terminationRepository.findByStatuses(List.of(TerminationStatus.RECEIPT_RECEIVED))) {
            LOG.debug("Sending SMS for termination {}.", termination.terminationId());
            try {
                sendPassword.sendPassword(termination);
            } catch (Exception e){
                LOG.error(e.getMessage());
            }
        }
    }
}
