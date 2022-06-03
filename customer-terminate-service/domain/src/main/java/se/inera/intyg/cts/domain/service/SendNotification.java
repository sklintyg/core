package se.inera.intyg.cts.domain.service;

import se.inera.intyg.cts.domain.model.Termination;

public interface SendNotification {

    void sendNotification(Termination termination);

    void sendReminder(Termination termination);

}
