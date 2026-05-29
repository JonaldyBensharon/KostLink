package com.kostlink.repository;

import com.kostlink.entity.BoardingHouse;
import com.kostlink.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // ambil semua room dalam suatu boarding house
    List<Room> findByBoardingHouse(BoardingHouse boardingHouse);

    // Pencarian room spesifik dalam suatu boarding house
    Optional<Room> findByIdAndBoardingHouse(Long id, BoardingHouse boardingHouse);

}