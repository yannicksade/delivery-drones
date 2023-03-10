package com.musala.delivery.drones.mappers;

import com.musala.delivery.drones.dto.LoadRequest;
import com.musala.delivery.drones.dto.LoadRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Service
public abstract class LoadMapper {
    public abstract LoadRequest toLoad(LoadRequestDto loadRequestDto);
}
