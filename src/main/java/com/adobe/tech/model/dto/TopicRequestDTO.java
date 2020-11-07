package com.adobe.tech.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TopicRequestDTO {
    private String topicTitle;
    private Long ownerId;
}
