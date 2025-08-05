package se.inera.intyg.certificateservice.application.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO.UserDTOBuilder;

@JsonDeserialize(builder = UserDTOBuilder.class)
@Value
@Builder
public class UserDTO {

    String id;
    String firstName;
    String middleName;
    String lastName;
    String fullName;
    RoleTypeDTO role;
    List<PaTitleDTO> paTitles;
    List<String> specialities;
    Boolean blocked;
    Boolean agreement;
    Boolean allowCopy;
    AccessScopeTypeDTO accessScope;
    List<String> healthCareProfessionalLicence;
    String responsibleHospName;
    Boolean srsActive;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserDTOBuilder {

    }
}