package com.musala.delivery.drones.suites;

import com.musala.delivery.drones.entities.dto.DroneDto;
import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.enumerations.EModel;
import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.mappers.DroneMapper;
import com.musala.delivery.drones.services.impl.DroneServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.annotations.BeforeTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DroneServiceTests {
    @InjectMocks

    DroneServiceImpl droneService;

    @Mock
    DroneMapper droneMapper;

    @BeforeTest
    public void init() {
        MockitoAnnotations.openMocks(DroneServiceTests.class);
    }

   /* @Test
    public void testFindById() {
        //long id

        Assertions.assertEquals(, testFindById());
    }
    */
    @Test
    public void testGetAllAvailableDrones() {
        //when(droneService.getAllAvailableDrones()).thenReturn(getIdleDrones());
        getIdleDrones().forEach(
                drone -> {
                    when(droneMapper.toDto(any())).thenReturn(getDto(drone));
                }
        );
        List<DroneDto> droneDtos = droneService.getAllAvailableDrones();
        Assertions.assertEquals(3, droneDtos.size());
        droneDtos.forEach(
                drone -> Assertions.assertEquals(EStatus.IDLE, drone.getState())
        );
    }
/*
    @Test
    public void testFindAllDrones() {
        Assertions.assertTrue(false);
    }

    @Test
    public void testRegisterDrone(DroneRequestDto droneRequest) {
        Assertions.assertTrue(false);
    }

    @Test
    public void testGetDroneBySerialNumber(String serialNumber) {
        Assertions.assertTrue(false);
    }

    @Test
    public void testValidateDrone(DroneRequestDto droneRequest) {
        Assertions.assertTrue(false);
    }

    @Test
    public void testCheckDroneBatteryLevelById(long id) {
        Assertions.assertTrue(false);
    }

    @Test
    public void testUpdateDroneStateById(long id, EStatus state) {
        Assertions.assertTrue(false);
    }

    @Test
    public void testCheckDroneLoad(Optional<Drone> drone) {
        Assertions.assertTrue(false);
    }

    @Test
    public void testUpdateDrone(DroneRequestDto droneRequest) {
        Assertions.assertTrue(false);
    }

    @Test
    public void testSave(Drone drone) {
        Assertions.assertTrue(false);
    }

    @Test
    public void testGetWorkingDrones() {
        Assertions.assertTrue(false);
    }
*/
    private DroneDto getDto(Drone drone) {
        return DroneDto.builder()
                //.id(drone.getId())
                .batteryLevel(drone.getBatteryLevel())
                .weightLimit(drone.getWeightLimit())
                .model(drone.getModel())
                .serialNumber(drone.getSerialNumber())
                .state(drone.getState())
                .build();
    }

    private List<Drone> getIdleDrones() {
        List<Drone> drones = new ArrayList<>();
        drones.add(
                Drone.builder()
                        .id(1L)
                        .batteryLevel(95)
                        .weightLimit(10f)
                        .model(EModel.CRUISERWEIGHT)
                        .serialNumber("125666")
                        .state(EStatus.IDLE)
                        .build()
        );

        drones.add(
                Drone.builder()
                        .id(2L)
                        .batteryLevel(80)
                        .weightLimit(20f)
                        .model(EModel.HEAVYWEIGHT)
                        .serialNumber("125667")
                        .state(EStatus.IDLE)
                        .build()
        );

        drones.add(
                Drone.builder()
                        .id(3L)
                        .batteryLevel(75)
                        .weightLimit(10f)
                        .model(EModel.LIGHTWEIGHT)
                        .serialNumber("125668")
                        .state(EStatus.IDLE)
                        .build()
        );
        return drones;
    }
}
