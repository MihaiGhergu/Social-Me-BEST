package com.adobe.tech.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Data
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long topicId;
    private Long ownerId;
    private String title;
    private String date;
    private Long likesCount;
    private String content;
    private ArrayList<Long> likes;

    public Post(Long topicId, Long ownerId, String title, String date, String content) {
        this.topicId = topicId;
        this.ownerId = ownerId;
        this.title = title;
        this.date = date;
        this.content = content;
        this.likesCount = 0L;
        this.likes = new ArrayList<>();
    }

    public void liked(Long userId) {
        if(this.likes.contains(userId))
            return;
        this.likes.add(userId);
        this.likesCount++;
    }

    public void disliked(Long userId) {
        if(!this.likes.contains(userId))
            return;
        this.likes.remove(userId);
        this.likesCount--;
    }
}
