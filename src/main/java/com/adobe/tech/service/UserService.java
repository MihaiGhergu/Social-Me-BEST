package com.adobe.tech.service;

import com.adobe.tech.repository.UserRespository;
import com.adobe.tech.model.User;
import com.adobe.tech.model.dto.UserRequestDTO;
import com.adobe.tech.model.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private UserRespository userRespository;

    public UserResponseDTO save(UserRequestDTO person) {
        if(!this.checkUser(person)) {
            return null;
        }
        User newUser = userRespository.save(
                new User(person.getIsArtist(), person.getNickname(), person.getFirstName(),
                            person.getLastName(), person.getEmail(), person.getPassword(),
                            person.getPhoneNumber(), person.getLocation(), person.getInterests(),
                            person.getSubscriptions()));
        return  new UserResponseDTO(newUser.getId(), newUser.getIsArtist(),
                newUser.getNickname(), newUser.getFirstName());
    }

    public Boolean checkUser(UserRequestDTO logAccount) {
        System.out.println("CHECK "+logAccount);
        User user = userRespository.getUserByEmail(logAccount.getEmail());
        System.out.println(user);
        if(user != null)
            return false;
        return true;

    }

    public List<UserResponseDTO> getAll() {
        List<UserResponseDTO> result = new ArrayList<>();
        userRespository.findAll().forEach(x->result.add(new UserResponseDTO(x.getId(), x.getIsArtist(),
                                             x.getNickname(), x.getFirstName())));
        return result;
    }



    public boolean deleteById(Long id) {
        if(!userRespository.findById(id).isPresent())
            return false;
        userRespository.deleteById(id);
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
