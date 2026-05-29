package com.kostlink.repository;

import com.kostlink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // cari user untuk login/autentikasi
    Optional<User> findByUsername(String username);

    // cek username apakah sudah digunakan atau belum untuk keperluan registrasi
    boolean existsByUsername(String username);

}