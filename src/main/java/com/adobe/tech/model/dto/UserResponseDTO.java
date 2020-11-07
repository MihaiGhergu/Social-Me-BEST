package com.adobe.tech.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private Boolean isArtist;
    private String nickname;
    private String firstName;
    private String latitude;
    private String longitude;
    private ArrayList<Integer> interests;
}
