package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, ProjectService projectService, TaskService taskService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
    }


    @Override
    public List<UserDTO> listAllUsers () {

        List<User> userList = userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);
        return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }


    @Override
    public UserDTO findByUserName(String username) {

        User user = userRepository.findByUserNameAndIsDeleted(username, false);
        return userMapper.convertToDto(user);
    }
    @Override
    public void save(UserDTO user) {
        userRepository.save(userMapper.convertToEntity(user));
    }
    @Override
    public void deleteByUserName(String username) {

         userRepository.deleteByUserName (username); //here we try to execute query and delete by username. We need to add delete method in UserRepository
    }



    @Override
    public UserDTO update(UserDTO user) { //this user is whatever coming from UI (updated one)
        //Find current user
        User user1 = userRepository.findByUserNameAndIsDeleted(user.getUserName(),false );
        //Map update user dto to entity object;
        User convertedUser = userMapper.convertToEntity(user); //does this entity has any id or not? no id
        //set id to the converted object;
        convertedUser.setId(user1.getId());//user1 comes from DB. We assign here same id , as user Dto doesn't have it.
        userRepository.save(convertedUser);

        return findByUserName(user.getUserName());
    }

    @Override
    public void delete(String username) {
        //find user from DB first. Go to DB and get that user with userName;
        User user = userRepository.findByUserNameAndIsDeleted(username, false);

        if (checkIfUserCanBeDeleted(user)){
            user.setIsDeleted(true);
            user.setUserName(user.getUserName()+" - " + user.getId());//harold@manager.com-2
            userRepository.save(user);
        }

        //change the isDeleted field to true
        user.setIsDeleted(true);
        //Save the object in the Db
        userRepository.save(user);

    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> users = userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted (role, false);
        //we will convert one by one and that's why we use map
        return users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    private boolean checkIfUserCanBeDeleted (User user) { //since we are checking user can be deleted or not we can pass user entity; We can either Dto or entity
        switch (user.getRole().getDescription()) { //user is coming from parameter
            case "Manager": //1st case is manager
                List <ProjectDTO> projectDTOSList = projectService.listAllNonCompletedByAssignedManager (userMapper.convertToDto(user));
                return projectDTOSList.size()==0;
            case "Employee": //2nd case is employee
                List <TaskDTO> taskDTOSList = taskService.listAllNonCompletedByAssignedEmployee(userMapper.convertToDto(user));
                return taskDTOSList.size()==0;
            default:
                return true; //it is deletable user if we are in the default;
        }

    }

}
