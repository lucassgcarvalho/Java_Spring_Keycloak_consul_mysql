package com.essential.app.service;

import com.essential.app.resource.dto.UserDto;
import com.essential.app.resource.dto.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Page<UserDto> findAll(Pageable pageable);
    Optional<UserDto> findById(Long id);
    void create(UserDto entity);
    void update(UserUpdateDto userDto);
    void delete(Long id);
}
