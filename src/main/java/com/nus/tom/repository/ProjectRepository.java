package com.nus.tom.repository;

import com.nus.tom.model.Department;
import com.nus.tom.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> {
}