package se.inera.intyg.certificateservice.application.unit.service;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsDTO;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsRequest;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsResponse;
import se.inera.intyg.certificateservice.application.unit.service.validator.UnitStatisticsRequestValidator;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitStatisticsDomainService;

@Service
@RequiredArgsConstructor
public class GetUnitStatisticsService {

  private final UnitStatisticsRequestValidator unitStatisticsRequestValidator;
  private final GetUnitStatisticsDomainService getUnitStatisticsDomainService;

  public UnitStatisticsResponse get(UnitStatisticsRequest request) {
    unitStatisticsRequestValidator.validate(request);

    final var statisticsMap = getUnitStatisticsDomainService.get(
        request.getUser().getRole().toRole(),
        request.getIssuedByUnitIds().stream()
            .map(HsaId::new)
            .toList()
    );

    return UnitStatisticsResponse.builder()
        .unitStatistics(
            statisticsMap.entrySet().stream()
                .collect(
                    Collectors.toMap(
                        entry -> entry.getKey().id(),
                        entry -> UnitStatisticsDTO.builder()
                            .draftCount(entry.getValue().certificateCount())
                            .unhandledMessageCount(entry.getValue().messageCount())
                            .build()
                    ))
        )
        .build();
  }
}
