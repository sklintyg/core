package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.BETA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.BETA_REGIONEN_NAME;

import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider.CareProviderBuilder;
import se.inera.intyg.certificateservice.domain.unit.model.UnitName;

public class TestDataCareProvider {

  public static final CareProvider ALFA_REGIONEN = alfaRegionenBuilder().build();

  public static final CareProvider BETA_REGIONEN = betaRegionenBuilder().build();

  public static CareProviderBuilder alfaRegionenBuilder() {
    return CareProvider.builder()
        .hsaId(new HsaId(ALFA_REGIONEN_ID))
        .name(new UnitName(ALFA_REGIONEN_NAME));
  }

  public static CareProviderBuilder betaRegionenBuilder() {
    return CareProvider.builder()
        .hsaId(new HsaId(BETA_REGIONEN_ID))
        .name(new UnitName(BETA_REGIONEN_NAME));
  }
}
