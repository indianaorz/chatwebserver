package com.colur;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ColorRepository extends JpaRepository<Color, Long> {
    Collection<Color> findByUserUsername(String username);
}