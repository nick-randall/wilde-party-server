package com.stomp.chat.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stomp.chat.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
