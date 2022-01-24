package com.essential.app.repository;

import com.essential.app.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /*@EntityGraph(value = "user.listSchedule", type = EntityGraph.EntityGraphType.LOAD)
    Page<User> findAll(Pageable pageable);*/

    @EntityGraph(value = "user.listSchedule", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findById(Long id);
}
