package com.nus.tom.repository;


import com.nus.tom.model.enums.ERole;
import com.nus.tom.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}