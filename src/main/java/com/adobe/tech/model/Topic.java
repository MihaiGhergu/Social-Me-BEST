package com.adobe.tech.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Data
@ToString
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String topicTitle;
    private Long ownerId;
    private ArrayList<Post> posts;

    public Topic(String topicTitle, Long ownerId) {
        this.topicTitle = topicTitle;
        this.ownerId = ownerId;
        this.posts = new ArrayList<>();
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void deletePost(Post post) {
        this.posts.remove(post);
    }
}
