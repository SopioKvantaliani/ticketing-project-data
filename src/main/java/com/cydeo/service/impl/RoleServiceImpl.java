package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> ListAllRoles() {

        //Controller called me and requested all RoleDto, so it can show in the drop-down in the UI.
        //I need to make a call to db and get all the roles from table
        //Go to repository and find a service which gives me the roles from db

        List < Role > roleList =  roleRepository.findAll();

        //I have Role Entities from DB
        //I need to convert those Role entities to DTOs
        //I need to use ModelMapper.
        //I already created a class called RoleMapper and there are methods for me that will make this conversion.

        return roleList.stream().map (roleMapper::convertToDto).collect(Collectors.toList());
        //when we want to check each element we use "map"
        //if you are calling method directly in the class, you can use double column operator.

    }

    @Override
    public RoleDTO findById(Long id) {
        return null;
    }
}

/*
When you mark a class with the @Service annotation, Spring will
recognize it as a service class and it will be eligible for autowiring
into other components, such as controllers or other services.

We add third party library, modelWrapper, because otherwise we need to do one by one operation to get RoleDTO and Set to Role.
That's why we use library, that does wrapping for us implicitly.

 */