package com.colur;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {
    Collection<User> findByUsername(String username);
    Collection<User> findByUserGoogleId(String userGoogleId);
}