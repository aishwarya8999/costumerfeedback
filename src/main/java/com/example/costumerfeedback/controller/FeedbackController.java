package com.example.costumerfeedback.controller;

import com.example.costumerfeedback.dto.FeedbackRequest;
import com.example.costumerfeedback.model.Feedback;
import com.example.costumerfeedback.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController
{
    private final FeedbackService feedbackService;


    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedback()
    {

        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }


    @GetMapping("/{id}")
    public ResponseEntity<List<Feedback>> getUserFeedback(@PathVariable Long id)
    {

        return ResponseEntity.ok(feedbackService.getByUser(id));
    }

    @PostMapping
    public ResponseEntity<Feedback> addFeedback(@RequestBody FeedbackRequest request)
    {
        return ResponseEntity.ok(feedbackService.saveFeedback(request));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(
            @PathVariable Long id,
            @RequestBody FeedbackRequest request,
            @AuthenticationPrincipal UserDetails userDetails)
    {

        return ResponseEntity.ok(feedbackService.updateFeedback(id, request.getUsersId(), request));
    }
}
