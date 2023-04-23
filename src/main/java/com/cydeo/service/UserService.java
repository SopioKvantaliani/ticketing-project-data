package com.cydeo.service;

import com.cydeo.dto.UserDTO;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.User;

import java.util.List;

public interface UserService {

    List <UserDTO> listAllUsers ();
    UserDTO findByUserName (String username); //why username and not primary key? UI perspective when we want to update we see "userName". we'll update byId in DB
    void save (UserDTO user);
    void deleteByUserName (String username);
    UserDTO update (UserDTO user);

    void delete (String username);
}
