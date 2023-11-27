package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toXMLGregorianCalendar;
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.truncateToSeconds;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CommissionRight;
import se.riv.infrastructure.directory.authorizationmanagement.v2.CommissionRightType;
import se.riv.infrastructure.directory.authorizationmanagement.v2.CommissionType;

@ExtendWith(MockitoExtension.class)
class CommissionTypeConverterTest {

  private static final LocalDateTime END_DATE = LocalDateTime.now();
  private static final LocalDateTime START_DATE = LocalDateTime.now().minusDays(10);

  private static final LocalDateTime END_DATE_UNIT = LocalDateTime.now().plusDays(1);
  private static final LocalDateTime START_DATE_UNIT = LocalDateTime.now().minusDays(9);

  @Mock
  private CommissionRightTypeConverter commissionRightTypeConverter;

  @InjectMocks
  private CommissionTypeConverter commissionTypeConverter;

  @Test
  void shouldConvertCommissionHsaId() {
    final var type = getType();

    final var response = commissionTypeConverter.convert(type);

    assertEquals(type.getCommissionHsaId(), response.getCommissionHsaId());
  }

  @Test
  void shouldConvertCommissionName() {
    final var type = getType();

    final var response = commissionTypeConverter.convert(type);

    assertEquals(type.getCommissionName(), response.getCommissionName());
  }

  @Test
  void shouldConvertCommissionPurpose() {
    final var type = getType();

    final var response = commissionTypeConverter.convert(type);

    assertEquals(type.getCommissionPurpose(), response.getCommissionPurpose());
  }

  @Test
  void shouldConvertHealthCareProviderOrgNo() {
    final var type = getType();

    final var response = commissionTypeConverter.convert(type);

    assertEquals(type.getHealthCareProviderOrgNo(), response.getHealthCareProviderOrgNo());
  }

  @Test
  void shouldConvertHealthCareProviderHsaId() {
    final var type = getType();

    final var response = commissionTypeConverter.convert(type);

    assertEquals(type.getHealthCareProviderHsaId(), response.getHealthCareProviderHsaId());
  }

  @Test
  void shouldConvertHealthCareProviderName() {
    final var type = getType();

    final var response = commissionTypeConverter.convert(type);

    assertEquals(type.getHealthCareProviderName(), response.getHealthCareProviderName());
  }

  @Test
  void shouldConvertPharmacyId() {
    final var type = getType();

    final var response = commissionTypeConverter.convert(type);

    assertEquals(type.getPharmacyIdentifier(), response.getPharmacyIdentifier());
  }

  @Test
  void shouldConvertFeignedCommission() {
    final var type = getType();

    final var response = commissionTypeConverter.convert(type);

    assertEquals(type.isFeignedCommission(), response.getFeignedCommission());
  }

  @Test
  void shouldConvertFeignedUnit() {
    final var type = getType();

    final var response = commissionTypeConverter.convert(type);

    assertEquals(type.isFeignedHealthCareUnit(), response.getFeignedHealthCareUnit());
  }

  @Test
  void shouldConvertFeignedProvider() {
    final var type = getType();

    final var response = commissionTypeConverter.convert(type);

    assertEquals(type.isFeignedHealthCareProvider(), response.getFeignedHealthCareProvider());
  }

  @Test
  void shouldConvertArchivedUnit() {
    final var type = getType();

    final var response = commissionTypeConverter.convert(type);

    assertEquals(type.isArchivedHealthCareUnit(), response.getArchivedHealthCareUnit());
  }

  @Test
  void shouldConvertArchivedProvider() {
    final var type = getType();

    final var response = commissionTypeConverter.convert(type);

    assertEquals(type.isArchivedHealthCareProvider(), response.getArchivedHealthCareProvider());
  }

  @Test
  void shouldConvertUnitStartDate() {
    final var type = getType();
    final var response = commissionTypeConverter.convert(type);

    assertEquals(truncateToSeconds(START_DATE_UNIT),
        truncateToSeconds(response.getHealthCareUnitStartDate()));
  }

  @Test
  void shouldConvertUnitEndDate() {
    final var type = getType();
    final var response = commissionTypeConverter.convert(type);

    assertEquals(truncateToSeconds(END_DATE_UNIT),
        truncateToSeconds(response.getHealthCareUnitEndDate()));
  }

  @Test
  void shouldConvertProviderStartDate() {
    final var type = getType();
    final var response = commissionTypeConverter.convert(type);

    assertEquals(truncateToSeconds(START_DATE),
        truncateToSeconds(response.getHealthCareProviderStartDate()));
  }

  @Test
  void shouldConvertProviderEndDate() {
    final var type = getType();
    final var response = commissionTypeConverter.convert(type);

    assertEquals(truncateToSeconds(END_DATE),
        truncateToSeconds(response.getHealthCareProviderEndDate()));
  }

  @Test
  void shouldConvertCommissionRight() {
    final var captor = ArgumentCaptor.forClass(CommissionRightType.class);
    final var commissionRight = new CommissionRightType();
    final var commissionRights = List.of(commissionRight, commissionRight);
    final var convertedCommissionRight = CommissionRight.builder().build();
    final var type = mock(CommissionType.class);
    when(type.getCommissionRight())
        .thenReturn(commissionRights);
    when(commissionRightTypeConverter.convert(any(CommissionRightType.class)))
        .thenReturn(convertedCommissionRight);

    final var response = commissionTypeConverter.convert(type);
    
    verify(commissionRightTypeConverter, times(2)).convert(captor.capture());
    assertEquals(2, response.getCommissionRight().size());
    assertEquals(convertedCommissionRight, response.getCommissionRight().get(1));
    assertEquals(convertedCommissionRight, response.getCommissionRight().get(0));
  }

  private CommissionType getType() {
    final var type = new CommissionType();
    type.setCommissionHsaId("HSA_ID");
    type.setCommissionName("NAME");
    type.setCommissionPurpose("PURPOSE");
    type.setHealthCareProviderEndDate(toXMLGregorianCalendar(END_DATE));
    type.setHealthCareProviderStartDate(toXMLGregorianCalendar(START_DATE));
    type.setHealthCareUnitStartDate(toXMLGregorianCalendar(START_DATE_UNIT));
    type.setHealthCareUnitEndDate(toXMLGregorianCalendar(END_DATE_UNIT));
    type.setHealthCareProviderOrgNo("ORG_NO");
    type.setHealthCareProviderHsaId("P_HSA_ID");
    type.setHealthCareProviderName("P_HSA_NAME");
    type.setPharmacyIdentifier("PHARMACY_ID");
    type.setArchivedHealthCareUnit(true);
    type.setArchivedHealthCareProvider(true);
    type.setFeignedHealthCareProvider(true);
    type.setFeignedCommission(true);
    type.setFeignedHealthCareUnit(true);

    return type;
  }
}