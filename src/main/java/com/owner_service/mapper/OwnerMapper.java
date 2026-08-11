package com.owner_service.mapper;


import com.owner_service.dto.OwnerCreateDTO;
import com.owner_service.dto.OwnerResponseDTO;
import com.owner_service.entity.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Owner toEntity(OwnerCreateDTO request);

    OwnerResponseDTO toResponse(Owner owner);
}
