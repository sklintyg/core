package se.inera.intyg.certificateservice.patient.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.patient.model.Deceased;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.PersonAddress;
import se.inera.intyg.certificateservice.domain.patient.model.ProtectedPerson;
import se.inera.intyg.certificateservice.domain.patient.model.TestIndicated;
import se.inera.intyg.certificateservice.patient.dto.PersonDTO;

@Component
public class PatientConverter {

  public Patient convert(PersonDTO person) {
    return Patient.builder()
        .id(getPersonId(person.getPersonnummer()))
        .name(
            Name.builder()
                .firstName(person.getFornamn())
                .middleName(person.getMellannamn())
                .lastName(person.getEfternamn())
                .build()
        )
        .deceased(new Deceased(person.isAvliden()))
        .address(
            PersonAddress.builder()
                .city(person.getPostort())
                .street(person.getPostadress())
                .zipCode(person.getPostnummer())
                .build()
        )
        .protectedPerson(new ProtectedPerson(person.isSekretessmarkering()))
        .testIndicated(new TestIndicated(person.isTestIndicator()))
        .build();
  }

  private PersonId getPersonId(String patientId) {
    return PersonId.builder()
        .type(
            isCoordinationNumber(patientId)
                ? PersonIdType.COORDINATION_NUMBER
                : PersonIdType.PERSONAL_IDENTITY_NUMBER
        )
        .id(patientId)
        .build();
  }

  private boolean isCoordinationNumber(String patientId) {
    return Character.getNumericValue(patientId.charAt(6)) >= 6;
  }
}