package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public void save(TaskDTO dto) {

    }

    @Override
    public TaskDTO update(TaskDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<TaskDTO> listAllTasks() {
        return null;
    }

    @Override
    public TaskDTO listById(Long Id) {
        return null;
    }
}
