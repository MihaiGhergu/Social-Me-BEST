package com.adobe.tech.service;

import com.adobe.tech.repository.UserRespository;
import com.adobe.tech.model.User;
import com.adobe.tech.model.dto.UserRequestDTO;
import com.adobe.tech.model.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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
                            person.getPhoneNumber(), person.getLatitude(), person.getLongitude(),
                            person.getInterests(), person.getSubscriptions()));
        return  new UserResponseDTO(newUser.getId(), newUser.getIsArtist(),
                newUser.getNickname(), newUser.getFirstName(), newUser.getLatitude(), newUser.getLongitude());
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
                                             x.getNickname(), x.getFirstName(), x.getLatitude(), x.getLongitude())));
        return result;
    }

    public boolean deleteById(Long id) {
        if(!userRespository.findById(id).isPresent())
            return false;
        userRespository.deleteById(id);
        return true;
    }

    public static double distance(double lat1, double lat2, double lon1, double lon2) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    public Set<Long> getClose(Long id) {
        User user = userRespository.getUserById(id);
        double latitude = Double.parseDouble(user.getLatitude());
        double longitude = Double.parseDouble(user.getLongitude());
        Map<Long, Double> userDistances = new TreeMap<>();
        List<UserResponseDTO> allUsers = getAll();
        for (UserResponseDTO crtUser : allUsers) {
            if(crtUser.getId() != id) {
                double dist = distance(latitude,
                                        Double.parseDouble(crtUser.getLatitude()),
                                        longitude,
                                        Double.parseDouble(crtUser.getLongitude()));
                userDistances.put(crtUser.getId(), dist);
            }
        }

        return userDistances.keySet();
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
