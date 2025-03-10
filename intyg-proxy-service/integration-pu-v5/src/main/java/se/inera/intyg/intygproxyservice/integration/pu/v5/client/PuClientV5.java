package se.inera.intyg.intygproxyservice.integration.pu.v5.client;

import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.PU_PROFILE_V5;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration.PuConstants.CODE_COORDINATION_NUMBER;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration.PuConstants.CODE_PERSONAL_ID;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration.PuConstants.COORDINATION_NUMBER_MONTH_INDEX;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration.PuConstants.COORDINATION_NUMBER_MONTH_VALUE_MIN;
import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.logging.PerformanceLogging;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofile.v5.rivtabp21.GetPersonsForProfileResponderInterface;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofileresponder.v5.GetPersonsForProfileType;
import se.riv.strategicresourcemanagement.persons.person.v5.IIType;
import se.riv.strategicresourcemanagement.persons.person.v5.LookupProfileType;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile(PU_PROFILE_V5)
public class PuClientV5 {

  private final GetPersonsForProfileResponderInterface getPersonsForProfileResponderInterface;

  private final GetPersonsForProfileResponseTypeHandlerV5 getPersonsForProfileResponseTypeHandlerV5;

  @Value("${integration.pu.logical.address}")
  private String logicalAddress;

  @PerformanceLogging(eventAction = "find-person", eventType = EVENT_TYPE_ACCESSED)
  public PuResponse findPerson(PuRequest puRequest) {
    final var parameters = getParameters(List.of(puRequest.getPersonId()));

    try {
      final var getPersonsForProfileResponseType = getPersonsForProfileResponderInterface
          .getPersonsForProfile(logicalAddress, parameters);

      return getPersonsForProfileResponseTypeHandlerV5.handle(getPersonsForProfileResponseType);
    } catch (Exception exception) {
      return error(exception);
    }
  }

  @PerformanceLogging(eventAction = "find-persons", eventType = EVENT_TYPE_ACCESSED)
  public PuPersonsResponse findPersons(PuPersonsRequest puRequest) {
    final var parameters = getParameters(puRequest.getPersonIds());

    try {
      final var getPersonsForProfileResponseType = getPersonsForProfileResponderInterface
          .getPersonsForProfile(logicalAddress, parameters);

      return getPersonsForProfileResponseTypeHandlerV5.handle(
          puRequest.getPersonIds(),
          getPersonsForProfileResponseType
      );

    } catch (Exception exception) {
      return PuPersonsResponse.builder()
          .persons(List.of(error(exception)))
          .build();
    }
  }

  private static PuResponse error(Exception exception) {
    log.error("Unexpected error occurred when trying to call PU", exception);
    return PuResponse.error();
  }

  private static GetPersonsForProfileType getParameters(List<String> personIds) {
    final var parameters = new GetPersonsForProfileType();
    parameters.setProfile(LookupProfileType.P_2);
    personIds.forEach(id -> parameters.getPersonId().add(getIIType(id)));
    parameters.setIgnoreReferredIdentity(true);

    return parameters;
  }

  private static IIType getIIType(String patientId) {
    final var iiType = new IIType();
    iiType.setRoot(getRoot(patientId));
    iiType.setExtension(patientId);
    return iiType;
  }

  private static String getRoot(String patientId) {
    return isCoordinationNumber(patientId) ? CODE_COORDINATION_NUMBER : CODE_PERSONAL_ID;
  }

  private static boolean isCoordinationNumber(String personId) {
    final var dateDigit = personId.charAt(COORDINATION_NUMBER_MONTH_INDEX);
    return Character.getNumericValue(dateDigit) >= COORDINATION_NUMBER_MONTH_VALUE_MIN;
  }
}
