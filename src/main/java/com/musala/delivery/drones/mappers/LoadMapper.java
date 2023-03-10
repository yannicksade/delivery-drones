package com.musala.delivery.drones.mappers;

import com.musala.delivery.drones.entities.dto.LoadRequest;
import com.musala.delivery.drones.entities.dto.LoadRequestDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Service
public abstract class LoadMapper {
    public abstract LoadRequest toLoad(LoadRequestDto loadRequestDto);
}
