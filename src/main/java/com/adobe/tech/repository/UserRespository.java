package com.adobe.tech.repository;

import com.adobe.tech.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRespository extends CrudRepository<User, Long> {
    User getUserByEmail(String email);
}
