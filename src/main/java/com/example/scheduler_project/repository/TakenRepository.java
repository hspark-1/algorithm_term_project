package com.example.scheduler_project.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.scheduler_project.entity.Taken;

public interface TakenRepository extends JpaRepository<Taken, Long> {
	
	@Query(value =
            "SELECT * FROM TAKEN where visit = 0 and start_index = 0 and arrive_index = 3 and start = ?1 order by time",
            nativeQuery = true)
	List<Taken> findFirsttourlocation(long current_place);
	
	@Query(value =
            "SELECT * FROM TAKEN where visit = 0 and arrive_index = 3 and start = ?1 order by time",
            nativeQuery = true)
	List<Taken> findtourlocation(long current_place);
	
	@Transactional
	@Modifying
	@Query(value =
            "UPDATE TAKEN t set visit = 1 where t.arrive = ?1",
            nativeQuery = true)
	int updateTourlocation(long current_place);
	
	@Query(value =
            "SELECT * FROM TAKEN where visit = 0 and arrive_index = 1 and start = ?1 order by time",
            nativeQuery = true)
	List<Taken> findFirstLunchlocation(long current_place);
	
	@Transactional
	@Modifying
	@Query(value =
            "UPDATE TAKEN t set visit = 1 where t.arrive = ?1",
            nativeQuery = true)
	int updateLunchlocation(long current_place);
	
	@Query(value =
            "SELECT * FROM TAKEN where visit = 0 and arrive_index = 2 and start = ?1 order by time",
            nativeQuery = true)
	List<Taken> findFirstDinnerlocation(long current_place);
	
	@Transactional
	@Modifying
	@Query(value =
            "UPDATE TAKEN t set visit = 1 where t.arrive = ?1",
            nativeQuery = true)
	int updateDinnerlocation(long current_place);

}
