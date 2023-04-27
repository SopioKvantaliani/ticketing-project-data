package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository <User, Long> {

    List <User> findAllByIsDeletedOrderByFirstNameDesc (Boolean deleted);

    //get user based on userName
    User findByUserNameAndIsDeleted (String username, Boolean deleted);

    @Transactional
    void deleteByUserName (String username);

    /*
    if you have more than one steps in action, if all the steps successfully completed, action will be successfully committed. But if any steps failes
    for any reason, app reverts the changes step 1 made. This action is called "rollback". When we put @Transactional
    it says, if anything happens rollback everything.
    @Transactional = we can put class level, or method level. When we delete, update, insert and this kind of actions, we need to put @Transactional.
    If anything happens we need to rollBack.
    @Transactional  = we use with drive query
    @Modify = we use jpql and native query.
    They create middle layer and track exceptions if any occurs.

     */

   List <User> findByRoleDescriptionIgnoreCaseAndIsDeleted (String description, Boolean deleted);

}
