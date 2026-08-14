package com.owner_service.service;

import com.owner_service.dto.ApiResponseDTO;
import com.owner_service.dto.OwnerCreateDTO;
import com.owner_service.dto.OwnerResponseDTO;
import com.owner_service.dto.OwnerUpdateDTO;
import jakarta.validation.Valid;

import java.util.UUID;

public interface OwnerService {

    ApiResponseDTO<OwnerResponseDTO> createOwner(UUID userId, @Valid OwnerCreateDTO request);

    OwnerResponseDTO updateOwner(UUID ownerId, UUID currentUserId, OwnerUpdateDTO request);

    void deleteOwner(UUID ownerId, UUID currentUserId);

}
