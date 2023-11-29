package se.inera.intyg.intygproxyservice.integration.organization.client.converter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toXMLGregorianCalendar;
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.truncateToSeconds;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.BusinessClassification;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.GeoCoordRt90;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.GeoCoordSweref99;
import se.riv.infrastructure.directory.organization.getunitresponder.v4.BusinessClassificationType;
import se.riv.infrastructure.directory.organization.getunitresponder.v4.GeoCoordRt90Type;
import se.riv.infrastructure.directory.organization.getunitresponder.v4.GeoCoordSWEREF99Type;
import se.riv.infrastructure.directory.organization.getunitresponder.v4.UnitType;
import se.riv.infrastructure.directory.organization.v3.AddressType;

@ExtendWith(MockitoExtension.class)
class UnitTypeConverterTest {

  @Mock
  private AddressTypeConverter addressTypeConverter;

  @Mock
  private GeoCoordRt90TypeConverter geoCoordRt90TypeConverter;

  @Mock
  private GeoCoordSweref99TypeConverter geoCoordSweref99TypeConverter;

  @Mock
  private BusinessClassificationTypeConverter businessClassificationTypeConverter;

  @InjectMocks
  private UnitTypeConverter unitTypeConverter;

  public static final LocalDateTime UNIT_END_DATE = LocalDateTime.now().plusDays(10);
  public static final LocalDateTime UNIT_START_DATE = LocalDateTime.now().plusDays(9);

  @Test
  void shouldConvertName() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getUnitName(), response.getUnitName());
  }

  @Test
  void shouldConvertId() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getUnitHsaId(), response.getUnitHsaId());
  }

  @Test
  void shouldConvertCountyName() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getCountyName(), response.getCountyName());
  }

  @Test
  void shouldConvertCountyCode() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getCountyCode(), response.getCountyCode());
  }

  @Test
  void shouldConvertMunicipalityName() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getMunicipalityName(), response.getMunicipalityName());
  }

  @Test
  void shouldConvertMunicipalityCode() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getMunicipalityCode(), response.getMunicipalityCode());
  }

  @Test
  void shouldConvertFeignedUnit() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.isFeignedUnit(), response.getFeignedUnit());
  }

  @Test
  void shouldConvertLocation() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getLocation(), response.getLocation());
  }

  @Test
  void shouldConvertEndDate() {
    final var type = getType();
    final var response = unitTypeConverter.convert(type);

    assertEquals(truncateToSeconds(UNIT_END_DATE),
        truncateToSeconds(response.getUnitEndDate()));
  }

  @Test
  void shouldConvertStartDate() {
    final var type = getType();
    final var response = unitTypeConverter.convert(type);

    assertEquals(truncateToSeconds(UNIT_START_DATE),
        truncateToSeconds(response.getUnitStartDate()));
  }

  @Test
  void shouldConvertPostalCode() {
    final var type = getType();
    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getPostalCode(), response.getPostalCode());
  }

  @Test
  void shouldConvertMail() {
    final var type = getType();
    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getMail(), response.getMail());
  }

  @Test
  void shouldConvertAddress() {
    final var type = mock(UnitType.class);
    final var address = List.of("ADDRESS", "ADDRESS_2");
    final var addressType = mock(AddressType.class);
    when(addressTypeConverter.convertV3(any(AddressType.class))).thenReturn(address);
    when(type.getPostalAddress()).thenReturn(addressType);

    final var response = unitTypeConverter.convert(type);

    assertEquals(address, response.getPostalAddress());
  }

  @Test
  void shouldConvertCareType() {
    final var type = mock(UnitType.class);
    final var data = List.of("DATA1", "DATA2");
    when(type.getCareType()).thenReturn(data);

    final var response = unitTypeConverter.convert(type);

    assertEquals(data, response.getCareType());
  }

  @Test
  void shouldConvertBusinessType() {
    final var type = mock(UnitType.class);
    final var data = List.of("DATA1", "DATA2");
    when(type.getBusinessType()).thenReturn(data);

    final var response = unitTypeConverter.convert(type);

    assertEquals(data, response.getBusinessType());
  }

  @Test
  void shouldConvertManagement() {
    final var type = mock(UnitType.class);
    final var data = List.of("DATA1", "DATA2");
    when(type.getManagement()).thenReturn(data);

    final var response = unitTypeConverter.convert(type);

    assertEquals(data, response.getManagement());
  }

  @Test
  void shouldConvertGeoCord() {
    final var type = new UnitType();
    final var geoType = new GeoCoordRt90Type();
    final var geo = GeoCoordRt90.builder().build();
    type.setGeographicalCoordinatesRt90(geoType);
    when(geoCoordRt90TypeConverter.convert(any(GeoCoordRt90Type.class))).thenReturn(geo);

    final var response = unitTypeConverter.convert(type);

    assertEquals(geo, response.getGeographicalCoordinatesRt90());
  }

  @Test
  void shouldConvertGeoCordSwe() {
    final var type = new UnitType();
    final var geoType = new GeoCoordSWEREF99Type();
    final var geo = GeoCoordSweref99.builder().build();
    type.setGeographicalCoordinatesSWEREF99(geoType);
    when(geoCoordSweref99TypeConverter.convert(any(GeoCoordSWEREF99Type.class))).thenReturn(geo);

    final var response = unitTypeConverter.convert(type);

    assertEquals(geo, response.getGeographicalCoordinatesSweref99());
  }

  @Test
  void shouldConvertBusinessClassification() {
    final var type = mock(UnitType.class);
    final var businessType = new BusinessClassificationType();
    final var business = BusinessClassification.builder().build();
    when(type.getBusinessClassification())
        .thenReturn(List.of(businessType));
    when(businessClassificationTypeConverter.convert(any(BusinessClassificationType.class)))
        .thenReturn(business);

    final var response = unitTypeConverter.convert(type);

    assertEquals(business, response.getBusinessClassification().get(0));
  }

  private UnitType getType() {
    final var type = new UnitType();
    type.setUnitName("NAME");
    type.setUnitHsaId("HSAID");
    type.setCountyCode("CODE");
    type.setCountyName("COUNTY_NAME");
    type.setFeignedUnit(true);
    type.setLocation("LOCATION");
    type.setMail("MAIL");
    type.setUnitEndDate(toXMLGregorianCalendar(UNIT_END_DATE));
    type.setUnitStartDate(toXMLGregorianCalendar(UNIT_START_DATE));
    type.setPostalCode("POSTAL_CODE");

    return type;
  }

}