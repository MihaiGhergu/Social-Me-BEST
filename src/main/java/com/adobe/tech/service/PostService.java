package com.adobe.tech.service;

import com.adobe.tech.model.dto.PostRequestDTO;
import com.adobe.tech.model.dto.PostResponseDTO;
import com.adobe.tech.repository.UserRespository;
import com.adobe.tech.model.Post;
import com.adobe.tech.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PostService {

    private PostRepository postRepository;
    private UserRespository userRepository;

    public PostResponseDTO save(PostRequestDTO post) {
        Post newPost = postRepository.save(
                new Post(post.getTopicId(), post.getOwnerId(), post.getTitle(), post.getDate(), post.getContent()));
        return  new PostResponseDTO(newPost.getId(), newPost.getTopicId(), newPost.getOwnerId(), newPost.getTitle(),
                newPost.getDate(), newPost.getContent(), newPost.getLikesCount(), newPost.getLikes());
    }


    public List<PostResponseDTO> getAll() {
        List<PostResponseDTO> result = new ArrayList<>();
        postRepository.findAll().forEach(x->result.add(new PostResponseDTO(x.getId(), x.getTopicId(), x.getOwnerId(),
                                                        x.getTitle(), x.getDate(), x.getContent(), x.getLikesCount(),
                                                        x.getLikes())));
        return result;
    }


    public boolean deleteById(Long id) {
        if(!postRepository.findById(id).isPresent())
            return false;
        postRepository.deleteById(id);
        return true;
    }

    public boolean likePost(Long id, Long userId) {
        if(!postRepository.findById(id).isPresent())
            return false;
        Post myPost = postRepository.findById(id).orElse(null);
        if(myPost == null)
            return false;
        myPost.liked(userId);
        postRepository.save(myPost);
        return true;
    }

    public boolean dislikePost(Long id, Long userId) {
        if(!postRepository.findById(id).isPresent())
            return false;
        Post myPost = postRepository.findById(id).orElse(null);
        if(myPost == null)
            return false;
        myPost.disliked(userId);
        postRepository.save(myPost);
        return true;
    }
}
