package com.akshay.ipl_crickbuzz.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.akshay.ipl_crickbuzz.entity.Player;
public interface PlayerRepository extends JpaRepository<Player,Integer> {

}
