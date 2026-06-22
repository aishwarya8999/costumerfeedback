package com.example.costumerfeedback.controller;

import com.example.costumerfeedback.dto.FeedbackRequest;
import com.example.costumerfeedback.model.Feedback;
import com.example.costumerfeedback.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController
{
    private final FeedbackService feedbackService;

    @GetMapping("/feedback")
    public ResponseEntity<List<Feedback>> getAllFeedback()
    {
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id)
    {
        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }


    @PutMapping("/feedback/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id,
                                                    @RequestBody FeedbackRequest request)
    {
        return ResponseEntity.ok(feedbackService.adminUpdateFeedback(id, request));
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long id)
    {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.ok("Feedback deleted successfully");
    }
}
