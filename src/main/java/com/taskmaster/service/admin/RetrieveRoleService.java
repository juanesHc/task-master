package com.taskmaster.service.admin;

import com.taskmaster.dto.role.RoleDtos;
import com.taskmaster.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrieveRoleService {

    private final RoleRepository roleRepository;

    public RoleDtos retrieveRoles(){
        RoleDtos roleDtos=new RoleDtos();
        roleDtos.setRoles(roleRepository.findAllRoleTypes());

        return roleDtos;
    }

}
