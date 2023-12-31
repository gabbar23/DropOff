package com.DropOff.app.controller.Courier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.DropOff.app.controller.Courier.RoutesController;
import com.DropOff.app.model.Courier.DashboardFilter;
import com.DropOff.app.model.Courier.Routes;
import com.DropOff.app.repository.*;
import com.DropOff.app.service.*;

class RouteControllerTest {

    private RoutesController driverRouteController;

    @Mock
    private DropOffDriverRouteFilter driverRouteFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        driverRouteController = new RoutesController();
        driverRouteController.setDriverRouteFilter(driverRouteFilter);
    }

    @Test
    void testCreateDriverRoute() throws JsonProcessingException {
        // given
        String jsonString = "{\"driverId\":\"5678\",\"sourceCity\":\"New York\",\"sourceCityReferenceId\":\"NYC\",\"destinationCity\":\"Chicago\",\"destinationCityReferenceId\":\"CHI\",\"maxPackages\":3,\"maxLength\":12,\"maxWidth\":10,\"maxHeight\":8,\"pickupDate\":\"2023-05-01\",\"dropoffDate\":\"2023-05-04\",\"daysToDeliver\":3,\"pickupLocationCoords\":[40.7128,-74.006],\"dropoffLocationCoords\":[41.8781,-87.6298],\"allowedCategory\":[\"books\",\"toys\"],\"radius\":100,\"price\":150}";

        ObjectMapper objectMapper = new ObjectMapper();
        Routes driverRoute = objectMapper.readValue(jsonString, Routes.class);
        when(driverRouteFilter.save(any(Routes.class))).thenReturn(driverRoute);

        // when
        ResponseEntity<?> response = driverRouteController.createDriverRoute(jsonString);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("Driver Details saved : \n" + driverRoute);
    }

    @Test
    void testGetDriverRoutesByFilters() {
        // given
        DashboardFilter dashboardFilters = new DashboardFilter();
        dashboardFilters.setSourceCity("city1");
        dashboardFilters.setDestination("city2");
        dashboardFilters.setPickupDataTime("2022-12-31T23:59:59");
        dashboardFilters.setMaxPackages("10");
        dashboardFilters.setAllowedCategory("A");
        dashboardFilters.setRadius("50");
        dashboardFilters.setPrice("100");
        dashboardFilters.setCategory("B");
        List<Routes> expectedDriverRoutes = new ArrayList<>();
        when(driverRouteFilter.getDriverRoutesByFilters(any(DashboardFilter.class))).thenReturn(expectedDriverRoutes);

        // when
        List<Routes> actualDriverRoutes = driverRouteController.getDriverRoutesByFilters("city1", "city2", "2022-12-31T23:59:59", "10", "A", "50", "100", "B");

        // then
        assertThat(actualDriverRoutes).isEqualTo(expectedDriverRoutes);
    }

    @Test
    void testGetDriverRoutes() {
        // given
        List<Routes> expectedDriverRoutes = new ArrayList<>();
        when(driverRouteFilter.getDriverRouteById("1234")).thenReturn(expectedDriverRoutes);

        // when
        List<Routes> actualDriverRoutes = driverRouteController.getDriverRoutes("1234");

        // then
        assertThat(actualDriverRoutes).isEqualTo(expectedDriverRoutes);
    }
}
