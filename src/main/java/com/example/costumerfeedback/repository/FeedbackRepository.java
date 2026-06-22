package com.example.costumerfeedback.repository;

import com.example.costumerfeedback.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>
{

    List<Feedback> findByUsersId(Long usersId);

    Optional<Feedback> findByIdAndUsersId(Long id, Long usersId);
}
