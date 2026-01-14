package com.taskmaster.mapper.user;

import com.taskmaster.dto.admin.response.RetrieveUsersFilterResponseDto;
import com.taskmaster.dto.task.response.RetrieveTaskFilterResponseDto;
import com.taskmaster.dto.user.request.RegisterPersonRequestDto;
import com.taskmaster.dto.user.response.RegisterPersonResponseDto;
import com.taskmaster.entity.PersonEntity;
import com.taskmaster.entity.RoleEntity;
import com.taskmaster.entity.enums.AuthEnum;
import com.taskmaster.entity.enums.RoleEnum;
import com.taskmaster.repository.PersonRepository;
import com.taskmaster.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PersonMapper {

    private final RoleRepository roleRepository;

    public PersonEntity registerPersonRequestDtoToPersonEntity(RegisterPersonRequestDto registerPersonRequestDto){
        PersonEntity personEntity=new PersonEntity();

        personEntity.setEmail(registerPersonRequestDto.getEmail());
        personEntity.setProvider(AuthEnum.LOCAL);
        personEntity.setPassword(registerPersonRequestDto.getPassword());
        personEntity.setGivenName(registerPersonRequestDto.getGivenName());
        personEntity.setFamilyName(registerPersonRequestDto.getFamilyName());

        RoleEntity roleEntity =
                roleRepository.findByType(RoleEnum.USUARIO);

        personEntity.setRole(roleEntity);

        return personEntity;
    }

    public RetrieveUsersFilterResponseDto personEntityToRetrieveFilterUsersResponseDto(PersonEntity personEntity){

        RetrieveUsersFilterResponseDto responseDto=new RetrieveUsersFilterResponseDto();

        responseDto.setProvider(String.valueOf(personEntity.getProvider()));
        responseDto.setEmail(personEntity.getEmail());
        responseDto.setGivenName(personEntity.getGivenName());
        responseDto.setFamilyName(personEntity.getFamilyName());
        responseDto.setRoleName(String.valueOf(personEntity.getRole().getType()));

        LocalDate date= LocalDate.from(personEntity.getCreatedAt());

        responseDto.setCreatedAt(date);

        return responseDto;
    }


}
