package com.taskmaster.repository;

import com.taskmaster.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity,UUID> {

    boolean existsByEmail(String email);

    @Query("SELECT p.id FROM PersonEntity p WHERE p.email = :email")
    UUID findIdByEmail(@Param("email") String email);



}
