package com.edusol.retailbanking.application.service;

import com.edusol.retailbanking.application.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    UserDto createdUser(UserDto user);
    UserDto  getUser(String email);
    UserDto getUserById(String userId);
    UserDto updateUser(UserDto user, String id);
    void deleteUser(String id);

    List<UserDto> getLimitedUser(int page, int limit);
}
