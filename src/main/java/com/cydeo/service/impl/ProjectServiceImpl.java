package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService, UserMapper userMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
    }


    @Override
    public ProjectDTO getByProjectCode(String code) {
        Project project = projectRepository.findByProjectCode(code);
        return projectMapper.convertToDto(project);
    }

    @Override
    public List<ProjectDTO> listAllProjects() {

        List<Project> list = projectRepository.findAll(Sort.by("projectCode"));

        return list.stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {

        dto.setProjectStatus(Status.OPEN);
        Project project = projectMapper.convertToEntity(dto);
        projectRepository.save(project);
    }

    @Override
    public void update(ProjectDTO dto) {

        //we go to dataBase, and we grab Dtl based on code;
        Project project = projectRepository.findByProjectCode(dto.getProjectCode());

        //convert to entity
        Project convertedProject = projectMapper.convertToEntity(dto);

        convertedProject.setId(project.getId());

        //We set the project status, because it is  not in the form.
        convertedProject.setProjectStatus(project.getProjectStatus());

        projectRepository.save(convertedProject);


    }

    @Override
    public void delete(String code) {

        Project project = projectRepository.findByProjectCode(code);
        project.setIsDeleted(true);
        projectRepository.save(project);
    }

    @Override
    public void complete(String projectCode) {

        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {
        //give me whoever is log in the system as a manager. As it comes from UI, we assign to DTO.
        UserDTO currentUserDto =userService.findByUserName("harold@manager.com"); //capture user
        User user = userMapper.convertToEntity(currentUserDto); //We convert DTO to Entity
        List <Project> list = projectRepository.findAllByAssignedManager(user); //hey db, give me all projects assigned to manager login in the system.
        //db project list doesn't have 2 fields,  private int completeTaskCounts, private int unfinishedTaskCounts;
        //I need to convert in Project DTO, and set these two fields



        return list.stream().map(project -> {
            ProjectDTO obj = projectMapper.convertToDto(project);
            obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTask (project.getProjectCode())); //Give me project as parameter and I will give you all tasks not completed;
            obj.setCompleteTaskCounts(taskService.totalCompletedTasks (project.getProjectCode()));
            return obj;
        }

        ).collect(Collectors.toList());
    }
}
