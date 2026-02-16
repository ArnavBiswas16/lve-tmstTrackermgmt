package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.entity.Employee;
import com.alt.lve_tmst_mgmt.entity.LeaveForecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface LeaveForecastRepository extends JpaRepository<LeaveForecast, Integer> {

    List<LeaveForecast> findByEmployeeAndStartDateIn(Employee employee, Collection<LocalDate> dates);

    @Modifying
    int deleteByEmployeeAndStartDateIn(Employee employee, Collection<LocalDate> dates);
}