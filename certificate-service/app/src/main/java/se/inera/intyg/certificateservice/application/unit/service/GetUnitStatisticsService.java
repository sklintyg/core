package se.inera.intyg.certificateservice.application.unit.service;

import java.util.Map;
import java.util.stream.Collectors;
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
            statisticsMap.entrySet().stream()
                .collect(
                    Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> UnitStatisticsDTO.builder()
                            .draftCount(entry.getValue().certificateCount())
                            .unhandledMessageCount(entry.getValue().messageCount())
                            .build()
                    ))
        )
        .build();
  }
}
