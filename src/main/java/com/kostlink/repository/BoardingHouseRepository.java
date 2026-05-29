package com.kostlink.repository;

import com.kostlink.entity.BoardingHouse;
import com.kostlink.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardingHouseRepository extends JpaRepository<BoardingHouse, Long> {
    // cari kos berdasarkan kode unik
    Optional<BoardingHouse> findByBoardingCode(String boardingCode);

    // cek apakah boardingCode sudah digunakan
    boolean existsByBoardingCode(String boardingCode);

    // ambil boarding house berdasarkan owner
    Optional<BoardingHouse> findByOwner(Owner owner);

}