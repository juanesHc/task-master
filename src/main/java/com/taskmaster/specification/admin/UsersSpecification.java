package com.taskmaster.specification.admin;

import com.taskmaster.dto.admin.request.RetrieveUsersFilterRequestDto;
import com.taskmaster.entity.PersonEntity;
import com.taskmaster.entity.TaskEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UsersSpecification {

    private static Specification<PersonEntity> hasGivenName(String givenName){
        return ((root, query, cb) -> cb.like(
                cb.lower(root.get("givenName")),"%"+givenName.toLowerCase()+"%"
        ));
    }

    private static Specification<PersonEntity> hasFamilyName(String familyName){
        return ((root, query, cb) -> cb.like(
                cb.lower(root.get("familyName")),"%"+familyName.toLowerCase()+"%"
        ));
    }

    private static Specification<PersonEntity> hasRole(String roleName) {
        return (root, query, cb) ->
                cb.equal(
                        root.get("role").get("type"),
                        roleName
                );
    }

    private static Specification<PersonEntity> createdAfter(LocalDate date) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("createdAt"), date);
    }


    private static Specification<PersonEntity> hasEmail(String email) {
        return (root, query, cb) ->
                cb.equal(cb.lower(root.get("email")), email.toLowerCase());
    }

    private static Specification<PersonEntity> hasProvider(String provider) {
        return (root, query, cb) ->
                cb.equal(cb.lower(root.get("provider")), provider.toLowerCase());
    }

    public static Specification<PersonEntity> buildUserSpecification(RetrieveUsersFilterRequestDto retrieveUsersFilterRequestDto){

        Specification<PersonEntity> personEntitySpecification=Specification.where(null);

        if(retrieveUsersFilterRequestDto.getGivenName()!=null && !retrieveUsersFilterRequestDto.getGivenName().isBlank()){
            personEntitySpecification=personEntitySpecification.and(hasGivenName(retrieveUsersFilterRequestDto.getGivenName()));
        }
        if(retrieveUsersFilterRequestDto.getFamilyName()!=null && !retrieveUsersFilterRequestDto.getFamilyName().isBlank()){
            personEntitySpecification=personEntitySpecification.and(hasFamilyName(retrieveUsersFilterRequestDto.getFamilyName()));
        }
        if(retrieveUsersFilterRequestDto.getEmail()!=null && !retrieveUsersFilterRequestDto.getEmail().isBlank()){
            personEntitySpecification=personEntitySpecification.and(hasEmail(retrieveUsersFilterRequestDto.getEmail()));
        }
        if(retrieveUsersFilterRequestDto.getProvider()!=null && !retrieveUsersFilterRequestDto.getProvider().isBlank()){
            personEntitySpecification=personEntitySpecification.and(hasProvider(retrieveUsersFilterRequestDto.getProvider()));
        }
        if(retrieveUsersFilterRequestDto.getRoleName()!=null && !retrieveUsersFilterRequestDto.getRoleName().isBlank()){
            personEntitySpecification=personEntitySpecification.and(hasRole(retrieveUsersFilterRequestDto.getRoleName()));
        }
        if (retrieveUsersFilterRequestDto.getCreatedAt() != null) {
            personEntitySpecification =
                    personEntitySpecification.and(createdAfter(retrieveUsersFilterRequestDto.getCreatedAt()));
        }

        return personEntitySpecification;
    }




}
