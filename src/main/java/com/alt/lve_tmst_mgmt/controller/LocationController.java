package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.entity.Location;
import com.alt.lve_tmst_mgmt.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Added for logging
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LocationController {

    @Autowired
    LocationService locationService;

    @GetMapping("/public/getAllLocation")
    public List<Location> getAllLocation() {

        log.info("GET /public/getAllLocation - fetching all locations");
        List<Location> locations = locationService.getAllLocation();
        log.info("GET /public/getAllLocation - fetched {} locations",
                (locations != null ? locations.size() : 0));

        return locations;
    }
}