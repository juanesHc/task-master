package com.taskmaster.mapper.user;

import com.taskmaster.dto.user.response.PersonProfileDto;
import com.taskmaster.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {

    PersonProfileDto toProfile(PersonEntity person);
}
