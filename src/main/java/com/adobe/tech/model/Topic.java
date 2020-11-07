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
    private String ownerNickname;
    private ArrayList<Post> posts;

    public Topic(String topicTitle, String ownerNickname) {
        this.topicTitle = topicTitle;
        this.ownerNickname = ownerNickname;
        this.posts = new ArrayList<>();
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void deletePost(Post post) {
        this.posts.remove(post);
    }
}
