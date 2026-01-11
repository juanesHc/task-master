package com.taskmaster.mapper.admin;

import com.taskmaster.dto.admin.request.RegisterWithRoleRequestDto;
import com.taskmaster.dto.user.request.RegisterPersonRequestDto;
import com.taskmaster.entity.PersonEntity;
import com.taskmaster.entity.RoleEntity;
import com.taskmaster.entity.enums.AuthEnum;
import com.taskmaster.entity.enums.RoleEnum;
import com.taskmaster.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminMapper {
    private final RoleRepository roleRepository;

    public PersonEntity registerWithRoleRequestDtoToPersonEntity(RegisterWithRoleRequestDto registerPersonRequestDto){
        PersonEntity personEntity=new PersonEntity();

        personEntity.setEmail(registerPersonRequestDto.getEmail());
        personEntity.setProvider(AuthEnum.LOCAL);
        personEntity.setPassword(registerPersonRequestDto.getPassword());
        personEntity.setGivenName(registerPersonRequestDto.getGivenName());
        personEntity.setFamilyName(registerPersonRequestDto.getFamilyName());

        RoleEntity roleEntity =
                roleRepository.findByType(RoleEnum.valueOf(registerPersonRequestDto.getRole()));

        personEntity.setRole(roleEntity);

        return personEntity;
    }
}
