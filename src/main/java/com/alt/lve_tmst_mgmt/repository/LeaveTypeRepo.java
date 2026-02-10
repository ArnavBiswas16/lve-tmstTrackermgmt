package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveTypeRepo extends JpaRepository<LeaveType, Integer> {


}
