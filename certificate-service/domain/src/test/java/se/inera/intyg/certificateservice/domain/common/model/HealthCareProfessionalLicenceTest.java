package se.inera.intyg.certificateservice.domain.common.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HealthCareProfessionalLicenceTest {

  @Test
  void shallReturnCodeForApotekare() {
    final var expected = new Code("AP", "1.2.752.29.23.1.6", "Apotekare");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Apotekare").code()
    );
  }

  @Test
  void shallReturnCodeForArbetsterapeut() {
    final var expected = new Code("AT", "1.2.752.29.23.1.6", "Arbetsterapeut");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Arbetsterapeut").code()
    );
  }

  @Test
  void shallReturnCodeForAudionom() {
    final var expected = new Code("AU", "1.2.752.29.23.1.6", "Audionom");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Audionom").code()
    );
  }

  @Test
  void shallReturnCodeForBarnmorska() {
    final var expected = new Code("BM", "1.2.752.29.23.1.6", "Barnmorska");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Barnmorska").code()
    );
  }

  @Test
  void shallReturnCodeForBiomedicinskAnalytiker() {
    final var expected = new Code("BA", "1.2.752.29.23.1.6", "Biomedicinsk analytiker");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Biomedicinsk analytiker").code()
    );
  }

  @Test
  void shallReturnCodeForDietist() {
    final var expected = new Code("DT", "1.2.752.29.23.1.6", "Dietist");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Dietist").code()
    );
  }

  @Test
  void shallReturnCodeForFysioterapeut() {
    final var expected = new Code("FT", "1.2.752.29.23.1.6", "Fysioterapeut");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Fysioterapeut").code()
    );
  }

  @Test
  void shallReturnCodeForHalsoOchSjukvardskurator() {
    final var expected = new Code("HK", "1.2.752.29.23.1.6", "Hälso- och sjukvårdskurator");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Hälso- och sjukvårdskurator").code()
    );
  }

  @Test
  void shallReturnCodeForKiropraktor() {
    final var expected = new Code("KP", "1.2.752.29.23.1.6", "Kiropraktor");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Kiropraktor").code()
    );
  }

  @Test
  void shallReturnCodeForLogoped() {
    final var expected = new Code("LG", "1.2.752.29.23.1.6", "Logoped");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Logoped").code()
    );
  }

  @Test
  void shallReturnCodeForLakare() {
    final var expected = new Code("LK", "1.2.752.29.23.1.6", "Läkare");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Läkare").code()
    );
  }

  @Test
  void shallReturnCodeForNaprapat() {
    final var expected = new Code("NA", "1.2.752.29.23.1.6", "Naprapat");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Naprapat").code()
    );
  }

  @Test
  void shallReturnCodeForOptiker() {
    final var expected = new Code("OP", "1.2.752.29.23.1.6", "Optiker");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Optiker").code()
    );
  }

  @Test
  void shallReturnCodeForOrtopedingenjor() {
    final var expected = new Code("OT", "1.2.752.29.23.1.6", "Ortopedingenjör");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Ortopedingenjör").code()
    );
  }

  @Test
  void shallReturnCodeForPsykolog() {
    final var expected = new Code("PS", "1.2.752.29.23.1.6", "Psykolog");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Psykolog").code()
    );
  }

  @Test
  void shallReturnCodeForPsykoterapeut() {
    final var expected = new Code("PT", "1.2.752.29.23.1.6", "Psykoterapeut");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Psykoterapeut").code()
    );
  }

  @Test
  void shallReturnCodeForReceptarie() {
    final var expected = new Code("RC", "1.2.752.29.23.1.6", "Receptarie");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Receptarie").code()
    );
  }

  @Test
  void shallReturnCodeForRontgensjukskoterska() {
    final var expected = new Code("RS", "1.2.752.29.23.1.6", "Röntgensjuksköterska");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Röntgensjuksköterska").code()
    );
  }

  @Test
  void shallReturnCodeForSjukgymnast() {
    final var expected = new Code("SG", "1.2.752.29.23.1.6", "Sjukgymnast");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Sjukgymnast").code()
    );
  }

  @Test
  void shallReturnCodeForSjukhusfysiker() {
    final var expected = new Code("SF", "1.2.752.29.23.1.6", "Sjukhusfysiker");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Sjukhusfysiker").code()
    );
  }

  @Test
  void shallReturnCodeForSjukskoterska() {
    final var expected = new Code("SJ", "1.2.752.29.23.1.6", "Sjuksköterska");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Sjuksköterska").code()
    );
  }

  @Test
  void shallReturnCodeForTandhygienist() {
    final var expected = new Code("TH", "1.2.752.29.23.1.6", "Tandhygienist");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Tandhygienist").code()
    );
  }

  @Test
  void shallReturnCodeForTandlakare() {
    final var expected = new Code("TL", "1.2.752.29.23.1.6", "Tandläkare");
    assertEquals(expected,
        new HealthCareProfessionalLicence("Tandläkare").code()
    );
  }
}