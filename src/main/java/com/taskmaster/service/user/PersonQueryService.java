package com.taskmaster.service.user;

import com.taskmaster.dto.user.response.PersonProfileDto;
import com.taskmaster.entity.PersonEntity;
import com.taskmaster.exception.ResourceNotFoundException;
import com.taskmaster.mapper.user.PersonMapper;
import com.taskmaster.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonQueryService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonProfileDto findById(UUID id) {
        PersonEntity entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + id));
        return personMapper.toProfile(entity);
    }

    public List<PersonProfileDto> findAll() {
        return personRepository.findAll().stream()
                .map(personMapper::toProfile)
                .toList();
    }
}
