package com.cydeo.service;

import com.cydeo.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    void save (TaskDTO dto);
    TaskDTO update (TaskDTO dto);
    void delete (Long id); //we use id because we don't have anything unique here rather than id.
    List<TaskDTO> listAllTasks ();
    TaskDTO listById (Long Id);
}
