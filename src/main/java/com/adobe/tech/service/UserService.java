package com.adobe.tech.service;

import com.adobe.tech.model.Session;
import com.adobe.tech.model.dto.UserLoginDTO;
import com.adobe.tech.repository.SessionRepository;
import com.adobe.tech.repository.UserRespository;
import com.adobe.tech.model.User;
import com.adobe.tech.model.dto.UserRequestDTO;
import com.adobe.tech.model.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.*;

@AllArgsConstructor
@Service
public class UserService {

    private UserRespository userRespository;
    private SessionRepository sessionRepository;



    public UserResponseDTO save(UserRequestDTO person) {
        if(!this.checkUser(person)) {
            return null;
        }
        User newUser = userRespository.save(
                new User(person.getIsArtist(), person.getNickname(), person.getFirstName(),
                            person.getLastName(), person.getEmail(), person.getPassword(),
                            person.getPhoneNumber(), person.getLatitude(), person.getLongitude(),
                            person.getInterests()));
        return  new UserResponseDTO(newUser.getId(), newUser.getIsArtist(),
                newUser.getNickname(), newUser.getFirstName(), newUser.getLatitude(), newUser.getLongitude());
    }

    public Boolean checkUser(UserRequestDTO logAccount) {
        System.out.println("CHECK "+logAccount);
        User user = userRespository.getUserByEmail(logAccount.getEmail());
        System.out.println(user);
        return user == null;

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

    public static <K,V extends Comparable<? super V>>
    SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<>(
                (e1, e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1;
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    public Set<Long> getClose(Long id) {
        User user = userRespository.getUserById(id);
        double latitude = Double.parseDouble(user.getLatitude());
        double longitude = Double.parseDouble(user.getLongitude());
        Map<Long, Double> userDistances = new TreeMap<>();
        List<UserResponseDTO> allUsers = getAll();
        for (UserResponseDTO crtUser : allUsers) {
            if(!crtUser.getId().equals(id)) {
                double dist = distance(latitude,
                                        Double.parseDouble(crtUser.getLatitude()),
                                        longitude,
                                        Double.parseDouble(crtUser.getLongitude()));
                userDistances.put(crtUser.getId(), dist);
            }
        }
//         for (Map.Entry<Long, Double> entry : userDistances.entrySet()) {
//            System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
//        }
        Set<Long> result = new HashSet<>();
        for (Map.Entry<Long, Double> entry : entriesSortedByValues(userDistances)) {
            result.add(entry.getKey());
        }

        return result;
    }

    public String checkCredentials(UserLoginDTO credentials) {
        User user = userRespository.getUserByEmail(credentials.getEmail());
        if (user == null)
            return null;
        if (user.getPassword().compareTo(credentials.getPassword()) != 0)
            return  null;
        String token = UUID.randomUUID().toString();
        sessionRepository.save(new Session(user.getId(), token));
        user.setLatitude(credentials.getLatitude());
        user.setLongitude(credentials.getLongitude());
        userRespository.save(user);
        return token;
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
