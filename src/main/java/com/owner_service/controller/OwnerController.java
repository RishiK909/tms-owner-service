package com.owner_service.controller;


import com.owner_service.dto.ApiResponseDTO;
import com.owner_service.dto.OwnerCreateDTO;
import com.owner_service.dto.OwnerResponseDTO;
import com.owner_service.dto.OwnerUpdateDTO;
import com.owner_service.service.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('Owner')")
    public ResponseEntity<ApiResponseDTO<OwnerResponseDTO>> createOwner(@Valid @RequestBody OwnerCreateDTO request, HttpServletRequest httpRequest) {

        UUID userId = (UUID) httpRequest.getAttribute("userId");

        ApiResponseDTO<OwnerResponseDTO> response = ownerService.createOwner(userId, request);
        return response.isStatus()
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/update/{ownerId}")
    public ResponseEntity<ApiResponseDTO<OwnerResponseDTO>> updateOwner(
            @PathVariable UUID ownerId,
            @Valid @RequestBody OwnerUpdateDTO request,
            HttpServletRequest httpRequest) {

        UUID userId = (UUID) httpRequest.getAttribute("userId");
        OwnerResponseDTO response = ownerService.updateOwner(ownerId, userId, request);
        return ResponseEntity.ok(new ApiResponseDTO<>("Owner updated successfully", true, response));
    }

    @DeleteMapping("/delete/{ownerId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteOwner(@PathVariable UUID ownerId,HttpServletRequest httpRequest) {

        UUID userId = (UUID) httpRequest.getAttribute("userId");
        ownerService.deleteOwner(ownerId, userId);
        return ResponseEntity.ok(new ApiResponseDTO<>("Owner deleted successfully", true));
    }

}
