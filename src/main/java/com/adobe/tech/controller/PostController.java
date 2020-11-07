package com.adobe.tech.controller;

import com.adobe.tech.model.dto.PostRequestDTO;
import com.adobe.tech.model.dto.PostResponseDTO;
import com.adobe.tech.repository.SessionRepository;
import com.adobe.tech.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping(path = "/socialme/posts")
public class PostController {
    private PostService postService;
    private SessionRepository sessionRepository;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequestDTO post) {
        PostResponseDTO createdPost = postService.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @DeleteMapping("/{token}")
    public ResponseEntity deletePostById(@PathVariable String token) {
        Long id = sessionRepository.getSessionByToken(token).getUserId();
        if (id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid session!");
        if(postService.deleteById(id))
            return ResponseEntity.status(HttpStatus.OK).body("Post with id = "+id+ " successfully deleted");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such post");
    }
    //
//    @GetMapping("/{id}")
//    public ResponseEntity getAccountById(@PathVariable Long id) {
//        BankAccountResponseDTO response = bankAccountService.getById(id);
//        return response != null ? ResponseEntity.status(HttpStatus.OK).body(response)
//                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occured");
//    }
//
    @GetMapping("/all")
    public ResponseEntity getAllPosts() {
        List<PostResponseDTO> response = postService.getAll();
        System.out.println("TOATE = "+response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{id}/{user_token}/like")
    public ResponseEntity likePost(@PathVariable Long id, @PathVariable String user_token) {
        Long user_id = sessionRepository.getSessionByToken(user_token).getUserId();
        if (id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid session!");
        if(postService.likePost(id, user_id))
            return ResponseEntity.status(HttpStatus.OK).body("Post with id succesfully liked by "+user_id);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such post");
    }

    @PostMapping("/{id}/{user_token}/dislike")
    public ResponseEntity dislikePost(@PathVariable Long id, @PathVariable String user_token) {
        Long user_id = sessionRepository.getSessionByToken(user_token).getUserId();
        if (id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid session!");
        if(postService.dislikePost(id, user_id))
            return ResponseEntity.status(HttpStatus.OK).body("Post with id succesfully disliked by "+user_id);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such post");
    }

}
