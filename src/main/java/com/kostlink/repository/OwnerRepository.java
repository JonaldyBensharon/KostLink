package com.kostlink.repository;

import com.kostlink.entity.Owner;
import com.kostlink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    // Pengambilan owner berdasarkan login
    Optional<Owner> findByUser(User user);

    boolean existsByUser(User user);

}