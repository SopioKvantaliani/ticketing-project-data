package com.cydeo.entity;

import com.cydeo.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tasks")
@NoArgsConstructor
@Where(clause = "is_deleted=false")
public class TaskEntity extends BaseEntity {

    private String taskSubject; //for mapper naming is important
    private String taskDetail;
    @Enumerated (EnumType.STRING)
    private Status taskStatus;
    @Column (columnDefinition = "DATE")
    private LocalDate assignedDate;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "employee_id")
    private User assignedEmployee;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "project_id")
    private Project project;

     /*
    In the case of FetchType.LAZY, it means that the associated data should not be loaded unless it is explicitly requested.
    e.g. here, when we retrieve "User" field tasks would not be loaded unless we'll do explicitly.
    This can be useful when dealing with large amount of data, which might result performance issues.
     */

}
