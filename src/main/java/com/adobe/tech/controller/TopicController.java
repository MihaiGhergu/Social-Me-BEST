package com.adobe.tech.controller;

import com.adobe.tech.model.dto.TopicRequestDTO;
import com.adobe.tech.model.dto.TopicResponseDTO;
import com.adobe.tech.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping(path = "/socialme/topics")
public class TopicController {
    private TopicService topicService;

    @PostMapping
    public ResponseEntity createTopic(@RequestBody TopicRequestDTO topic) {
        TopicResponseDTO createdTopic = topicService.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTopicById(@PathVariable Long id) {
        if(topicService.deleteById(id))
            return ResponseEntity.status(HttpStatus.OK).body("Topic with id = "+id+ " successfully deleted");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such topic");
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
    public ResponseEntity getAllAccounts() {
        List<TopicResponseDTO> response = topicService.getAll();
        System.out.println("TOATE = "+response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
