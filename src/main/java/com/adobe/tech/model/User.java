package com.adobe.tech.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Data
@ToString
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean isArtist;
    private String nickname;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String latitude;
    private String longitude;
    private ArrayList<Integer> interests;
    private ArrayList<Integer> subscriptions;

    public User(Boolean isArtist, String nickname, String firstName, String lastName, String email,
                String password, String phoneNumber, String latitude, String longitude,
                ArrayList<Integer> interests) {

        this.isArtist = isArtist;
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.interests = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
    }

    //to do subscribe
}
