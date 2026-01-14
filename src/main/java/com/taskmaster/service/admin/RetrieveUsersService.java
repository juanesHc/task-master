package com.taskmaster.service.admin;

import com.taskmaster.dto.admin.request.RetrieveUsersFilterRequestDto;
import com.taskmaster.dto.admin.response.RetrieveUsersFilterResponseDto;
import com.taskmaster.entity.PersonEntity;
import com.taskmaster.mapper.user.PersonMapper;
import com.taskmaster.repository.PersonRepository;
import com.taskmaster.specification.admin.UsersSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RetrieveUsersService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public List<RetrieveUsersFilterResponseDto> retrieveUsers(
            RetrieveUsersFilterRequestDto filter
    ) {

        Specification<PersonEntity> specification = UsersSpecification.buildUserSpecification(filter);

        List<RetrieveUsersFilterResponseDto> responseDtos=new ArrayList<>();
        Specification<PersonEntity> spec = (UsersSpecification.buildUserSpecification(filter));

        List<PersonEntity> filteredUsers = personRepository.findAll(spec);
        filteredUsers.forEach(personEntity -> responseDtos.add(personMapper.personEntityToRetrieveFilterUsersResponseDto(personEntity)));

        return responseDtos;
    }
}
