package com.essential.app.service;

import com.essential.app.model.Gender;
import com.essential.app.model.User;
import com.essential.app.repository.UserRepository;
import com.essential.app.resource.dto.GenderDto;
import com.essential.app.resource.dto.UserDto;
import com.essential.app.resource.dto.UserUpdateDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private UserRepository userRepository;
    private final GenderService genderService;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GenderService genderService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.genderService = genderService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
       return userRepository.findAll(pageable).map(user -> modelMapper.map(user, UserDto.class));

    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return Optional.ofNullable(modelMapper.map(this.userRepository.findById(id).orElseThrow(() -> getUserByIdNotFoundException(id)),UserDto.class ));
    }

    @Override
    @Transactional
    public void create(UserDto userDto) {
        User userEntity = modelMapper.map(userDto, User.class);
        userEntity.setGender(modelMapper.map(this.genderService.findByNameIgnoreCase(userDto.getGender().getName()).get(), Gender.class));
        userEntity.setCreatedAt(LocalDateTime.now());
        this.userRepository.save(userEntity);
    }

    @Override
    public void update(UserUpdateDto userUpdateDto) {
        User userFound = modelMapper.map(this.findById(userUpdateDto.getId()).get(), User.class);

        Optional.ofNullable(userUpdateDto.getGender())
                .filter(gender -> !userFound.getGender().getName().equalsIgnoreCase(gender.getName()))
                .ifPresent(gender -> {
                    this.genderService.findByNameIgnoreCase(gender.getName())
                            .map(genderDto -> modelMapper.map(genderDto, Gender.class))
                            .ifPresent(userFound::setGender);
        });

        Optional.ofNullable(userUpdateDto.getEmail())
                .ifPresent(userFound::setEmail);

        Optional.ofNullable(userUpdateDto.getAge())
                .ifPresent(userFound::setAge);

        Optional.ofNullable(userUpdateDto.getName())
                .ifPresent(userFound::setName);

        userFound.setUpdatedAt(LocalDateTime.now());

        this.userRepository.save(userFound);
    }

    @Override
    public void delete(Long id) {
       try {
           this.userRepository.deleteById(id);
       }catch (EmptyResultDataAccessException e){
           logger.info(e.getMessage());
       }
    }

    private EntityNotFoundException getUserByIdNotFoundException(Long id) {
        return new EntityNotFoundException("User Not Found With ID ".concat(String.valueOf(id)));
    }
}
