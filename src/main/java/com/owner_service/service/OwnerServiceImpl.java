package com.owner_service.service;


import com.owner_service.Exception.DuplicateResourceException;
import com.owner_service.dto.ApiResponseDTO;
import com.owner_service.dto.OwnerCreateDTO;
import com.owner_service.dto.OwnerResponseDTO;
import com.owner_service.entity.Owner;
import com.owner_service.mapper.OwnerMapper;
import com.owner_service.repository.OwnerRepository;
import org.springframework.stereotype.Service;

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
    public ApiResponseDTO<Void> createOwner(
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

        ownerRepository.save(owner);

        return new ApiResponseDTO<>(
                "Owner profile created successfully",
                true,
                null
                );
    }
}
