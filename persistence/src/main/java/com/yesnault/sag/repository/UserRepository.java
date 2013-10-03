package com.yesnault.sag.repository;

import com.yesnault.sag.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByLastname(String lastname);

}