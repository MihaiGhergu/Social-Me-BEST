package com.adobe.tech.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Data
@ToString
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
    @ElementCollection(targetClass=String.class)
    @MapKeyColumn(name="Interest_points")
    private Map<String, Integer> interests;
    private ArrayList<Integer> subscriptions;

    public User(Boolean isArtist, String nickname, String firstName, String lastName, String email,
                String password, String phoneNumber, String latitude, String longitude,
                Map<String, Integer> interests, ArrayList<Integer> subscriptions) {

        this.isArtist = isArtist;
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.interests = new HashMap<>();
        this.subscriptions = new ArrayList<>();
    }
}
