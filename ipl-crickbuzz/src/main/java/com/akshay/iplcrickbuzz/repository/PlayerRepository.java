package com.akshay.iplcrickbuzz.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.akshay.iplcrickbuzz.entity.Player;
public interface PlayerRepository extends JpaRepository<Player,Integer> {

}
