package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;
import se.inera.intyg.intygproxyservice.integration.api.employee.HCPSpecialityCode;
import se.inera.intyg.intygproxyservice.integration.api.employee.PersonInformation;
import se.inera.intyg.intygproxyservice.integration.api.employee.PersonInformation.PaTitle;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.ParsedPaTitle;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.Speciality;

@Component
public class EmployeeConverter {

  private static final boolean FEIGNED_PERSON = false;

  public Employee convert(ParsedHsaPerson parsedHsaPerson) {
    return Employee.builder()
        .personInformation(
            List.of(
                PersonInformation.builder()
                    .personHsaId(parsedHsaPerson.getHsaId())
                    .age(parsedHsaPerson.getAge())
                    .gender(parsedHsaPerson.getGender())
                    .givenName(parsedHsaPerson.getGivenName())
                    .feignedPerson(FEIGNED_PERSON)
                    .title(parsedHsaPerson.getTitle())
                    .healthCareProfessionalLicence(
                        parsedHsaPerson.getHealthCareProfessionalLicence())
                    .healthCareProfessionalLicenceSpeciality(getSpecialities(parsedHsaPerson))
                    .middleAndSurName(parsedHsaPerson.getMiddleAndSurname())
                    .protectedPerson(parsedHsaPerson.isProtectedPerson())
                    .paTitle(getPaTitles(parsedHsaPerson.getParsedPaTitle()))
                    .build()
            )
        )
        .build();
  }

  private List<PaTitle> getPaTitles(List<ParsedPaTitle> parsedPaTitle) {
    if (parsedPaTitle == null || parsedPaTitle.isEmpty()) {
      return Collections.emptyList();
    }
    return parsedPaTitle.stream()
        .map(toPaTitle())
        .toList();
  }

  private static List<HCPSpecialityCode> getSpecialities(ParsedHsaPerson parsedHsaPerson) {
    if (parsedHsaPerson.getSpecialities() == null
        || parsedHsaPerson.getSpecialities().isEmpty()) {
      return Collections.emptyList();
    }
    return parsedHsaPerson.getSpecialities().stream()
        .map(toHCPSpecialityCode())
        .toList();
  }

  private static Function<ParsedPaTitle, PaTitle> toPaTitle() {
    return title -> PaTitle.builder()
        .paTitleCode(title.getTitleCode())
        .paTitleName(title.getTitleName())
        .build();
  }


  private static Function<Speciality, HCPSpecialityCode> toHCPSpecialityCode() {
    return speciality -> HCPSpecialityCode.builder()
        .specialityCode(speciality.getSpecialityCode())
        .specialityName(speciality.getSpecialityName())
        .build();
  }
}
