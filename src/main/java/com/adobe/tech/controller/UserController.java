package com.adobe.tech.controller;

import com.adobe.tech.model.dto.UserRequestDTO;
import com.adobe.tech.service.UserService;
import com.adobe.tech.model.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping(path = "/socialme")
public class UserController {
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity registerPerson(@RequestBody UserRequestDTO person) {
        System.out.println("Am primit un register\n");
        UserResponseDTO savedAccount = userService.save(person);
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

    @GetMapping("/close/{id}")
    public ResponseEntity getCloseUsers(@PathVariable Long id) {
        List<UserResponseDTO> response = userService.getClose(id);
        System.out.println("IN APROPIERE : "+ response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
//
//    @PostMapping("/login")
//    public ResponseEntity loginUser(@RequestBody BankAccountRequestDTO accountLogin) {
//        BankAccountResponseDTO response = bankAccountService.checkUser(accountLogin);
//        System.out.println("REZULTAT = "+response);
//        return response != null ? ResponseEntity.status(HttpStatus.OK).body(response)
//                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error");
//    }

}
