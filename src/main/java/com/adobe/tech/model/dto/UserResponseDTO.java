package com.adobe.tech.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private Boolean isArtist;
    private String nickname;
    private String firstName;
}
