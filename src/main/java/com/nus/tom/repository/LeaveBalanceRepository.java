package com.nus.tom.repository;

import com.nus.tom.model.Leave;
import com.nus.tom.model.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, String> {
    @Query(value = "SELECT * FROM leave_balance WHERE employee_id  = :employeeId AND leave_type  = :leaveType ", nativeQuery = true)
    List<LeaveBalance> findByEmployeeIdAndLeaveType(@Param("employeeId") String employeeId, @Param("leaveType") String leaveType);

    @Query(value = "SELECT * FROM leave_balance WHERE employee_id  = :employeeId ", nativeQuery = true)
    List<Map<String,Object>> findByEmployeeId(@Param("employeeId") String employeeId);

}

