package com.demo.demo1;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface SoftwareEngineerRepository extends JpaRepository<SoftwareEngineer,Integer>{
    Optional<SoftwareEngineer>findByEmail(String email);
    Optional<SoftwareEngineer>findByFirstName(String firstName);
}