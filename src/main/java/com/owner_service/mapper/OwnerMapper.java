package com.owner_service.mapper;


import com.owner_service.dto.OwnerCreateDTO;
import com.owner_service.dto.OwnerResponseDTO;
import com.owner_service.dto.OwnerUpdateDTO;
import com.owner_service.entity.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Owner toEntity(OwnerCreateDTO request);

    OwnerResponseDTO toResponseDTO(Owner owner);

    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(OwnerUpdateDTO request, @MappingTarget Owner owner);
}
