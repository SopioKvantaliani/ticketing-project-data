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
@Table(name = "projects")
@NoArgsConstructor
@Where(clause = "is_deleted=false")
public class Project extends BaseEntity {
    @Column (unique = true) //make unique annotation
    private String projectCode;
    private String projectName;

    @Column (columnDefinition = "DATE") //we don't need to put this annotation, it already understands but let's put to show extra code
    private LocalDate startDate;

    @Column (columnDefinition = "DATE")
    private LocalDate endDate;

    @Enumerated (EnumType.STRING)
    private Status projectStatus;

    private String projectDetail;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "manager_id")
    private User assignedManager;

}
