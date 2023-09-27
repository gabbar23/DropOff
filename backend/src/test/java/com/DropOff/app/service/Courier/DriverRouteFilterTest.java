package com.DropOff.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.DropOff.app.model.Courier.DashboardFilter;
import com.DropOff.app.model.Courier.Routes;
import com.DropOff.app.repository.Authentication.UserRepository;
import com.DropOff.app.repository.Courier.RoutesRepository;

@ExtendWith(MockitoExtension.class)
public class DriverRouteFilterTest {

    @Mock
    private RoutesRepository driverRouteRepository;

    @InjectMocks
    private DropOffDriverRouteFilter driverRouteFilter;

    final private Long TEST_DRIVER_ROUTE = 2L;

    final private int EXPECTED_FILTER_SIZE = 2;

    @Test
    public void testFilterWithNullRoutes() {
        // given
        List<Routes> driverRoutes = null;

        // when
        List<Routes> filteredRoutes = driverRouteFilter.filter(driverRoutes);

        // then
        assertThat(filteredRoutes).isNull();
    }

    @Test
    public void testFilterWithEmptyRoutes() {
        // given
        List<Routes> driverRoutes = new ArrayList<>();

        // when
        List<Routes> filteredRoutes = driverRouteFilter.filter(driverRoutes);

        // then
        assertThat(filteredRoutes).isEqualTo(driverRoutes);
    }

    @Test
    public void testGetDriverRoutesByFilters() {
        // given
        DashboardFilter filter = new DashboardFilter();
        filter.setSourceCity("New York");
        filter.setDestination("Los Angeles");

        Routes driverRoute1 = new Routes();
        driverRoute1.setDriverRouteId(1L);
        driverRoute1.setSourceCity("New York");
        driverRoute1.setDestinationCity("Los Angeles");

        Routes driverRoute2 = new Routes();
        driverRoute2.setDriverRouteId(TEST_DRIVER_ROUTE);
        driverRoute2.setSourceCity("Chicago");
        driverRoute2.setDestinationCity("Dallas");

        List<Routes> expectedRoutes = Arrays.asList(driverRoute1, driverRoute2);

        when(driverRouteRepository.getDriverRoutesByFilters("New York", "Los Angeles", null, null, null, null, null, null))
                .thenReturn(expectedRoutes);

        // when
        List<Routes> filteredRoutes = driverRouteFilter.getDriverRoutesByFilters(filter);

        // then
        assertThat(filteredRoutes).hasSize(EXPECTED_FILTER_SIZE);
        assertThat(filteredRoutes).containsExactlyInAnyOrderElementsOf(expectedRoutes);
    }

    @Test
    public void testGetDriverRoutesByFilters_NoMatchingRoutes() {
        // given
        DashboardFilter filter = new DashboardFilter();
        filter.setSourceCity("New York");
        filter.setDestination("Los Angeles");

        List<Routes> expectedRoutes = Collections.emptyList();
        when(driverRouteRepository.getDriverRoutesByFilters("New York", "Los Angeles", null, null, null, null, null, null))
                .thenReturn(expectedRoutes);

        // when
        List<Routes> filteredRoutes = driverRouteFilter.getDriverRoutesByFilters(filter);

        // then
        assertThat(filteredRoutes).isEmpty();
    }

    @Test
    public void testGetDriverRoutesByFilters_NullInput() {
        // given
        DashboardFilter filter = null;

        // when
        List<Routes> filteredRoutes = driverRouteFilter.getDriverRoutesByFilters(filter);

        // then
        assertThat(filteredRoutes).isNull();
    }
    @Test
    public void testGetDriverRouteById_NullInput() {
        // given
        when(driverRouteRepository.getDriverRoutes(null)).thenReturn(null);

        // when
        List<Routes> filteredRoutes = driverRouteFilter.getDriverRouteById(null);

        // then
        assertThat(filteredRoutes).isNull();
    }

    @Test
    public void testGetDriverRouteById_NonExistingDriver() {
        // given
        when(driverRouteRepository.getDriverRoutes("3")).thenReturn(Collections.emptyList());

        // when
        List<Routes> filteredRoutes = driverRouteFilter.getDriverRouteById("3");

        // then
        assertThat(filteredRoutes).isEmpty();
    }

    @Test
    public void testFilter_NullInput() {
        // given
        List<Routes> driverRoutes = null;

        // when
        List<Routes> filteredRoutes = driverRouteFilter.filter(driverRoutes);

        // then
        assertThat(filteredRoutes).isNull();
    }
}