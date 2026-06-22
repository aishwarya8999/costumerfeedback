package com.example.costumerfeedback.dto;

import lombok.Data;

@Data
public class FeedbackRequest
{
    private String feedback;
    private Long usersId;
}
