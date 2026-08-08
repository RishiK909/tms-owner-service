package com.owner_service.service;

import com.owner_service.dto.ApiResponseDTO;
import com.owner_service.dto.OwnerCreateDTO;
import com.owner_service.dto.OwnerResponseDTO;
import jakarta.validation.Valid;

import java.util.UUID;

public interface OwnerService {

    ApiResponseDTO<Void> createOwner(UUID userId, @Valid OwnerCreateDTO request);

}
