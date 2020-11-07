package com.adobe.tech.service;

import com.adobe.tech.model.Topic;
import com.adobe.tech.model.dto.TopicRequestDTO;
import com.adobe.tech.model.dto.TopicResponseDTO;
import com.adobe.tech.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class TopicService {

    private TopicRepository topicRepository;

    public TopicResponseDTO save(TopicRequestDTO topic) {
        Topic newTopic = topicRepository.save(
                new Topic(topic.getTopicTitle(), topic.getOwnerId()));
        return  new TopicResponseDTO(newTopic.getId(), newTopic.getTopicTitle(),
                newTopic.getOwnerId());
    }


    public List<TopicResponseDTO> getAll() {
        List<TopicResponseDTO> result = new ArrayList<>();
        topicRepository.findAll().forEach(x->result.add(new TopicResponseDTO(x.getId(), x.getTopicTitle(),
                x.getOwnerId())));
        return result;
    }


    public boolean deleteById(Long id) {
        if(!topicRepository.findById(id).isPresent())
            return false;
        topicRepository.deleteById(id);
        return true;
    }
//
//    public List<BankAccountResponseDTO> getAll() {
//        System.out.println("get all!!!====");
//        List<BankAccountResponseDTO> result = new ArrayList<>();
//        bankAccountRepository.findAll().forEach(x->result.add(new BankAccountResponseDTO(x.getId(), x.getAccountNumber(), x.getBalance())));
//        return result;
//    }
//
//
//
//    public BankAccountResponseDTO getById(Long id) {
//        BankAccount res = bankAccountRepository.findById(id).orElse(null);
//        if(res == null)
//            return null;
//        return new BankAccountResponseDTO(res.getId(), res.getAccountNumber(), res.getBalance());
//    }
}
