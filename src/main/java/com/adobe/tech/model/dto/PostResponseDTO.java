package com.adobe.tech.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Set;

@Data
@AllArgsConstructor
public class PostResponseDTO {
    private Long id;
    private Long topicId;
    private Long ownerId;
    private String title;
    private String date;
    private String content;
    private Long likesCount;
    private ArrayList<Long> likes;
}
