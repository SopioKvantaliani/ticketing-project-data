package com.cydeo.mapper;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    private final ModelMapper modelMapper;
    /*
    We inject ModelMapper, but where is the bean?
    Bean is created in runner class and we use @Bean annotation because it is not custom made class, it's java built in class.

    Why we need modelWrapper?
    We need to convert entity to dto and dto to entity;
     */

    public TaskMapper (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public Task convertToEntity(TaskDTO dto){
        return modelMapper.map(dto,Task.class);

    }

    public TaskDTO convertToDto(Task entity){
        return modelMapper.map(entity,TaskDTO.class);
    }
}
