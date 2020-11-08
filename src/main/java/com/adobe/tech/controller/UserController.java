package com.adobe.tech.controller;

import com.adobe.tech.model.Session;
import com.adobe.tech.model.User;
import com.adobe.tech.model.dto.TokenDTO;
import com.adobe.tech.model.dto.UserLoginDTO;
import com.adobe.tech.model.dto.UserRequestDTO;
import com.adobe.tech.repository.SessionRepository;
import com.adobe.tech.repository.UserRespository;
import com.adobe.tech.service.UserService;
import com.adobe.tech.model.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping(path = "/socialme")
public class UserController {
    private UserService userService;
    private SessionRepository sessionRepository;
    private UserRespository userRespository;

    @PostMapping("/register")
    public ResponseEntity registerPerson(@RequestBody UserRequestDTO person) {

        System.out.println("Am primit un register\n");
        UserResponseDTO savedAccount = userService.save(person);
        System.out.println(savedAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable Long id) {
        if(userService.deleteById(id))
            return ResponseEntity.status(HttpStatus.OK).body("User with id = "+id+ " successfully deleted");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such user");
    }

//    @GetMapping("/{id}")
//    public ResponseEntity getAccountById(@PathVariable Long id) {
//        BankAccountResponseDTO response = bankAccountService.getById(id);
//        return response != null ? ResponseEntity.status(HttpStatus.OK).body(response)
//                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occured");
//    }
//
    @GetMapping("/all")
    public ResponseEntity getAllAccounts() {
        List<UserResponseDTO> response = userService.getAll();
        System.out.println("TOATE = "+response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/close/{token}")
    public ResponseEntity getCloseUsers(@PathVariable String token) {
        Long id = sessionRepository.getSessionByToken(token).getUserId();
        if (id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid session!");
        System.out.println("ID = "+id);
        Set<UserResponseDTO> response = userService.getClose(id);
        System.out.println("IN APROPIERE : "+ response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/similar/{token}")
    public ResponseEntity getSimilarUsers(@PathVariable String token) {
        Long id = sessionRepository.getSessionByToken(token).getUserId();
        if (id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid session!");
        Set<UserResponseDTO> response = userService.getSimilar(id);
        System.out.println("SIMILARI : "+ response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserLoginDTO accountLogin) {
        String response = userService.checkCredentials(accountLogin);
        System.out.println("REZULTAT = " + response);
        return response != null ? ResponseEntity.status(HttpStatus.OK).body(new TokenDTO(response))
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error");
    }

    @GetMapping("/checkToken/{token}")
    public ResponseEntity checkToken(@PathVariable String token) {
        Session session = sessionRepository.getSessionByToken(token);
        if (session == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid session!");
        Long userId = session.getUserId();
        User myuser = userRespository.getUserById(userId);

        UserResponseDTO user = new UserResponseDTO(myuser.getId(), myuser.getIsArtist(), myuser.getNickname(),
                myuser.getFirstName(), myuser.getLatitude(), myuser.getLongitude(), myuser.getInterests());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
