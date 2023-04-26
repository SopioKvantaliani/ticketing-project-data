package com.cydeo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isDeleted=false;
    @Column(nullable = false, updatable = false)
    //this field can not be null and whenever updatable, do not do any action.
    private LocalDateTime insertDateTime;
    @Column(nullable = false, updatable = false)
    private Long insertUserId;
    @Column(nullable = false)
    private LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    private Long lastUpdateUserId;


    @PrePersist //whenever we create something new
    private void onPrePersist () { //this method needs to be executed whenever we create new task, manager;
        this.insertDateTime = LocalDateTime.now(); //current dateTime
        this.lastUpdateDateTime = LocalDateTime.now();
        this.insertUserId = 1L; //it should be dynamic, now it's hard coded. Whoever updates this , should be that user's primary key.
        this.lastUpdateUserId =1L;

    }
    /*
    Why we wrote the method?
    To initialize fields inside the method.
     */
    @PreUpdate //whenever we update something new
    private void onPreUpdate (){ //this method needs to be executed whenever something updated.
        this.lastUpdateDateTime=LocalDateTime.now();
        this.lastUpdateUserId=1L;
    }

}
