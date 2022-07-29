package com.command;

import com.model.vehicle.Bus;
import com.model.vehicle.VehicleType;
import com.service.VehicleService;
import com.service.VehicleServiceFactory;
import com.util.UserInputUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

class CreateTest {

    private Create create = new Create();

    @Test
    void executeCreateTest() {
        MockedStatic<UserInputUtil> userInputUtilMockedStatic = Mockito.mockStatic(UserInputUtil.class);
        userInputUtilMockedStatic.when(() -> UserInputUtil.getUserInput(Mockito.any(), Mockito.any())).thenReturn(1);
        create.execute();
        VehicleService<Bus> busVehicleService = VehicleServiceFactory.getInstance().build(VehicleType.BUS);
        List<Bus> buses = busVehicleService.getAllVehicle();
        Assertions.assertEquals(1, buses.size());
    }
}
