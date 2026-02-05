package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.Location;
import com.alt.lve_tmst_mgmt.repository.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService{

    @Autowired
    LocationRepo locationRepo;

    @Override
    public List<Location> getAllLocation() {

        List<Location> location = locationRepo.findAll();
        return location;
    }
}
