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

class RemoveTest {
    private Remove remove = new Remove();

    @Test
    void executeRemoveTest() {
        MockedStatic<UserInputUtil> userInputUtilMockedStatic = Mockito.mockStatic(UserInputUtil.class);
        userInputUtilMockedStatic.when(() -> UserInputUtil.getUserInput(Mockito.any(), Mockito.any())).thenReturn(1, 2);
        VehicleService<Bus> service = VehicleServiceFactory.getInstance().build(VehicleType.BUS);
        service.createAndSave(5);
        remove.execute();
        List<Bus> actual = service.getAllVehicle();
        Assertions.assertEquals(4, actual.size());
    }


}
