package com.adobe.tech.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicResponseDTO {
    private Long id;
    private String topicTitle;
    private Long ownerId;
}
