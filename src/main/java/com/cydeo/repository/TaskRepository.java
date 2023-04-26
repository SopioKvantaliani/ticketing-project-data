package com.cydeo.repository;

import com.cydeo.dto.TaskDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface TaskRepository extends JpaRepository <TaskDTO, Long> { //first we need to pass what entity and then primary key;

}
