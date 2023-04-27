package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table (name = "users")
@NoArgsConstructor
//@Where(clause = "is_deleted=false") //soft delete, instead of hard. When we put on class level, it means whatever repository is using user entity, all those queries belongs to that repository automatically concatenate this annotation.
public class User extends BaseEntity {

    private String firstName;
    private String lastName;

    @Column (unique = true) //userName is unique
    private String userName;
    private String passWord;
    private boolean enabled;
    private String phone;

    @ManyToOne
    private Role role;
    @Enumerated (EnumType.STRING)
    private Gender gender;



}
