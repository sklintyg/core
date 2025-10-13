package se.inera.intyg.certificateservice.application.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.AvailableFunctionDTO.AvailableFunctionDTOBuilder;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunction;

@JsonDeserialize(builder = AvailableFunctionDTOBuilder.class)
@Value
@Builder
public class AvailableFunctionDTO {

  AvailableFunctionType type;
  String name;
  String description;
  String title;
  String body;
  List<InformationDTO> information;
  boolean enabled;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AvailableFunctionDTOBuilder {

  }

  public static AvailableFunctionDTO toDTO(CitizenAvailableFunction function) {
    return AvailableFunctionDTO.builder()
        .title(function.title())
        .name(function.name())
        .type(AvailableFunctionType.valueOf(function.type().name()))
        .body(function.body())
        .enabled(function.enabled())
        .description(function.description())
        .information(
            function.information() != null ?
                function.information().stream()
                    .map(info -> InformationDTO.builder()
                        .id(info.id() != null ? info.id().id() : "")
                        .type(InformationType.valueOf(info.type().name()))
                        .text(info.text())
                        .build()
                    ).toList() : List.of()
        )
        .build();
  }
}
