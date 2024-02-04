package se.inera.intyg.certificateservice.domain.testdata;

import se.inera.intyg.certificateservice.domain.patient.model.Deceased;
import se.inera.intyg.certificateservice.domain.patient.model.ProtectedPerson;
import se.inera.intyg.certificateservice.domain.patient.model.TestIndicated;

public class TestDataPatientConstants {

  public static final TestIndicated TEST_INDICATED_TRUE = new TestIndicated(true);
  public static final TestIndicated TEST_INDICATED_FALSE = new TestIndicated(false);
  public static final Deceased DECEASED_TRUE = new Deceased(true);
  public static final Deceased DECEASED_FALSE = new Deceased(false);
  public static final ProtectedPerson PROTECTED_PERSON_TRUE = new ProtectedPerson(true);
  public static final ProtectedPerson PROTECTED_PERSON_FALSE = new ProtectedPerson(false);

  public static final String ATHENA_REACT_ANDERSSON_ID = "19401130-6125";
  public static final String ATHENA_REACT_ANDERSSON_FIRST_NAME = "Athena";
  public static final String ATHENA_REACT_ANDERSSON_MIDDLE_NAME = "React";
  public static final String ATHENA_REACT_ANDERSSON_LAST_NAME = "Andersson";
  public static final String ATHENA_REACT_ANDERSSON_FULL_NAME = "%s %s %s".formatted(
      ATHENA_REACT_ANDERSSON_FIRST_NAME,
      ATHENA_REACT_ANDERSSON_MIDDLE_NAME,
      ATHENA_REACT_ANDERSSON_LAST_NAME
  );
  public static final String ATHENA_REACT_ANDERSSON_STREET = "Grekstigen 90";
  public static final String ATHENA_REACT_ANDERSSON_CITY = "Karlstad";
  public static final String ATHENA_REACT_ANDERSSON_ZIP_CODE = "65340";
  public static final TestIndicated ATHENA_REACT_ANDERSSON_TEST_INDICATED = TEST_INDICATED_FALSE;
  public static final Deceased ATHENA_REACT_ANDERSSON_DECEASED = DECEASED_FALSE;
  public static final ProtectedPerson ATHENA_REACT_ANDERSSON_PROTECTED_PERSON = PROTECTED_PERSON_FALSE;

  public static final String ALVE_REACT_ALFREDSSON_ID = "19411212-8154";
  public static final String ALVE_REACT_ALFREDSSON_FIRST_NAME = "Alve";
  public static final String ALVE_REACT_ALFREDSSON_MIDDLE_NAME = "React";
  public static final String ALVE_REACT_ALFREDSSON_LAST_NAME = "Alfredsson";
  public static final String ALVE_REACT_ALFREDSSON_FULL_NAME = "%s %s %s".formatted(
      ALVE_REACT_ALFREDSSON_FIRST_NAME,
      ALVE_REACT_ALFREDSSON_MIDDLE_NAME,
      ALVE_REACT_ALFREDSSON_LAST_NAME
  );
  public static final String ALVE_REACT_ALFREDSSON_STREET = "Reactgatan 456";
  public static final String ALVE_REACT_ALFREDSSON_CITY = "Karlstad";
  public static final String ALVE_REACT_ALFREDSSON_ZIP_CODE = "65340";
  public static final TestIndicated ALVE_REACT_ALFREDSSON_TEST_INDICATED = TEST_INDICATED_FALSE;
  public static final Deceased ALVE_REACT_ALFREDSSON_DECEASED = DECEASED_TRUE;
  public static final ProtectedPerson ALVE_REACT_ALFREDSSON_PROTECTED_PERSON = PROTECTED_PERSON_FALSE;

}
