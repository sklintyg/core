package se.inera.intyg.certificateservice.application.unit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsDTO;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsRequest;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsResponse;
import se.inera.intyg.certificateservice.application.unit.service.validator.UnitStatisticsRequestValidator;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitStatisticsDomainService;

@Service
@RequiredArgsConstructor
public class GetUnitStatisticsService {

  private final UnitStatisticsRequestValidator unitStatisticsRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final GetUnitStatisticsDomainService getUnitStatisticsDomainService;

  public UnitStatisticsResponse get(UnitStatisticsRequest request) {
    unitStatisticsRequestValidator.validate(request);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var statisticsMap = getUnitStatisticsDomainService.get(
        actionEvaluation,
        request.getUnitIds()
    );

    return UnitStatisticsResponse.builder()
        .unitStatistics(
            statisticsMap.keySet().stream()
                .map(key -> {
                      final var unitStatistics = statisticsMap.get(key);
                      return UnitStatisticsDTO.builder()
                          .unitId(key)
                          .draftCount(unitStatistics.certificateCount())
                          .unhandledMessageCount(unitStatistics.messageCount())
                          .build();
                    }
                )
                .toList()
        )
        .build();
  }
}
