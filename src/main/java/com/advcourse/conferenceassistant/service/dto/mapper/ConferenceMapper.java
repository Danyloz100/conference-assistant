package com.advcourse.conferenceassistant.service.dto.mapper;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;


import java.util.List;
import java.util.stream.Collectors;

public class ConferenceMapper {

    public static ConferenceDto toDto(Conference entity) {
        return new ConferenceDto(
                entity.getId(),
                entity.getTheme(),
                entity.getDescription(),
                entity.getStart(),
                entity.getEnd(),
                entity.getAddress() );
    }

    public static List<ConferenceDto> toDto(List<Conference> dto){
        return dto.stream().map(e->toDto(e)).collect(Collectors.toList());
    }

   public static List<Conference> fromDto(List<ConferenceDto> dto){
        return dto.stream().map(e->fromDto(e)).collect(Collectors.toList());
    }

    public static Conference fromDto(ConferenceDto dto){
        return new Conference(
                dto.getId(),
                dto.getTheme(),
                dto.getDescription(),
                dto.getStart(),
                dto.getEnd(),
                dto.getAddress() );
    }
}
