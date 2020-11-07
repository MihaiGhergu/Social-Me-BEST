package com.adobe.tech.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;


@Data
@AllArgsConstructor
public class PostRequestDTO {
    private Long topicId;
    private Long ownerId;
    private String title;
    private String date;
    private String content;
}
