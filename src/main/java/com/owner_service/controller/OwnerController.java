package com.owner_service.controller;


import com.owner_service.dto.ApiResponseDTO;
import com.owner_service.dto.OwnerCreateDTO;
import com.owner_service.dto.OwnerResponseDTO;
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
    public ResponseEntity<ApiResponseDTO<Void>> createOwner(@Valid @RequestBody OwnerCreateDTO request, HttpServletRequest httpRequest) {

        UUID userId = (UUID) httpRequest.getAttribute("userId");

        ApiResponseDTO<Void> response = ownerService.createOwner(userId, request);
        return response.isStatus()
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }

}
