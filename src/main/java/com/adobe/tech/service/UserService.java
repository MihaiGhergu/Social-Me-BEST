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
        return  new UserResponseDTO(newUser.getId(), newUser.getIsArtist(), newUser.getNickname(),
                newUser.getFirstName(), newUser.getLatitude(), newUser.getLongitude(), newUser.getInterests());
    }

    public Boolean checkUser(UserRequestDTO logAccount) {
        System.out.println("CHECK "+logAccount);
        User user = userRespository.getUserByEmail(logAccount.getEmail());
        System.out.println(user);
        return user == null;

    }

    public List<UserResponseDTO> getAll() {
        List<UserResponseDTO> result = new ArrayList<>();
        userRespository.findAll().forEach(x->result.add(new UserResponseDTO(x.getId(), x.getIsArtist(), x.getNickname(),
                x.getFirstName(), x.getLatitude(), x.getLongitude(), x.getInterests())));
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

    public static double similarity(ArrayList<Integer> interests1, ArrayList<Integer> interests2) {
        double similarityIndex = 0.0;
        for(int i = 0; i < 5; i++)
            similarityIndex += (interests1.get(i) - interests2.get(i)) * (interests1.get(i) - interests2.get(i));

        return Math.sqrt(similarityIndex);
    }

    public static <K,V extends Comparable<? super V>>
    SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<>(
                (e1, e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    int i = res != 0 ? res : 1;
                    return i;
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    public Set<UserResponseDTO> getClose(Long id) {
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

        return getUsersSorted(userDistances);
    }

    public Set<UserResponseDTO> getSimilar(Long id) {
        User user = userRespository.getUserById(id);
        ArrayList<Integer> interests = user.getInterests();
        Map<Long, Double> userSimilarities = new TreeMap<>();
        List<UserResponseDTO> allUsers = getAll();
        for (UserResponseDTO crtUser : allUsers) {
            if(!crtUser.getId().equals(id)) {
                double similarity = similarity(interests, crtUser.getInterests());
                userSimilarities.put(crtUser.getId(), similarity);
            }
        }

        return getUsersSorted(userSimilarities);
    }

    private Set<UserResponseDTO> getUsersSorted(Map<Long, Double> usersMap) {
        Set<UserResponseDTO> result = new HashSet<>();
        for (Map.Entry<Long, Double> entry : entriesSortedByValues(usersMap)) {
            User myuser = userRespository.getUserById(entry.getKey());
            result.add(new UserResponseDTO(myuser.getId(), myuser.getIsArtist(), myuser.getNickname(),
                    myuser.getFirstName(), myuser.getLatitude(), myuser.getLongitude(), myuser.getInterests()));
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
}
