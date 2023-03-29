package com.nus.tom.repository;

import com.nus.tom.model.Employee;
import com.nus.tom.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepository extends JpaRepository<Leave, String> {
}
