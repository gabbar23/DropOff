package com.DropOff.app.controller.Courier;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.DropOff.app.model.*;
import com.DropOff.app.model.Courier.DashboardFilter;
import com.DropOff.app.model.Courier.Routes;
import com.DropOff.app.repository.*;
import com.DropOff.app.service.DropOffDriverRouteFilter;

@RestController
public class RoutesController {
    private DropOffDriverRouteFilter driverRouteFilter;

    /**
     * Set driver route filter.
     *

     * @param driverRouteFilter driver route filter.
     */
    @Autowired
    public void setDriverRouteFilter(DropOffDriverRouteFilter driverRouteFilter) {
        this.driverRouteFilter = driverRouteFilter;
    }

    /**
     * Create driver route.
     *
     * @param jsonString json string.
     * @return response entity.
     * @throws JsonProcessingException json processing exception.
     */
    @PostMapping("/driverRoutes")
    public ResponseEntity<?> createDriverRoute(@RequestBody String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Routes driverRoute = objectMapper.readValue(jsonString, Routes.class);

        Routes savedDriverRoute = driverRouteFilter.save(driverRoute);
        return new ResponseEntity<>("Driver Details saved : \n" +  savedDriverRoute, HttpStatus.CREATED);
    }

    /**
     * Get driver routes by filters.
     *
     * @param sourceCity source city.
     * @param destination destination.
     * @param pickupDataTime pickup date time.
     * @param maxPackages max packages.
     * @param allowedCategory allowed category.
     * @param radius radius.
     * @param price price.
     * @param category category.
     * @return list of driver routes.
     */
    @GetMapping("/driverRoutes")
    public List<Routes> getDriverRoutesByFilters(
            @RequestParam(required = false) String sourceCity,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String pickupDataTime,
            @RequestParam(required = false) String maxPackages,
            @RequestParam(required = false) String allowedCategory,
            @RequestParam(required = false) String radius,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String category
    ) {
        DashboardFilter dashboardFilters = new DashboardFilter();
        dashboardFilters.setSourceCity(sourceCity);
        dashboardFilters.setDestination(destination);
        dashboardFilters.setPickupDataTime(pickupDataTime);
        dashboardFilters.setMaxPackages(maxPackages);
        dashboardFilters.setAllowedCategory(allowedCategory);
        dashboardFilters.setRadius(radius);
        dashboardFilters.setPrice(price);
        dashboardFilters.setCategory(category);

        return driverRouteFilter.getDriverRoutesByFilters(dashboardFilters);
    }

    /**
     * Get driver routes by driver id.
     *
     * @param driverId driver id.
     * @return list of driver routes.
     */
    @GetMapping("/driverRoutesByDriverId")
    public List<Routes> getDriverRoutes(@RequestParam(required = true) String driverId) {
        return driverRouteFilter.getDriverRouteById(driverId);
    }
}