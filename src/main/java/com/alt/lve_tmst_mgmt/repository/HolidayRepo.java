package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayRepo extends JpaRepository<Holiday, Integer> {

    @Query("select h from Holiday h join fetch h.location")
    List<Holiday> findAllWithLocation();

}
