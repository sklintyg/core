package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATLAS_REACT_ABRAHAMSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultAIPrefillCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public abstract class CreateCertificateIT extends BaseIntegrationIT {

  protected abstract String type();

  protected abstract String typeVersion();

  protected abstract String wrongVersion();

  @Test
  @DisplayName("Om utkastet framgångsrikt skapats skall utkastet returneras")
  void shallReturnCertificateWhenActive() {
    final var response = api.createCertificate(
        defaultCreateCertificateRequest(type(), typeVersion())
    );

    assertNotNull(
        certificate(response.getBody()),
        "Should return certificate as it is active!"
    );
  }

  @Test
  @DisplayName("Om utkastet framgångsrikt skapats med AI förifyllnad skall utkastet returneras")
  void shallReturnCertificateWhenAIPrefill() {
    final var response = api.createCertificate(
        defaultCreateCertificateRequest(type(), typeVersion())
    );

    final var prefilledResponse = api.prefillCertificate(
        defaultAIPrefillCertificateRequest("Journalanteckning 1\n"
            + " \n"
            + "Patientinformation\n"
            + "Namn: Anna Svensson\n"
            + "Personnummer: 19750101-1234\n"
            + "Adress: Storgatan 12, 123 45 Stockholm\n"
            + "Telefonnummer: 070-123 45 67\n"
            + " \n"
            + "Besöksinformation\n"
            + "Datum och tid för besöket: 2015-02-12\n"
            + "Typ av besök: Nybesök\n"
            + "Vårdgivare: Dr. Emma Åman\n"
            + " \n"
            + "Anamnes\n"
            + "Anna söker vård på grund av återkommande episoder av domningar och svaghet i höger arm och ben under de senaste sex månaderna. Hon har också upplevt synförsämring på höger öga och ökad trötthet. Ingen tidigare neurologisk sjukdom.\n"
            + " \n"
            + "Status\n"
            + "Allmäntillstånd: Vaken och orienterad, något trött.\n"
            + "Neurologiskt: Nedsatt kraft i höger arm och ben, positiv Babinskis tecken på höger sida. Nedsatt känsel för lätt beröring och vibration i höger extremiteter.\n"
            + "Ögon: Nedsatt synskärpa på höger öga, ingen rodnad eller svullnad.\n"
            + "Gång: Ataktisk gång, svårigheter att hålla balansen.\n"
            + " \n"
            + "Bedömning\n"
            + "Misstänkt MS baserat på anamnes och kliniska fynd. Patienten rekommenderas att genomgå MR-undersökning av hjärnan och ryggmärgen för att bekräfta diagnosen och bedöma sjukdomens omfattning.\n"
            + " \n"
            + "Plan\n"
            + "Remiss till neurolog för vidare utredning och MR-undersökning. Blodprover för att utesluta andra orsaker till symtomen. Uppföljning på vårdcentralen om fyra veckor. Information om MS och stöd från MS-sjuksköterska.\n"
            + " \n"
            + "Journalanteckning 2\n"
            + " \n"
            + "Patientinformation\n"
            + "Namn: Anna Svensson\n"
            + "Personnummer: 19750101-1234\n"
            + "Datum: 2015-03-31\n"
            + " \n"
            + "Anamnes\n"
            + "Anna Karlsson, kommer på återbesök efter att ha genomgått MR-undersökning och neurologisk utredning på grund av återkommande episoder av domningar och svaghet i höger arm och ben, samt synförsämring på höger öga. Hon har också upplevt ökad trötthet.\n"
            + " \n"
            + "Neurologisk utredning\n"
            + "En detaljerad bedömning av patientens neurologiska status har genomförts av Dr. Maria Lindgren, specialistläkare i neurologi. Utredningen inkluderar motoriska och sensoriska funktioner. MR-undersökning har genomförts för att bedöma progressionen av MS och dess påverkan på centrala nervsystemet. Resultaten från MR-undersökningen visar flera typiska MS-lesioner i hjärnan och ryggmärgen, vilket bekräftar diagnosen och indikerar en progression av sjukdomen.\n"
            + " \n"
            + "Status\n"
            + "Allmäntillstånd: Vaken och orienterad, något trött.\n"
            + "Neurologiskt: Nedsatt kraft i höger arm och ben, positiv Babinskis tecken på höger sida. Nedsatt känsel för lätt beröring och vibration i höger extremiteter.\n"
            + "Ögon: Nedsatt synskärpa på höger öga, ingen rodnad eller svullnad.\n"
            + "Gång: Ataktisk gång, svårigheter att hålla balansen.\n"
            + " \n"
            + "Bedömning\n"
            + "MR-undersökningen visar typiska förändringar för MS, och kliniska fynd stödjer diagnosen. Patienten informeras om diagnosen MS och får information om sjukdomen, behandlingsalternativ och prognos.\n"
            + " \n"
            + "Plan\n"
            + "Insättning av sjukdomsmodifierande behandling (t.ex. interferon-beta eller glatirameracetat). Remiss till MS-sjuksköterska för stöd och rådgivning. Uppföljning hos neurolog om fyra veckor för att utvärdera behandlingsrespons och eventuella biverkningar. Råd om livsstilsförändringar, inklusive regelbunden fysisk aktivitet och stresshantering. Information om patientföreningar och stödgrupper för personer med MS.\n"
            + " \n"
            + "Journalanteckning 3\n"
            + " \n"
            + "Patientinformation\n"
            + "Namn: Anna Svensson\n"
            + "Personnummer: 19750101-1234\n"
            + "Adress: Storgatan 12, 123 45 Stockholm\n"
            + "Telefonnummer: 070-123 45 67\n"
            + " \n"
            + "Besöksinformation\n"
            + "Datum och tid för besöket: 2015-12-04\n"
            + "Typ av besök: Psykosocialt stöd\n"
            + "Vårdgivare: Dr. Erik Johansson\n"
            + " \n"
            + "Anamnes\n"
            + "Anna söker för att prata om sin ensamhet och sina känslor kring sin sjukdom. Hon säger att hon känner sig mer isolerad och har svårt att hitta aktiviteter som hon kan göra hemma. Anna bor ensam och har ingen nära anhörig som kan hjälpa till regelbundet. Hon uttrycker oro för framtiden och den fysiska nedgången som MS kan medföra.\n"
            + " \n"
            + "Status\n"
            + "Allmäntillstånd: Normal psykisk status, men känner sig orolig och ensam.\n"
            + " \n"
            + "Bedömning\n"
            + "Psykosocial stress relaterad till sjukdomen och ensamhet.\n"
            + " \n"
            + "Plan\n"
            + "Erbjuda kontakt med kurator och föreslå gruppstöd för personer med MS. Uppmuntra till fysisk aktivitet som kan göras hemma, som sittande yoga. Remiss till en psykiatrimottagning för utredning avseende depression skickas.\n"
            + " \n"
            + "Journalanteckning 4\n"
            + " \n"
            + "Patientinformation\n"
            + "Namn: Anna Svensson\n"
            + "Personnummer: 19750101-1234\n"
            + "Adress: Storgatan 12, 123 45 Stockholm\n"
            + "Telefonnummer: 070-123 45 67\n"
            + " \n"
            + "Besöksinformation\n"
            + "Datum och tid för besöket: 2019-05-12\n"
            + "Typ av besök: Nybesök\n"
            + "Vårdgivare: Dr. Karina Svensson\n"
            + " \n"
            + "Diagnos\n"
            + "Basaliom i ansiktet\n"
            + " \n"
            + "Anamnes\n"
            + "Patienten Anna Svensson diagnostiserades med basaliom i ansiktet. Tumören var lokaliserad på näsan.\n"
            + " \n"
            + "Behandling\n"
            + "Patienten genomgick kirurgisk behandling för att avlägsna basaliomet. Operationen utfördes framgångsrikt utan komplikationer. Patienten följdes upp postoperativt och visade god läkning.\n"
            + " \n"
            + "Plan\n"
            + "Fortsatt uppföljning rekommenderas för att övervaka eventuella återfall eller nya lesioner. Patienten informeras om vikten av solskydd och regelbundna hudkontroller.\n"
            + " \n"
            + "Journalanteckning 5\n"
            + " \n"
            + "Patientinformation\n"
            + "Namn: Anna Svensson\n"
            + "Personnummer: 19750101-1234\n"
            + "Adress: Storgatan 12, 123 45 Stockholm\n"
            + "Telefonnummer: 070-123 45 67\n"
            + " \n"
            + "Besöksinformation\n"
            + "Datum och tid för besöket: 2023-05-12\n"
            + "Typ av besök: Akutbesök\n"
            + "Vårdgivare: Dr. Erik Johansson\n"
            + " \n"
            + "Anamnes\n"
            + "Anna söker akut för halsont och feber som har varat i 4 dagar. Hon har haft svårt att svälja och känner sig allmänt sjuk. Eftersom Anna är immunnedsatt på grund av MS, är hon orolig för att det kan vara något allvarligare. Inga andra symtom som hosta eller snuva förekommer.\n"
            + " \n"
            + "Status\n"
            + "Allmäntillstånd: Feber (38,2°C), påverkad allmäntillstånd. Hals: Rodnad och svullnad på båda tonsillerna, inga beläggningar. Övrigt: Inga tecken på andningspåverkan.\n"
            + " \n"
            + "Bedömning\n"
            + "Troligtvis en virusinfektion, möjligen faryngit. Viktigt att följa upp på grund av immunstatusen och MS.\n"
            + " \n"
            + "Plan\n"
            + "Rekommenderad behandling med febernedsättande och vilande. Återbesök om symtomen inte förbättras inom några dagar.\n"
            + " \n"
            + "Journalanteckning 6\n"
            + " \n"
            + "Patientinformation\n"
            + "Namn: Anna Svensson\n"
            + "Personnummer: 19750101-1234\n"
            + "Adress: Storgatan 12, 123 45 Stockholm\n"
            + "Telefonnummer: 070-123 45 67\n"
            + " \n"
            + "Besöksinformation\n"
            + "Datum och tid för besöket: 2025-01-07\n"
            + "Typ av besök: Återbesök\n"
            + "Vårdgivare: Dr. Erik Johansson\n"
            + " \n"
            + "Anamnes\n"
            + "Patientens beskrivning: Anna Svensson är en 50-årig kvinna med diagnosen multipel skleros (MS) sedan 2015. Hon har successivt fått ökade funktionsnedsättningar och har nu behov av hjälpmedel och anpassningar i hemmet. Anna bor ensam och har ingen närstående som kan hjälpa henne dagligen. Anna dricker vin 2 gånger i veckan. Anna har två katter hemma, hon är allergisk mot katter och äter antihistamin. Anna upplever ökade svårigheter med balans och koordination. Hon har även smärta i nedre delen av ryggen. Men hon hoppas på att smärtan i nedre delen av ryggen ska försvinna snart.\n"
            + " \n"
            + "Tidigare sjukdomar och behandlingar\n"
            + "MS sedan 2015, regelbundna vårdbesök och behandlingar för att hantera symtom. Anna opererade bort basaliom i ansiktet under 2019. Anna har ont i halsen sedan två veckor tillbaka. Anna är blind på ena ögat.\n"
            + " \n"
            + "Familjeanamnes\n"
            + "Ingen relevant familjehistorik av MS.\n"
            + " \n"
            + "Status\n"
            + "Allmäntillstånd: Anna har svårt att gå längre sträckor och använder rullstol vid behov. Hon har även problem med finmotoriken, vilket gör det svårt för henne att utföra vardagliga sysslor som matlagning och städning. Anna är positiv och glad.\n"
            + " \n"
            + "Specifika fynd\n"
            + "Behov av elektrisk rullstol och specialanpassad säng.\n"
            + " \n"
            + "Bedömning\n"
            + "Diagnos: Multipel skleros (MS)\n"
            + "Differentialdiagnoser: Ingen\n"
            + " \n"
            + "Plan\n"
            + "Behandlingsplan: Fortsatt användning av hjälpmedel och anpassningar i hemmet. Regelbundna vårdbesök för uppföljning av MS. Försöka ta det lugnt så mycket som möjligt.\n"
            + "Ordinationer: Elektrisk rullstol, specialanpassad säng, anpassad bil för transport.\n"
            + "Uppföljning: Ska komma tillbaka om 3 månader för utvärdering av hjälpmedel och anpassningar.\n"
            + " \n"
            + "Journalanteckning 7\n"
            + " \n"
            + "Patientinformation\n"
            + "Namn: Anna Svensson\n"
            + "Personnummer: 19750101-1234\n"
            + "Adress: Storgatan 12, 123 45 Stockholm\n"
            + "Telefonnummer: 070-123 45 67\n"
            + " \n"
            + "Besöksinformation\n"
            + "Datum och tid för besöket: 2025-03-05\n"
            + "Typ av besök: Återbesök hos neurolog\n"
            + "Vårdgivare: Dr. Karin Pettersson\n"
            + " \n"
            + "Anamnes\n"
            + "Patientens beskrivning: Anna rapporterar att hon har haft flera episoder av yrsel och illamående. Hon upplever också att hennes syn har försämrats. Anna är orolig att hon kommer behöva komma tillbaka flera gånger till vårdcentralen.\n"
            + " \n"
            + "Tidigare sjukdomar och behandlingar\n"
            + "MS sedan 2015, regelbundna vårdbesök och behandlingar för att hantera symtom.\n"
            + " \n"
            + "Familjeanamnes\n"
            + "Ingen relevant familjehistorik av MS.\n"
            + " \n"
            + "Status\n"
            + "Allmäntillstånd: Anna ser trött och påverkad ut av yrseln. Anna mår helt okej annars, ganska glad.\n"
            + " \n"
            + "Specifika fynd\n"
            + "Försämrad syn, episoder av yrsel och illamående. Illamåendet är riktigt jobbigt, Anna har extra svårt att se på natten.\n"
            + " \n"
            + "Bedömning\n"
            + "Diagnos: Multipel skleros (MS) med sekundära vestibulära och visuella problem.\n"
            + "Differentialdiagnoser: Ingen\n"
            + " \n"
            + "Plan\n"
            + "Behandlingsplan: Justering av medicinering för att hantera yrsel och illamående. Remiss till ögonläkare för utvärdering av synen.\n"
            + "Ordinationer: Ny medicinering, remiss till ögonläkare.\n"
            + "Uppföljning: Återbesök om 2 månader för utvärdering av medicinering och syn. Förhoppningsvis kommer patienten att må mycket bättre när hon kommer tillbaka om 2 månader"),
        response.getBody().getCertificate().getMetadata().getId()
    );

    assertNotNull(
        prefilledResponse.getBody().getCertificate(),
        "Should return certificate as it is active!"
    );
  }

  @Test
  @DisplayName("Om patienten är avliden skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403PatientIsDeceased() {
    final var response = api.createCertificate(
        customCreateCertificateRequest(type(), typeVersion())
            .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om användaren är blockerad skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403UserIsBlocked() {
    final var response = api.createCertificate(
        customCreateCertificateRequest(type(), typeVersion())
            .user(
                ajlaDoktorDtoBuilder()
                    .blocked(Boolean.TRUE)
                    .build()
            )
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om användaren saknar avtal skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403UserMissingAgreement() {
    final var response = api.createCertificate(
        customCreateCertificateRequest(type(), typeVersion())
            .user(
                ajlaDoktorDtoBuilder()
                    .agreement(Boolean.FALSE)
                    .build()
            )
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om patient är avliden och användaren är blockerad skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403PatientIsDeceasedAndUserIsBlocked() {
    final var response = api.createCertificate(
        customCreateCertificateRequest(type(), typeVersion())
            .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
            .user(
                ajlaDoktorDtoBuilder()
                    .blocked(Boolean.TRUE)
                    .build()
            )
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @ParameterizedTest
  @DisplayName("Om utkastet är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
  @MethodSource("rolesAccessToProtectedPerson")
  void shallReturnCertificateIfPatientIsProtectedPerson(UserDTO userDTO) {
    final var response = api.createCertificate(
        customCreateCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .user(userDTO)
            .build()
    );

    assertNotNull(
        certificate(response.getBody()),
        "Should return certificate because the user is a doctor!"
    );
  }

  @Test
  @DisplayName("Vårdadministratör - Om patienten har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403PatientIsProtectedPersonAndUserDoctor() {
    final var response = api.createCertificate(
        customCreateCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om den efterfrågade versionen inte stöds skall felkod 400 (BAD_REQUEST) returneras")
  void shallReturn400IfVersionNotSupported() {
    final var response = api.createCertificate(
        defaultCreateCertificateRequest(type(), wrongVersion())
    );

    assertEquals(400, response.getStatusCode().value());
  }
}
