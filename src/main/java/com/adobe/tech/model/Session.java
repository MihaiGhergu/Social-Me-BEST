package com.adobe.tech.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Data
@ToString
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private Long userId;
    private String token;

    public Session(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
