package com.musala.delivery.drones.mappers;

import com.musala.delivery.drones.dto.HistoryDto;
import com.musala.delivery.drones.dto.HistoryRequestDto;
import com.musala.delivery.drones.entities.ActivityHistory;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Service
public abstract class HistoryMapper {
    public abstract HistoryDto toDto(ActivityHistory history);

    public abstract ActivityHistory toEntity(HistoryRequestDto requestDto);
}
