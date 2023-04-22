package com.cydeo.service;


import com.cydeo.dto.RoleDTO;

import java.util.List;

public interface RoleService {
   List<RoleDTO> ListAllRoles (); //we are on Service layer and DTO because it goes to UI side.
   RoleDTO findById (Long id);
}
