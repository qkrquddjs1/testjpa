package com.ohgiraffers.test.user.repository;

import com.ohgiraffers.test.user.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT c FROM User c ORDER BY c.userName")
    List<User> findAllUser();

    @Query("SELECT u FROM User u WHERE u.gender IS NOT NULL ")
    List<User> findUsersWithNonNullGender();
    @Query("SELECT distinct u.gender FROM User u WHERE u.gender IS NOT NULL ")
    List<String> findDistinctUsersWithNonNullGender();

    List<User> findByGenderGreaterThan(Integer gender, Sort sort);

    List<User> findByGender(String gender);


    /*  JpaRepository 상속 받음으로써 기본 CRUD 가능 */
}
