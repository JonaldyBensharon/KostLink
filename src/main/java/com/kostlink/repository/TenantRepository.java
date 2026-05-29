package com.kostlink.repository;

import com.kostlink.entity.Room;
import com.kostlink.entity.Tenant;
import com.kostlink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    // Pengambilan tenant berdasarkan user login
    Optional<Tenant> findByUser(User user);

    // cek apakah user sudah punya tenant profile
    boolean existsByUser(User user);

    // ambil tenant berdasarkan room
    List<Tenant> findByRoom(Room room);

}