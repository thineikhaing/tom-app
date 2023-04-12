package com.nus.tom.repository;

import com.nus.tom.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface LeaveRepository extends JpaRepository<Leave, String> {

    @Query(value = "SELECT * FROM leave WHERE employee_id  = :employeeId AND leave_type  = :leaveType AND status  = :status ", nativeQuery = true)
    List<Leave> findByEmployeeIdAndLeaveTypeAndStatus(@Param("employeeId") String employeeId, @Param("leaveType") String leaveType, @Param("status") String status);

    @Query(value = "SELECT * FROM leave WHERE employee_id  = :employeeId AND leave_type  = :leaveType AND " + "start_date  = :startDate AND end_date  = :endDate ", nativeQuery = true)
    List<Leave> findByEmployeeIdAndLeaveTypeAndStartDateAndEndDate(@Param("employeeId") String employeeId, @Param("leaveType") String leaveType, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

    @Query(value = "SELECT * FROM leave WHERE employee_id  = :employeeId ", nativeQuery = true)
    List<Map<String, Object>> findByEmployeeId(@Param("employeeId") String employeeId);
}

