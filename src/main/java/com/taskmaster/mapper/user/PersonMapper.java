package com.taskmaster.mapper.user;


import com.taskmaster.dto.user.request.RegisterPersonRequestDto;
import com.taskmaster.entity.PersonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class PersonMapper {


    public PersonEntity registerPersonRequestDtoToPersonEntity(RegisterPersonRequestDto registerPersonRequestDto){
        PersonEntity personEntity=new PersonEntity();

        personEntity.setEmail(registerPersonRequestDto.getEmail());
        personEntity.setPassword(registerPersonRequestDto.getPassword());
        personEntity.setGivenName(registerPersonRequestDto.getGivenName());
        personEntity.setFamilyName(registerPersonRequestDto.getFamilyName());



        return personEntity;
    }




}
