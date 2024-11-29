package se.inera.intyg.intygproxyservice.integration.pu.client;

import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.PU_PROFILE_V3;
import static se.inera.intyg.intygproxyservice.integration.pu.configuration.PuConstants.KODVERK_PERSONNUMMER;
import static se.inera.intyg.intygproxyservice.integration.pu.configuration.PuConstants.KODVERK_SAMORDNINGSNUMMER;
import static se.inera.intyg.intygproxyservice.integration.pu.configuration.PuConstants.SAMORDNING_MONTH_INDEX;
import static se.inera.intyg.intygproxyservice.integration.pu.configuration.PuConstants.SAMORDNING_MONTH_VALUE_MIN;
import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.logging.PerformanceLogging;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofile.v3.rivtabp21.GetPersonsForProfileResponderInterface;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofileresponder.v3.GetPersonsForProfileType;
import se.riv.strategicresourcemanagement.persons.person.v3.IIType;
import se.riv.strategicresourcemanagement.persons.person.v3.LookupProfileType;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile(PU_PROFILE_V3)
public class PuClientV3 {

  private final GetPersonsForProfileResponderInterface getPersonsForProfileResponderInterface;

  private final GetPersonsForProfileResponseTypeHandler getPersonsForProfileResponseTypeHandler;

  @Value("${integration.pu.logical.address}")
  private String logicalAddress;

  @PerformanceLogging(eventAction = "get-persons-for-profile", eventType = EVENT_TYPE_ACCESSED)
  public PuResponse findPerson(PuRequest puRequest) {
    final GetPersonsForProfileType parameters = getParameters(puRequest.getPersonId());

    try {
      final var getPersonsForProfileResponseType = getPersonsForProfileResponderInterface
          .getPersonsForProfile(logicalAddress, parameters);

      return getPersonsForProfileResponseTypeHandler.handle(getPersonsForProfileResponseType);
    } catch (Exception ex) {
      log.error("Unexpected error occurred when trying to call PU!", ex);
      return PuResponse.error();
    }
  }

  private static GetPersonsForProfileType getParameters(String personId) {
    final var parameters = new GetPersonsForProfileType();
    parameters.setProfile(LookupProfileType.P_2);
    parameters.getPersonId().add(
        getIIType(personId)
    );
    return parameters;
  }

  private static IIType getIIType(String patientId) {
    final var iiType = new IIType();
    iiType.setRoot(
        getRoot(patientId)
    );
    iiType.setExtension(patientId);
    return iiType;
  }

  private static String getRoot(String patientId) {
    return isSamordningsNummer(patientId) ? KODVERK_SAMORDNINGSNUMMER : KODVERK_PERSONNUMMER;
  }

  private static boolean isSamordningsNummer(String personId) {
    final var dateDigit = personId.charAt(SAMORDNING_MONTH_INDEX);
    return Character.getNumericValue(dateDigit) >= SAMORDNING_MONTH_VALUE_MIN;
  }
}
