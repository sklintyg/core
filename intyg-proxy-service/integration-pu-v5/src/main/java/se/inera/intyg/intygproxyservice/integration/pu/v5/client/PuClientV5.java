package se.inera.intyg.intygproxyservice.integration.pu.v5.client;

import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.PU_PROFILE_V5;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration.PuConstants.KODVERK_PERSONNUMMER;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration.PuConstants.KODVERK_SAMORDNINGSNUMMER;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration.PuConstants.SAMORDNING_MONTH_INDEX;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration.PuConstants.SAMORDNING_MONTH_VALUE_MIN;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
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

  public PuResponse findPerson(PuRequest puRequest) {
    final var parameters = getParameters(puRequest.getPersonId());

    try {
      final var getPersonsForProfileResponseType = getPersonsForProfileResponderInterface
          .getPersonsForProfile(logicalAddress, parameters);

      return getPersonsForProfileResponseTypeHandlerV5.handle(getPersonsForProfileResponseType);
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