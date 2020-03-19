package com.edusol.retailbanking.application.repository;

import com.edusol.retailbanking.application.entity.UserEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {
    UserEntity findUserByEmail(String email);
    UserEntity findUserByUserId(String userId);

}
