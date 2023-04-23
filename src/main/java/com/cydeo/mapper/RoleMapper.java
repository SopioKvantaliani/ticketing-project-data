package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class RoleMapper {


    private ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //Give me RoleDto and I'll return Role
    public Role convertToEntity (RoleDTO dto){
        return modelMapper.map(dto, Role.class);
    }

    //Give me Role and I'll return Role DTO
    public RoleDTO convertToDto (Role entity){

    return modelMapper.map(entity, RoleDTO.class);

    }
}
