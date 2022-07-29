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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class UpdateTest {
    private Update update = new Update();
    @Test
    void executeUpdateTest() {
        MockedStatic<UserInputUtil> userInputUtilMockedStatic = Mockito.mockStatic(UserInputUtil.class);
        userInputUtilMockedStatic.when(() -> UserInputUtil.getUserInput(Mockito.any(), Mockito.any())).thenReturn(1, 2);
        VehicleService<Bus> service = VehicleServiceFactory.getInstance().build(VehicleType.BUS);
        List<Bus> buses = service.createAndSave(5);
        Bus updateBus = buses.get(2);
        BigDecimal oldPrice = updateBus.getPrice();
        update.execute();
        Optional <Bus> actual = service.findOneById(updateBus.getId());
        Assertions.assertEquals(oldPrice.add(BigDecimal.TEN),actual.get().getPrice());
    }
}
