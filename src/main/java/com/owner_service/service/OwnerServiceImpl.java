package com.owner_service.service;


import com.owner_service.Exception.DuplicateResourceException;
import com.owner_service.Exception.ResourceNotFoundException;
import com.owner_service.dto.ApiResponseDTO;
import com.owner_service.dto.OwnerCreateDTO;
import com.owner_service.dto.OwnerResponseDTO;
import com.owner_service.dto.OwnerUpdateDTO;
import com.owner_service.entity.Owner;
import com.owner_service.enums.Status;
import com.owner_service.mapper.OwnerMapper;
import com.owner_service.repository.OwnerRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OwnerServiceImpl implements OwnerService {


    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
    }


    @Override
    public ApiResponseDTO<OwnerResponseDTO> createOwner(
            UUID userId,
            OwnerCreateDTO request) {

        if (ownerRepository.existsByUserId(userId)) {
            throw new DuplicateResourceException(
                    "Owner profile already exists for this user"
            );
        }

        if (ownerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new DuplicateResourceException(
                    "Phone number already in use"
            );
        }

        Owner owner = ownerMapper.toEntity(request);
        owner.setUserId(userId);

        Owner savedOwner = ownerRepository.save(owner);

        OwnerResponseDTO responseDTO = ownerMapper.toResponseDTO(savedOwner);


        return new ApiResponseDTO<>(
                "Owner profile created successfully",
                true,
                responseDTO
                );
    }

    @Override
    public OwnerResponseDTO updateOwner(UUID ownerId, UUID currentUserId, OwnerUpdateDTO request) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        if (!owner.getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("You can only update your own profile");
        }

        ownerMapper.updateEntity(request, owner);

        Owner updated = ownerRepository.save(owner);
        return ownerMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteOwner(UUID ownerId, UUID currentUserId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        if (!owner.getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("You can only delete your own profile");
        }

        owner.setStatus(Status.Inactive);
        owner.setDeletedAt(LocalDateTime.now());
        ownerRepository.save(owner);
    }
}
