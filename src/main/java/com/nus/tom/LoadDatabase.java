package com.nus.tom;

import com.nus.tom.model.Department;
import com.nus.tom.model.Role;
import com.nus.tom.model.enums.ERole;
import com.nus.tom.repository.DepartmentRepository;
import com.nus.tom.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository, DepartmentRepository departmentRepository) {
        return args -> {
//            log.info("Preloading " + roleRepository.save(new Role(ERole.ROLE_USER)));
//            log.info("Preloading " + roleRepository.save(new Role(ERole.ROLE_ADMIN)));
//            log.info("Preloading " + roleRepository.save(new Role(ERole.ROLE_MANAGER)));
//            log.info("Preloading " + roleRepository.save(new Role(ERole.ROLE_APPROVER)));
//            log.info("Preloading " + departmentRepository.save(new Department("Software Development", "Software Development Department")));
//            log.info("Preloading " + departmentRepository.save(new Department("IT Infrastructure and Operations", "IT Infrastructure and Operations Department")));
//            log.info("Preloading " + departmentRepository.save(new Department("Technical Support and Customer Service", "Technical Support and Customer Service Department")));
//            log.info("Preloading " + departmentRepository.save(new Department("Sales and Marketing", "Sales and Marketing Department")));
//            log.info("Preloading " + departmentRepository.save(new Department("Human Resources", "Human Resources Department")));
//            log.info("Preloading " + departmentRepository.save(new Department("Finance and Accounting", "Finance and Accounting Department")));
//            log.info("Preloading " + departmentRepository.save(new Department("Product Management", "Product Management Department")));
        };
    }
}
