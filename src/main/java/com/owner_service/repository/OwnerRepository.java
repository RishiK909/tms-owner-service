package com.owner_service.repository;

import com.owner_service.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, UUID> {

    Optional<Owner> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);

    boolean existsByPhoneNumber(String phoneNumber);
}
