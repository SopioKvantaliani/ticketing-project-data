package com.cydeo.repository;

import com.cydeo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository <Role, Long> { //here we pass only entities, not DTO

    //give me the role based on the description
    //Role is return type; String Description = based on description, so we need to pass.
     Role findByDescription(String description);

    @Override
    List<Role> findAll();
    //even if we don't write this findAll method, Spring gives us because we extend JpaRepository.
}
