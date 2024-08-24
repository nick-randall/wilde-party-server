package com.wildeparty.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wildeparty.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
