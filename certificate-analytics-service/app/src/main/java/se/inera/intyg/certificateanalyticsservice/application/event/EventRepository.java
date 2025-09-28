package se.inera.intyg.certificateanalyticsservice.application.event;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;

public interface EventRepository {

  EventEntity save(EventEntity event);
}

