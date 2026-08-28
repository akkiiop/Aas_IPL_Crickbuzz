package com.akshay.iplcrickbuzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.akshay.iplcrickbuzz.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {

}
