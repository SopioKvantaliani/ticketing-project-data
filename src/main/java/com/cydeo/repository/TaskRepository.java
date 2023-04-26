package com.cydeo.repository;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface TaskRepository extends JpaRepository <Task, Long> { //first we need to pass what entity and then primary key;

    @Query ("select count (t) from Task t where t.project.projectCode= ?1 and t.taskStatus <> 'COMPLETE' ")
    int totalNonCompletedTasks (String projectCode);

    @Query (value = "select count (*) from tasks t join projects p on t.project_id=p.id where p.project_code = ?1 and t.task_status = 'COMPLETE'",nativeQuery = true )
    int totalCompletedTasks (String projectCode);

}
