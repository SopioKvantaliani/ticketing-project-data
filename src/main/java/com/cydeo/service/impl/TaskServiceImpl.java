package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectMapper projectMapper, UserService userService, UserMapper userMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @Override
    public List<TaskDTO> listAllTasks() {
        return taskRepository.findAll().stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO dto) {
    dto.setTaskStatus(Status.OPEN);
    dto.setAssignedDate(LocalDate.now());
    Task task = taskMapper.convertToEntity(dto);
    taskRepository.save(task);
    }

    @Override
    public void update(TaskDTO dto) {
        Optional<Task> task = taskRepository.findById(dto.getId());
        Task convertedTask  = taskMapper.convertToEntity(dto);

        if(task.isPresent()){
                                        //if there is no status just get from Db, if yes, use that status.
            convertedTask.setTaskStatus(dto.getTaskStatus()==null ? task.get().getTaskStatus() : dto.getTaskStatus() );
            convertedTask.setAssignedDate(task.get().getAssignedDate());
            taskRepository.save(convertedTask);
        }

    }

    @Override
    public void delete(Long id) {

       Optional <Task> foundTask =  taskRepository.findById(id);
       if (foundTask.isPresent())
           foundTask.get().setIsDeleted(true);
       taskRepository.save(foundTask.get());
    //We put if statement to handle some errors if there is any.

    }

    @Override
    public TaskDTO findById(Long id) {
       Optional <Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            return taskMapper.convertToDto(task.get());
        }
        return null;
    }

    @Override
    public int totalNonCompletedTask(String projectCode) {
        return taskRepository.totalNonCompletedTasks (projectCode);
    }

    @Override
    public int totalCompletedTasks(String projectCode) {
        return taskRepository.totalCompletedTasks (projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO projectDTO) {
        Project project = projectMapper.convertToEntity (projectDTO);
        List <Task> tasks = taskRepository.findAllByProject (project);
        tasks.forEach(task -> delete(task.getId()));
    }

    @Override
    public void completeByProject(ProjectDTO projectDTO) {
        Project project = projectMapper.convertToEntity (projectDTO);
        List <Task> tasks = taskRepository.findAllByProject (project);
        tasks.stream().map(taskMapper::convertToDto).forEach(taskDTO -> {
            taskDTO.setTaskStatus(Status.COMPLETE);
            update((taskDTO));
                } );
    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {
        UserDTO loggedInUser = userService.findByUserName("john@employee.com"); //since we don't have any login system and security, I'll do hardCoded
        List<Task> tasks = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee (status, userMapper.convertToEntity(loggedInUser));

        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {
        UserDTO loggedInUser = userService.findByUserName("john@employee.com"); //since we don't have any login system and security, I'll do hardCoded
        //we need to go repository, and find there task status and assigned to managers, convert dto to entity
        List<Task> tasks = taskRepository.findAllByTaskStatusAndAssignedEmployee (status, userMapper.convertToEntity(loggedInUser));
        //return converted dto finally.
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO assignedEmployee) {
        //find all projects with no status and assigned manager from repository. As we pass dto parameter, we convert to entity to retrieve data from db
        List <Task> tasks = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee (Status.COMPLETE, userMapper.convertToEntity(assignedEmployee));
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

}

