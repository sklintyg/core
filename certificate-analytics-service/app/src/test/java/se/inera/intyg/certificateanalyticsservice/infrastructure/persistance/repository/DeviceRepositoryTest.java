package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.DeviceEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities;

@ExtendWith(MockitoExtension.class)
class DeviceRepositoryTest {

  @InjectMocks
  private DeviceRepository deviceRepository;
  @Mock
  private DeviceEntityRepository deviceEntityRepository;

  @Test
  void shouldCreateNewDeviceEntityIfNotExists() {
    final var device = TestDataEntities.deviceEntity();
    final var savedDevice = mock(DeviceEntity.class);
    when(deviceEntityRepository.findByDevice(device.getDevice())).thenReturn(Optional.empty());
    when(deviceEntityRepository.save(device)).thenReturn(savedDevice);

    final var result = deviceRepository.findOrCreate(device.getDevice());

    assertEquals(savedDevice, result);
  }

  @Test
  void shouldFindExistingDeviceEntity() {
    final var deviceName = TestDataEntities.deviceEntity().getDevice();
    final var entity = mock(DeviceEntity.class);
    when(deviceEntityRepository.findByDevice(deviceName)).thenReturn(Optional.of(entity));

    final var result = deviceRepository.findOrCreate(deviceName);

    assertEquals(entity, result);
  }
}

