package com.kostlink.repository;

import com.kostlink.entity.RegistrationToken;
import com.kostlink.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {
    // cari token berdasarkan value
    Optional<RegistrationToken> findByTokenValue(String tokenValue);

    // validasi token per room
    Optional<RegistrationToken> findByTokenValueAndRoom(String tokenValue, Room room);

    // ambil semua token dalam room tertentu
    List<RegistrationToken> findByRoom(Room room);

}