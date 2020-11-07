package com.adobe.tech.repository;

import com.adobe.tech.model.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {
     Session getSessionByToken(String token);
}
