package com.adobe.tech.model.dto;

        import lombok.AllArgsConstructor;
        import lombok.Data;

        import java.util.ArrayList;
        import java.util.Map;

@Data
@AllArgsConstructor
public class UserRequestDTO {
    private Boolean isArtist;
    private String nickname;
    private String firstName;
    private String password;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String latitude;
    private String longitude;
    private Map<String, Integer> interests;
    private ArrayList<Integer> subscriptions;
}
