package com.edusol.retailbanking.application.service;

import com.edusol.retailbanking.application.dto.UserDto;
import com.edusol.retailbanking.application.entity.UserEntity;
import com.edusol.retailbanking.application.exception.UserServiceException;
import com.edusol.retailbanking.application.repository.UserRepository;
import com.edusol.retailbanking.application.responce.ErrorMessages;
import com.edusol.retailbanking.application.shared.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createdUser(UserDto user) {

        UserEntity storedEmail = userRepository.findUserByEmail(user.getEmail());
        if (storedEmail != null)
            throw new RuntimeException("User Email Already Exist");
        else {

            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(user, userEntity);

            userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            String publicUserId = utils.generateUserId(10);
            userEntity.setUserId(publicUserId);

            UserEntity storedUserDetail = userRepository.save(userEntity);

            UserDto returnValue = new UserDto();
            BeanUtils.copyProperties(storedUserDetail, returnValue);
            return returnValue;
        }
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserById(String userId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findUserByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException(userId);
        }
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto updateUser(UserDto user, String userId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findUserByUserId(userId);
        if (userEntity == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        // userEntity.setEmail(user.getEmail());
        // userEntity.setPassword(user.getPassword());
        UserEntity updatedRecord = userRepository.save(userEntity);

        BeanUtils.copyProperties(updatedRecord, returnValue);

        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findUserByUserId(userId);
        if (userEntity == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getLimitedUser(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();
        Pageable pageRequest = PageRequest.of(page, limit);
        Page<UserEntity> userPage = userRepository.findAll(pageRequest);
        List<UserEntity> users = userPage.getContent();
        for (UserEntity userEntity : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);

        }
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
