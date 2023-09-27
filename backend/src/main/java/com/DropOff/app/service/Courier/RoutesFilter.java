package com.DropOff.app.service;

import java.util.*;

import com.DropOff.app.model.*;
import com.DropOff.app.model.Courier.DashboardFilter;
import com.DropOff.app.model.Courier.Routes;
import com.DropOff.app.repository.Authentication.UserRepository;
import com.DropOff.app.repository.Courier.RoutesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DropOffDriverRouteFilter {
    @Autowired
    private final RoutesRepository driverRouteRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Constructor
     *
     * 
     * @param driverRouteRepository driver route repository
     */
    public DropOffDriverRouteFilter(RoutesRepository driverRouteRepository) {
        this.driverRouteRepository = driverRouteRepository;
    }

    /**
     * get filtered driver routes
     *
     * 
     * @param driverRoutes list of driver routes
     * @return list of driver routes
     */
    public List<Routes> filter(List<Routes> driverRoutes) {
        return driverRoutes;
    }

    /**
     * filter driver routes by filters
     *
     * 
     * @param filter filter object
     * @return list of driver routes
     */
    public List<Routes> getDriverRoutesByFilters(DashboardFilter filter) {
        if (filter == null)
            return null;

        return driverRouteRepository.getDriverRoutesByFilters(filter.getSourceCity(), filter.getDestination(), filter.getPickupDataTime(), filter.getMaxPackages()
                , filter.getRadius(), filter.getPrice(), filter.getAllowedCategory(), filter.getCategory());
    }

    /**
     * get driver routes by id
     *
     * 
     * @param driverId driver id
     * @return list of driver routes
     */
    public List<Routes> getDriverRouteById(String driverId) {
        return driverRouteRepository.getDriverRoutes(driverId);
    }

    /**
     * get driver routes by id
     *
     * 
     * @param driverRoute driver route object
     * @return list of driver routes
     */
    public Routes save(Routes driverRoute) {
        return driverRouteRepository.save(driverRoute);
    }
}
