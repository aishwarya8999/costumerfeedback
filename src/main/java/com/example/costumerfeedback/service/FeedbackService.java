package com.example.costumerfeedback.service;

import com.example.costumerfeedback.dto.FeedbackRequest;
import com.example.costumerfeedback.model.Feedback;
import com.example.costumerfeedback.model.Users;
import com.example.costumerfeedback.repository.FeedbackRepository;
import com.example.costumerfeedback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FeedbackService
{
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    public List<Feedback> getAllFeedback()
    {
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(Long id)
    {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Feedback not found: " + id));
    }

    public void deleteFeedback(Long id)
    {
        if (!feedbackRepository.existsById(id))
            throw new NoSuchElementException("Feedback not found: " + id);
        feedbackRepository.deleteById(id);
    }

    public List<Feedback> getByUser(Long usersId)
    {
        return feedbackRepository.findByUsersId(usersId);
    }

    public Feedback saveFeedback(FeedbackRequest request)
    {
        Users user = userRepository.findById(request.getUsersId())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + request.getUsersId()));

        Feedback feedback = new Feedback();
        feedback.setFeedback(request.getFeedback());
        feedback.setUsers(user);

        return feedbackRepository.save(feedback);
    }

    public Feedback updateFeedback(Long feedbackId, Long usersId, FeedbackRequest request)
    {
        Feedback existing = feedbackRepository.findByIdAndUsersId(feedbackId, usersId)
                .orElseThrow(() -> new NoSuchElementException("Feedback not found or access denied"));

        existing.setFeedback(request.getFeedback());
        return feedbackRepository.save(existing);
    }

    public Feedback adminUpdateFeedback(Long feedbackId, FeedbackRequest request)
    {
        Feedback existing = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new NoSuchElementException("Feedback not found: " + feedbackId));

        existing.setFeedback(request.getFeedback());
        return feedbackRepository.save(existing);
    }
}
