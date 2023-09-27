package com.DropOff.app.controller.Courier;

import com.DropOff.app.controller.Courier.CourierController;
import com.DropOff.app.model.Courier.Package;
import com.DropOff.app.service.PackageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
public class CourierControllerTests {
    @InjectMocks
    CourierController packageController;
    @Mock
    PackageServiceImpl packageService;
    @Mock
    Package _package;

    @Test
    public void createPackageTest() {

        packageController.createPackage(_package);

        verify(packageService,times(1)).storePackage(_package);
    }

    @Test
    public void getAllPackageTest() {

        packageController.getAllPackages();

        verify(packageService,times(1)).getPackages();
    }

    @Test
    public void updatePackageTest() {

        packageController.updatePackage(_package);

        verify(packageService,times(1)).updatePackage(_package);
    }
}
